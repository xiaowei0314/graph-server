package com.graph.framework.db;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 针对service层 find*;get*;query*;方法自动使用读库,多个读库随机选择
 * 针对service层 update*;insert*;add*;delete*;save*;方法自动采用写库
 * 如需额外设置某方法数据库，采用@ReadDataSource @WriteDataSource
 * 读写分离切面一定要先于事物AOP执行Order(1) @EnableTransactionManagement(order = 10)
 */
@Aspect
@Component
@Order(1)
public class DataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(com.graph.framework.db.DataSourceAspect.class);

    @Value("${slave.datasource.names}")
    private String slaves;

    @Pointcut("execution(* com..service..*.*(..)) " +
            "&& @annotation(com.graph.framework.db.ReadDataSource)")
    private void readPointcut() {
    }

    @Pointcut("execution(* com..service..*.*(..)) " +
            "&& @annotation(com.graph.framework.db.WriteDataSource)")
    private void writePointcut() {
    }

    @Before("writePointcut()")
    public void setWriteDataSource(JoinPoint joinPoint) {
        DataSourceHolder.setDataSourceName("w");
        if (logger.isDebugEnabled()) {
            logger.debug("set datasource w, from:{} ", pointInfo(joinPoint));
        }
    }

    @Before("readPointcut()")
    public void setReadDataSource(JoinPoint joinPoint) {
        try {
            String[] slaveArray = slaves.split(",");
            Random random = new Random();
            int index = random.nextInt(slaveArray.length);
            DataSourceHolder.setDataSourceName(slaveArray[index]);
            if (logger.isDebugEnabled()) {
                logger.debug("set datasource:{}, from:{} ", slaveArray[index], pointInfo(joinPoint));
            }
        } catch (Exception e) {
            // 清空，使用默认数据源
            DataSourceHolder.clear();
            logger.error("setReadDataSourceErr at {}: ", pointInfo(joinPoint));
            logger.error("setReadDataSourceErr: " + e.getMessage(), e);
        }
    }

    // 后置通知
    @After("readPointcut()")
    public void restoreReadDs(JoinPoint joinPoint) {
        DataSourceHolder.clear();
        if (logger.isDebugEnabled()) {
            logger.debug("restore from:{} ", pointInfo(joinPoint));
        }
    }

    // 后置通知
    @After("writePointcut()")
    public void restoreWriteDs(JoinPoint joinPoint) {
        DataSourceHolder.clear();
        if (logger.isDebugEnabled()) {
            logger.debug("restore from:{} ", pointInfo(joinPoint));
        }
    }

    public String pointInfo(JoinPoint joinPoint) {
        StringBuffer str = new StringBuffer("point:" + joinPoint.toString() + "args: ");
        for (Object obj : joinPoint.getArgs()) {
            str.append(obj);
            str.append(";");
        }
        return str.toString();
    }
}
