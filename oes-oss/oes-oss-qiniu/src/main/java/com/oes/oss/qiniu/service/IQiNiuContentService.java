package com.oes.oss.qiniu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.oss.qiniu.entity.QiNiuConfig;
import com.oes.oss.qiniu.entity.QiNiuContent;
import com.oes.oss.qiniu.entity.query.QiNiuQueryDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * 七牛云文件存储表服务接口
 *
 * @author chachae
 * @since 2020-06-27 13:21:53
 */
public interface IQiNiuContentService extends IService<QiNiuContent> {

  /**
   * 分页查询
   *
   * @return /
   */
  IPage<QiNiuContent> pageQiNiuContent(QiNiuQueryDto qiNiuQueryDto);

  /**
   * 上传文件
   *
   * @param file        文件
   * @param qiNiuConfig 配置
   * @return QiNiuContent
   */
  QiNiuContent upload(MultipartFile file, QiNiuConfig qiNiuConfig);

  /**
   * 通过文件名查询文件
   *
   * @param name name
   * @return /
   */
  QiNiuContent getByName(String name);

  /**
   * 查询文件
   *
   * @param id 文件ID
   * @return QiniuContent
   */
  QiNiuContent getById(Long id);

  /**
   * 下载文件
   *
   * @param content 文件信息
   * @param config  配置
   * @return String
   */
  String download(QiNiuContent content, QiNiuConfig config);

  /**
   * 删除文件
   *
   * @param content 文件
   * @param config  配置
   */
  void delete(QiNiuContent content, QiNiuConfig config);

  /**
   * 同步数据
   *
   * @param config 配置
   */
  void synchronize(QiNiuConfig config);

  /**
   * 删除文件
   *
   * @param ids    文件ID数组
   * @param config 配置
   */
  void deleteAll(Long[] ids, QiNiuConfig config);
}