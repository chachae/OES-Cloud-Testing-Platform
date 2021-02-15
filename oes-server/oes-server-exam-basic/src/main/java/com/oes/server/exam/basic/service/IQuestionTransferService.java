package com.oes.server.exam.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.entity.ResultMap;
import com.oes.common.core.exam.entity.QuestionTransfer;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
public interface IQuestionTransferService extends IService<QuestionTransfer> {

  /**
   * 导入Excel题目数据到临时表
   * @param file file
   * @return ResultMap
   */
  ResultMap importExcel(MultipartFile file);

  /**
   * 导入题目到中转表
   *
   * @param questionTransferList 题目信息
   */
  void insertBatch(List<QuestionTransfer> questionTransferList);

  /**
   * 通过导入批次号删除临时表数据
   *
   * @param importId 导入批次号
   */
  void deleteByImportId(String importId);


  /**
   * 通过导入批次号获取临时表是题目数据
   *
   * @param importId 导入批次号
   */
  List<QuestionTransfer> listByImportId(Long importId);

}