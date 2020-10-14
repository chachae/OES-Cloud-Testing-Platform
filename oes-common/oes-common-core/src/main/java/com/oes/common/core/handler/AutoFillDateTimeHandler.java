package com.oes.common.core.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.oes.common.core.constant.FieldConstant;
import java.util.Date;
import org.apache.ibatis.reflection.MetaObject;

/**
 *
 * @author chachae
 * @date 2020/9/28 20:29
 * @version v1.0
 */
public abstract class AutoFillDateTimeHandler implements MetaObjectHandler {

  @Override
  public void insertFill(MetaObject metaObject) {
    setFieldValByName(FieldConstant.CREATE_TIME, new Date(), metaObject);
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    setFieldValByName(FieldConstant.UPDATE_TIME, new Date(), metaObject);
  }
}
