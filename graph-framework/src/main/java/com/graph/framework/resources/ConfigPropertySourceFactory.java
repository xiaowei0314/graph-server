package com.graph.framework.resources;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Create by xiaow on 2018/11/13
 */
public class ConfigPropertySourceFactory {

    private final List<ConfigPropertySource> configPropertySources = Lists.newLinkedList();

    public ConfigPropertySource getConfigPropertySource(String name, Config source) {
        ConfigPropertySource configPropertySource = new ConfigPropertySource(name, source);
        configPropertySources.add(configPropertySource);
        return configPropertySource;
    }

    public List<ConfigPropertySource> getAllConfigPropertySources() {
        return Lists.newLinkedList(configPropertySources);
    }
}
