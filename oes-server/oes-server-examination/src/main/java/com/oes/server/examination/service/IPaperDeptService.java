package com.oes.server.examination.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.server.examination.entity.system.PaperDept;
import java.util.List;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
public interface IPaperDeptService extends IService<PaperDept> {

  /**
   * 通过试卷编号查询试卷-班级中间数据集合
   *
   * @param paperIds 试卷编号数组
   * @return {@link List<PaperDept>} 试卷-班级数据集合
   */
  List<PaperDept> getByPaperId(String[] paperIds);

}