package com.oes.server.system.event;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 支持批处理限制的泛型 Excel 数据读取同步解析监听
 *
 * @author chachae
 * @version v1.0
 * @date 2020/7/21 23:11
 */
public class SyncReadExcelListener<E> extends AnalysisEventListener<E> {

  /**
   * 解析数据集合
   */
  private final List<E> list;

  /**
   * 批处理，默认-1，不限制
   */
  private Integer batchCount = -1;

  public SyncReadExcelListener() {
    this(-1);
  }

  public SyncReadExcelListener(int batchCount) {
    if (batchCount <= 0) {
      list = new ArrayList<>();
    } else {
      this.batchCount = batchCount;
      list = new ArrayList<>(batchCount);
    }
  }

  @Override
  public void invoke(E e, AnalysisContext analysisContext) {
    if (this.batchCount == -1 || list.size() < this.batchCount) {
      list.add(e);
    }
  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    // 啥都不用干
  }

  public List<E> getList() {
    return this.list;
  }
}