package com.graph.framework.cache;

import com.graph.framework.exception.BusinessException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@Aspect
public class CacheAspect {

    private static Logger logger = LoggerFactory.getLogger(com.graph.framework.cache.CacheAspect.class);
    @Autowired
    RedisTemplate redisTemplate;
    //@Value("${cache.enabled}")
    private boolean cacheEnabled = true;

    /**
     *
     * @param pjp
     * @return
     */
    @Around("@annotation(com.graph.framework.cache.Cacheable)")
    public Object cache(ProceedingJoinPoint pjp) {
        Object result = null;
        try {
            if (cacheEnabled) {
                Method method = getMethod(pjp);
                Cacheable cache = method.getAnnotation(Cacheable.class);
                StandardEvaluationContext context = getEvaluationContext(method, pjp.getArgs());
                String key = parseAnotationVar(cache.key(), context);
                if (StringUtils.isNotBlank(key)) {
                    // 从缓存中获取key对应的数据
                    String cacheStr = (String) redisTemplate.opsForValue().get(key);
                    if (StringUtils.isNotBlank(cacheStr)) {
                        result = unserialize(Base64.decodeBase64(cacheStr));
                    }
                    // 如果缓存中不存在，执行方法体
                    if (null == result) {
                        result = pjp.proceed();
                        if (null == result) {
                            return null;
                        }
                        context.setVariable("result", result);
                        // 将key添加到group里
                        SetOperations<String, String> setOpt = redisTemplate.opsForSet();
                        String group = parseAnotationVar(cache.group(), context);
                        setOpt.add(group, key);
                        // 缓存key对应的数据
                        byte[] bytes = serialize(result);
                        redisTemplate.opsForValue().set(key, Base64.encodeBase64String(bytes), cache.expire(), TimeUnit.SECONDS);
                    }
                }
            }
            if (null == result) {
                result = pjp.proceed();
            }
        } catch (BusinessException e) {
            throw e;
        } catch (Throwable e) {
            logger.error("获取缓存失败: " + e.getMessage(), e);
        }
        return result;
    }


    /**
     *
     * @param pjp
     * @return
     */
    @Around(value = "@annotation(com.graph.framework.cache.CacheEvict)")
    public Object evict(ProceedingJoinPoint pjp) {
        Object result = null;
        try {
            result = pjp.proceed();
            if (cacheEnabled) {
                Method method = getMethod(pjp);
                CacheEvict evict = method.getAnnotation(CacheEvict.class);
                StandardEvaluationContext context = getEvaluationContext(method, pjp.getArgs());
                String group = parseAnotationVar(evict.group(), context);
                String key = parseAnotationVar(evict.key(), context);
                SetOperations<String, String> setOpt = redisTemplate.opsForSet();
                if (StringUtils.isNotBlank(key)) {
                    // 清空特定key对应的缓存
                    redisTemplate.opsForValue().getOperations().delete(key);
                    setOpt.remove(group, key);
                } else {
                    // 清空特定group对应的缓存
                    Set<String> groupSet = setOpt.members(group);
                    for (String val : groupSet) {
                        redisTemplate.opsForValue().getOperations().delete(val);
                        setOpt.remove(group, val);
                    }
                }
            }
        } catch (BusinessException e) {
            throw e;
        } catch (Throwable t) {
            logger.error("清空缓存失败: " + t.getMessage(), t);
        }
        return result;
    }

    /**
     *
     * @param pjp
     * @return
     */
    private Method getMethod(ProceedingJoinPoint pjp) throws Exception {
        //获取参数的类型
        Object[] args = pjp.getArgs();
        Class[] argTypes = new Class[pjp.getArgs().length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
        Method method = pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), argTypes);
        return method;
    }

    /**
     *
     * @param method
     * @param args
     * @return
     * @throws Exception
     */
    private StandardEvaluationContext getEvaluationContext(Method method, Object[] args) throws Exception {
        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = discoverer.getParameterNames(method);
        StandardEvaluationContext context = new StandardEvaluationContext();
        //放入SpEL上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        return context;
    }

    /**
     *
     * @param key
     * @param context
     * @return
     * @throws Exception
     */
    private String parseAnotationVar(String key, StandardEvaluationContext context) throws Exception {
        if (-1 == key.indexOf("#")) {
            return key;
        }
        //SpEL解析
        ExpressionParser parser = new SpelExpressionParser();
        String[] fragments = key.split("_");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < fragments.length; i++) {
            String fragment = fragments[i];
            if (fragment.startsWith("#")) {
                builder.append(parser.parseExpression(fragment).getValue(context, String.class));
            } else {
                builder.append(fragment);
            }
            if (i < (fragments.length - 1)) {
                builder.append("_");
            }
        }
        return builder.toString();
    }

    /**
     * 反序列化
     * @param bytes
     * @return
     */
    private Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            logger.error("缓存反序列化失败: " + e.getMessage(), e);
        } finally {
            try {
                if (null != ois) {
                    ois.close();
                }
                if (null != bais) {
                    bais.close();
                }
            } catch (Exception e) {
                logger.error("关闭缓存反序列化流失败: " + e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * 序列化
     * @param object
     * @return
     */
    private byte[] serialize(Object object) {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            logger.error("缓存序列化失败: " + e.getMessage(), e);
        } finally {
            try {
                if (null != oos) {
                    oos.close();
                }
                if (null != baos) {
                    baos.close();
                }
            } catch (Exception e) {
                logger.error("关闭缓存序列化流失败: " + e.getMessage(), e);
            }
        }
        return null;
    }

}