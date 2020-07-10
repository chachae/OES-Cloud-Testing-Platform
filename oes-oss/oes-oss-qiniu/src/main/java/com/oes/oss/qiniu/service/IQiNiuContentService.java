package com.oes.oss.qiniu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.constant.SystemConstant;
import com.oes.oss.qiniu.entity.QiNiuConfig;
import com.oes.oss.qiniu.entity.QiNiuContent;
import com.oes.oss.qiniu.entity.query.QiNiuQueryDto;
import java.util.List;
import java.util.Map;
import org.springframework.scheduling.annotation.Async;
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
   * 通过编号查询图片数据
   *
   * @param ids 编号数组
   * @return {@link List<QiNiuContent>} 图片集合数据
   */
  List<QiNiuContent> getByIds(String[] ids);

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
   * @return QiNiuContent
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
   * @param contentIds 文件编号
   * @param config     配置
   */
  void delete(String[] contentIds, QiNiuConfig config);

  /**
   * 删除七牛云对象存储文件
   *
   * @param contents 文件
   * @param config   配置
   */
  @Async(SystemConstant.ASYNC_POOL)
  void deleteOssFiles(List<QiNiuContent> contents, QiNiuConfig config);

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
  void deleteAll(String[] ids, QiNiuConfig config);

  /**
   * 统计排名前10的文件类型及数量
   *
   * <pre>
   *   返回的集合内 Map 中的数据解释
   *   value：当前类型的文件总数
   *   name：当前文件的类型
   * </pre>
   *
   * @return {@link List <Map>} 查询数据题
   */
  List<Map<String, Object>> getTopTenFileTypeData();
}