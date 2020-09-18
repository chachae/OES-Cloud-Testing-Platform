package com.oes.server.exam.online.service;

import com.oes.server.exam.online.entity.OnlineExamUser;
import java.util.List;

/**
 *
 * @author chachae
 * @date 2020/9/14 09:43
 * @version v1.0
 */
public interface IExamOnlineInfoService {

  /**
   * 保存在线用户信息
   *
   * @param username 用户名
   * @param fullName 真实姓名
   * @param paperId 试卷编号
   */
  void saveInfo(String username, String fullName, String paperId);

  /**
   * 检查学生是否已经在线
   *
   * @param username 用户名
   * @param paperId 试卷编号
   * @param fullName 真实姓名
   */
  boolean checkOnlineUser(String username, String fullName, String paperId);

  /**
   * 移除缓存
   *
   * @param paperId 试卷编号
   * @param onlineExamUser value
   */
  void removeInfo(String paperId, OnlineExamUser onlineExamUser);

  /**
   * 获取某场考试的在线考试列表
   *
   * @return 集合
   */
  List<Object> listByPaperId(String paperId);

  /**
   * 统计某个set缓存的长度（某场考试在线考试考生人数）
   *
   * @param paperId 试卷编号
   * @return 人数
   */
  Long countByPaperId(String paperId);
}
