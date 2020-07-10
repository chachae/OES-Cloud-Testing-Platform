package com.oes.oss.qiniu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oes.oss.qiniu.entity.QiNiuContent;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Select;

/**
 * 七牛云文件存储(QiniuContent)表数据库访问层
 *
 * @author chachae
 * @since 2020-06-27 11:37:24
 */
public interface QiNiuContentMapper extends BaseMapper<QiNiuContent> {

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
  @Select("SELECT COUNT(1) value,tqc.suffix name FROM t_qiniu_content tqc GROUP BY tqc.suffix")
  List<Map<String, Object>> listCountTopTenFileTypeData();

}