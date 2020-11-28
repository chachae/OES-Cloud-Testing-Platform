package com.oes.server.exam.basic.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.constant.SystemConstant;
import com.oes.common.core.exam.entity.Type;
import com.oes.common.core.exam.entity.query.QueryTypeDto;
import com.oes.common.core.exception.ApiException;
import com.oes.common.core.util.SortUtil;
import com.oes.server.exam.basic.mapper.TypeMapper;
import com.oes.server.exam.basic.service.IQuestionService;
import com.oes.server.exam.basic.service.ITypeService;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements ITypeService {

  private final IQuestionService questionService;

  @Override
  public IPage<Type> pageType(QueryTypeDto type) {
    Page<Type> page = new Page<>(type.getPageNum(), type.getPageSize());
    LambdaQueryWrapper<Type> wrapper = new LambdaQueryWrapper<>();
    if (StrUtil.isNotBlank(type.getTypeName())) {
      wrapper.like(Type::getTypeName, type.getTypeName());
    }
    SortUtil.handlePageSort(type, page, "typeId", SystemConstant.ORDER_ASC, true);
    return baseMapper.selectPage(page, wrapper);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void createType(Type type) {
    baseMapper.insert(type);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteType(String[] typeIds) {
    if (hasDefaultType(typeIds)) {
      throw new ApiException("不能删除默认题型");
    }
    if (hasQuestion(typeIds)) {
      throw new ApiException("该题型已有题目使用，请移除相关题目后重试");
    }

    baseMapper.deleteBatchIds(Arrays.asList(typeIds));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateType(Type type) {
    baseMapper.updateById(type);
  }

  @Override
  public Type getByTypeName(String typeName) {
    return baseMapper.selectOne(new LambdaQueryWrapper<Type>().eq(Type::getTypeName, typeName));
  }

  private boolean hasDefaultType(String[] typeIds) {
    for (String typeId : typeIds) {
      if (Integer.parseInt(typeId) <= 5) {
        return true;
      }
    }
    return false;
  }

  public boolean hasQuestion(String[] typeIds) {
    return this.questionService.countByTypeIds(typeIds) > 0;
  }
}