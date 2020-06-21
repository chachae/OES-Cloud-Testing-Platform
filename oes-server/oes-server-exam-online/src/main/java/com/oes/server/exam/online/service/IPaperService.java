package com.oes.server.exam.online.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.entity.exam.Paper;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/21 18:04
 */
public interface IPaperService extends IService<Paper> {

  IPage<Paper> getNormalPaper(QueryParam param, Paper paper);

  IPage<Paper> getImitatePaper(QueryParam param, Paper paper);

}
