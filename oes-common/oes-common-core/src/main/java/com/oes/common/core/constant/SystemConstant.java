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
   * 试卷缓存前缀，后跟：试卷编号
   */
  String PAPER_PREFIX = "oes:paper:";


  /**
   * 试卷答案缓存前置，后跟：试卷编号
   */
  String PAPER_QUESTION_PREFIX = "oes:paper-question:";

  /**
   * 默认缓存时效（秒）
   */
  Long DEFAULT_EXPIRED = 2 * 3600L;

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

  String START_WITH_HTTP = "http://";

  String START_WITH_HTTPS = "https://";

  String JSON_HEADER = "application/json; charset=UTF-8";

  String FORM_ENCODED = "application/x-www-form-urlencoded;charset=utf-8";

  String BASE64_JPG_HEAD = "data:image/jpg;base64,";

}
