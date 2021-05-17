package com.oes.server.exam.basic.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.entity.ResultMap;
import com.oes.common.core.exam.entity.QuestionTransfer;
import com.oes.common.core.exam.util.QuestionUtil;
import com.oes.common.core.exception.ApiException;
import com.oes.server.exam.basic.config.SnowflakeConfig;
import com.oes.server.exam.basic.mapper.QuestionTransferMapper;
import com.oes.server.exam.basic.service.IQuestionTransferService;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chachae
 * @date 2021-02-15 10:25:16
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class QuestionTransferServiceImpl extends ServiceImpl<QuestionTransferMapper, QuestionTransfer> implements IQuestionTransferService {

  private final SnowflakeConfig snowflakeConfig;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void insertBatch(List<QuestionTransfer> questionTransferList) {
    this.baseMapper.insertBatchSomeColumn(questionTransferList);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteByImportId(String importId) {
    LambdaQueryWrapper<QuestionTransfer> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(QuestionTransfer::getImportId, importId);
    this.baseMapper.delete(queryWrapper);
  }

  @Override
  public List<QuestionTransfer> listByImportId(Long importId) {
    LambdaQueryWrapper<QuestionTransfer> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(QuestionTransfer::getImportId, importId);
    return this.baseMapper.selectList(queryWrapper);
  }

  @Override
  public ResultMap importExcel(MultipartFile file) {
    // 同步读取题目数据
    List<QuestionTransfer> questionTransferList;
    // 获取输入流对象
    InputStream inputStream = null;
    try {
      inputStream = file.getInputStream();
      // 同步读取题目数据
      questionTransferList =
          EasyExcel
              .read(inputStream)
              .head(QuestionTransfer.class)
              .sheet()  // 设置sheet，默认读取第一个
              .headRowNumber(2) // 设置标题所在行数
              .doReadSync();  // 同步读取
    } catch (IOException e) {
      throw new ApiException("导入题目失败");
    } finally {
      // 关闭流
      if (inputStream != null) {
        IoUtil.close(inputStream);
      }
    }

    // 数据校验
    String errorMessage = "导入题目不能为空";
    if (CollUtil.isNotEmpty(questionTransferList) && questionTransferList.size() > 0) {
      // 校验
      String currentErrorResult = this.validateQuestionTransfer(questionTransferList);
      if (currentErrorResult.length() == 0) {
        // 批量插入
        this.insertBatch(questionTransferList);
        return new ResultMap().putKV("questionList", questionTransferList).putKV("message", "ok");
      } else {
        errorMessage = currentErrorResult;
      }
    }
    return new ResultMap().putKV("message", errorMessage);
  }

  /**
   * 校验题目的正确性
   */
  private String validateQuestionTransfer(List<QuestionTransfer> questionTransferList) {
    // 导入批次编号，example: 1613364943886
    String importId = String.valueOf(this.snowflakeConfig.getId());
    // 异常数据StringBuilder
    StringBuilder resultBuilder = new StringBuilder();
    for (QuestionTransfer questionTransfer : questionTransferList) {
      String errorMessage = QuestionUtil.validateImportQuestion(questionTransfer);
      if (errorMessage.length() != 0) {
        errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
        resultBuilder
            .append(questionTransfer.getQuestionName())
            .append(" : ")
            .append(errorMessage)
            .append("\\r\\n");
      } else {
        // 设置导入批次号
        questionTransfer.setImportId(importId);
      }
    }

    // 异常数据
    return resultBuilder.toString().trim();
  }
}