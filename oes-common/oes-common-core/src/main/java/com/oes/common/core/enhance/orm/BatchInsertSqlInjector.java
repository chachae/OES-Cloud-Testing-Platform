package com.oes.common.core.enhance.orm;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import java.util.List;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/11/27 14:11
 */
public class BatchInsertSqlInjector extends DefaultSqlInjector {

  @Override
  public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
    List<AbstractMethod> methodList = super.getMethodList(mapperClass);
    // MySQL 批量插入选装
    methodList.add(new InsertBatchSomeColumn(i -> i.getFieldFill() != FieldFill.UPDATE));
    return methodList;
  }
}
