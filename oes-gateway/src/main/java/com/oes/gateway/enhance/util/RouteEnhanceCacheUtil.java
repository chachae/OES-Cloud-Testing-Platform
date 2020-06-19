package com.oes.gateway.enhance.util;

import com.oes.common.core.constant.SystemConstant;

/**
 * @author chachae
 */
public class RouteEnhanceCacheUtil {

  private static final String BLACKLIST_CHACHE_KEY_PREFIX = "oes:route:blacklist:";
  private static final String RATELIMIT_CACHE_KEY_PREFIX = "oes:route:ratelimit:";
  private static final String RATELIMIT_COUNT_KEY_PREFIX = "oes:route:ratelimit:cout:";

  public static String getBlackListCacheKey(String ip) {
    if (SystemConstant.LOCALHOST.equalsIgnoreCase(ip)) {
      ip = SystemConstant.LOCALHOST_IP;
    }
    return String.format("%s%s", BLACKLIST_CHACHE_KEY_PREFIX, ip);
  }

  public static String getBlackListCacheKey() {
    return String.format("%sall", BLACKLIST_CHACHE_KEY_PREFIX);
  }

  public static String getRateLimitCacheKey(String uri, String method) {
    return String.format("%s%s:%s", RATELIMIT_CACHE_KEY_PREFIX, uri, method);
  }

  public static String getRateLimitCountKey(String uri, String ip) {
    return String.format("%s%s:%s", RATELIMIT_COUNT_KEY_PREFIX, uri, ip);
  }
}
