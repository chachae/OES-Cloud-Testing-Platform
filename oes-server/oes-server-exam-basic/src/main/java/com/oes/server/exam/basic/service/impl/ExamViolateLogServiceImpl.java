package com.oes.server.exam.basic.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.constant.DataSourceConstant;
import com.oes.common.core.constant.SystemConstant;
import com.oes.common.core.entity.UserAgent;
import com.oes.common.core.entity.auth.CurrentUser;
import com.oes.common.core.exam.constant.ExamViolateBehaviourEnum;
import com.oes.common.core.exam.entity.ExamViolateLog;
import com.oes.common.core.exam.entity.query.QueryExamViolateLogDto;
import com.oes.common.core.util.DateUtil;
import com.oes.common.core.util.HttpUtil;
import com.oes.common.core.util.SecurityUtil;
import com.oes.common.core.util.SortUtil;
import com.oes.server.exam.basic.mapper.ExamViolateLogMapper;
import com.oes.server.exam.basic.service.IExamViolateLogService;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 考试违规行为日志表服务实现类
 *
 * @author chachae
 * @since 2020-07-15 20:18:32
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ExamViolateLogServiceImpl extends
    ServiceImpl<ExamViolateLogMapper, ExamViolateLog> implements IExamViolateLogService {

  @Override
  @DS(DataSourceConstant.SLAVE)
  public IPage<ExamViolateLog> pageExamViolateLog(QueryExamViolateLogDto entity) {
    Page<ExamViolateLog> page = new Page<>(entity.getPageNum(), entity.getPageSize());
    SortUtil.handlePageSort(entity, page, "violate_id", SystemConstant.ORDER_DESC, false);
    return baseMapper.pageExamViolateLog(page, entity);
  }

  @Override
  @DS(DataSourceConstant.SLAVE)
  public List<ExamViolateLog> selectByPaperId(Long paperId) {
    return baseMapper.selectByPaperId(paperId);
  }

  @Override
  @DS(DataSourceConstant.SLAVE)
  public Integer getViolateCount(Long paperId, String username) {
    return baseMapper
        .selectCount(new LambdaQueryWrapper<ExamViolateLog>()
            .eq(ExamViolateLog::getPaperId, paperId)
            .eq(ExamViolateLog::getUsername, username));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void saveExamViolateLog(ExamViolateLog examViolateLog) {
    examViolateLog.setUsername(SecurityUtil.getCurrentUsername());
    examViolateLog.setFullName(SecurityUtil.getCurrentUser().getFullName());
    setExamViolateLogInfo(examViolateLog);
    baseMapper.insert(examViolateLog);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteExamViolateLog(List<String> violateIds) {
    baseMapper.deleteBatchIds(violateIds);
  }

  private void setExamViolateLogInfo(ExamViolateLog examViolateLog) {
    // 获取请求 UserAgent 信息
    UserAgent agentInfo = HttpUtil.getUserAgent();
    CurrentUser curUser = SecurityUtil.getCurrentUser();
    String stayTime = DateUtil.calTimes(examViolateLog.getViolateTime(), new Date());
    String violateMsg = ExamViolateBehaviourEnum.getMsg(examViolateLog.getCode());
    // 格式化描述信息
    final String desc = String.format("%s：%s于%s时%s，违规停留时间共计%s。", curUser.getDeptName(),
        curUser.getFullName(), DateUtil.formatDate(examViolateLog.getViolateTime()), violateMsg, stayTime);

    examViolateLog.setUsername(curUser.getUsername());
    examViolateLog.setFullName(curUser.getFullName());
    examViolateLog.setBehaviour(violateMsg);
    examViolateLog.setSystem(agentInfo.getSystem());
    examViolateLog.setBrowser(agentInfo.getBrowser());
    examViolateLog.setDescription(desc);
    examViolateLog.setStayTime(stayTime);
  }
}