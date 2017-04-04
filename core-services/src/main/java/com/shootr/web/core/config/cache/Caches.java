package com.shootr.web.core.config.cache;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public interface Caches {

    Long defaultCacheExpiration = 60L;

    String TEST = "TEST";

    Map<String, Long> ALL = ImmutableMap.<String, Long>builder()
            .put(TEST, defaultCacheExpiration)
            .build();

}
