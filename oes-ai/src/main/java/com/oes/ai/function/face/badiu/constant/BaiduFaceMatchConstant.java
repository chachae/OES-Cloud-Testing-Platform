package com.oes.ai.function.face.badiu.constant;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/7/9 21:47
 */
public interface BaiduFaceMatchConstant {

  /**
   * 上传的图片类型：base64
   */
  String TYPE_IMAGE_BASE64 = "BASE64";

  /**
   * 上传的图片类型：链接
   */
  String TYP_IMAGE_URL = "URL";

  /**
   * 上传的图片类型：图片token
   */
  String TYPE_IMAGE_FACE_TOKEN = "FACE_TOKEN";

  /**
   * 表示生活照：通常为手机、相机拍摄的人像图片、或从网络获取的人像图片等
   */
  String TYPE_FACE_LIVE = "LIVE";

  /**
   * 表示身份证芯片照
   */
  String TYPE_FACE_IDCARD = "IDCARD";

  /**
   * 表示带水印证件照：一般为带水印的小图，如公安网小图
   */
  String TYPE_FACE_WATERMARK = "WATERMARK";

  /**
   * 表示证件照片：如拍摄的身份证、工卡、护照、学生证等证件图片
   */
  String TYPE_FACE_CERT = "CERT";

  /**
   * 表示红外照片：使用红外相机拍摄的照片
   */
  String TYPE_FACE_INFRARED = "INFRARED";

  /**
   * 不进行控制（默认）
   */
  String CONTROL_NONE = "NONE";

  /**
   * 较低
   */
  String CONTROL_LOW = "LOW";

  /**
   * 一般
   */
  String CONTROL_NORMAL = "NORMAL";

  /**
   * 较高
   */
  String CONTROL_HIGH = "HIGH";

  /**
   * 图片信息 key
   */
  String KEY_IMAGE = "image";

  /**
   * 图片类型 key
   */
  String KET_IMAGE_TYPE = "image_type";

  /**
   * 人脸的类型 key
   */
  String KEY_FACE_TYPE = "face_type";

  /**
   * 图片质量控制 key
   */
  String KEY_QUALITY_CONTROL = "quality_control";

  /**
   * 活体检测控制 key
   */
  String KEY_LIVENESS_CONTROL = "liveness_control";

  String KEY_ERR_CODE = "error_code";

  String KEY_ERR_MSG = "error_msg";

  String KEY_RESULT = "result";

  String KEY_SCORE = "score";

}
