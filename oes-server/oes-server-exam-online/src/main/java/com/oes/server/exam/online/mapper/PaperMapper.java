package com.oes.server.exam.online.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oes.common.core.exam.entity.Paper;
import com.oes.common.core.exam.entity.query.QueryPaperDto;
import org.apache.ibatis.annotations.Param;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/21 18:26
 */
public interface PaperMapper extends BaseMapper<Paper> {

  IPage<Paper> pagePaper(@Param("paper") QueryPaperDto paper, Page<Paper> page);

}
