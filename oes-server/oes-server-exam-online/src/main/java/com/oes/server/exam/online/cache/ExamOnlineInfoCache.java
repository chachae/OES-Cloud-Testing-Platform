package com.oes.server.exam.online.cache;

import com.oes.common.core.cache.ICacheService;
import com.oes.common.redis.starter.service.RedisService;
import com.oes.server.exam.online.entity.OnlineExamUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author chachae
 * @date 2020/9/13 21:29
 * @version v1.0
 */
@Service
@RequiredArgsConstructor
public class ExamOnlineInfoCache implements ICacheService<OnlineExamUser> {

  private final RedisService redisService;

  private static final String PREFIX = "exam-online-info:";

  @Override
  public void save(String paperId, OnlineExamUser onlineExamUser) {
    // exam-online-info-paperId
    this.redisService.lSet(PREFIX + paperId, onlineExamUser);
  }

  @Override
  public OnlineExamUser get(String paperId) {
    // 不开发
    return null;
  }

  public List<Object> list(String paperId) {
    return this.redisService.lGet(PREFIX + paperId, 0L, -1L);
  }

  public void remove(String paperId, OnlineExamUser onlineExamUser) {
    this.redisService.lRemove(PREFIX + paperId, 1L, onlineExamUser);
  }

  public Long countByPaperId(String paperId) {
    return this.redisService.lGetListSize(PREFIX + paperId);
  }

  public boolean checkOnlineUser(String username, String fullName, String paperId) {
    List<Object> ans = this.list(paperId);
    if (!ans.isEmpty()) {
      return ans.contains(new OnlineExamUser(username, fullName));
    } else {
      return false;
    }
  }

//  public static void main(String[] args) {
//    OnlineExamUser e = new OnlineExamUser("", "1");
//    OnlineExamUser e2 = new OnlineExamUser("", "1");
//    Set<OnlineExamUser> set = new HashSet<>();
//    set.add(e);
//    System.out.println(set.contains(e2));
//  }
}
