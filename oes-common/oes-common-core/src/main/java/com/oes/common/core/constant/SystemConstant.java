package com.oes.common.core.constant;

/**
 * 系统常量
 *
 * @author chachae
 * @since 2020/4/24 18:18
 */
public interface SystemConstant {

  /**
   * 排序规则：降序
   */
  String ORDER_DESC = "descending";

  /**
   * 排序规则：升序
   */
  String ORDER_ASC = "ascending";

  /**
   * OAUTH 2 令牌类型 https://oauth.net/2/bearer-tokens/
   */
  String OAUTH2_TOKEN_TYPE = "bearer";


  /**
   * 验证码 key前缀
   */
  String CAPTCHA_PREFIX = "oes:captcha:";

  /**
   * 异步线程池名称
   */
  String ASYNC_POOL = "oes-async-thread-pool";

  /**
   * Java默认临时目录
   */
  String JAVA_TEMP_DIR = "java.io.tmpdir";

  /**
   * 注册用户角色ID
   */
  Long REGISTER_ROLE_ID = 2L;

  String LOCALHOST = "localhost";
  String LOCALHOST_IP = "127.0.0.1";
}
