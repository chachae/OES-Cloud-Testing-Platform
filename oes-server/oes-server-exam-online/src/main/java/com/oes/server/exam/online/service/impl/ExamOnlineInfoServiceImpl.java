package com.oes.server.exam.online.service.impl;

import com.oes.server.exam.online.cache.ExamOnlineInfoCache;
import com.oes.server.exam.online.entity.OnlineExamUser;
import com.oes.server.exam.online.service.IExamOnlineInfoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author chachae
 * @date 2020/9/14 11:52
 * @version v1.0
 */
@Service
@RequiredArgsConstructor
public class ExamOnlineInfoServiceImpl implements IExamOnlineInfoService {

  private final ExamOnlineInfoCache examOnlineInfoCache;

  @Override
  public void saveInfo(String username, String fullName, String paperId) {
    examOnlineInfoCache.save(paperId, new OnlineExamUser(username, fullName));
  }

  @Override
  public boolean checkOnlineUser(String username, String fullName, String paperId) {
    return examOnlineInfoCache.checkOnlineUser(username, fullName, paperId);
  }

  @Override
  public void removeInfo(String paperId, OnlineExamUser onlineExamUser) {
    examOnlineInfoCache.remove(paperId, onlineExamUser);
  }

  @Override
  public List<Object> listByPaperId(String paperId) {
    return examOnlineInfoCache.list(paperId);
  }

  @Override
  public Long countByPaperId(String paperId) {
    return examOnlineInfoCache.countByPaperId(paperId);
  }
}
