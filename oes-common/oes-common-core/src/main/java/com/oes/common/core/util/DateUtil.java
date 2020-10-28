package com.oes.common.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类
 *
 * @author chachae
 * @since 2020/45/27 9:21
 */
public class DateUtil {

  private DateUtil() {
  }

  public static final String FULL_TIME_PATTERN = "yyyyMMddHHmmss";

  public static final String FULL_TIME_SPLIT_PATTERN = "yyyy-MM-dd HH:mm:ss";

  public static final String CST_TIME_PATTERN = "EEE MMM dd HH:mm:ss zzz yyyy";

  public static final String DAY = "天";
  public static final String HOUR = "小时";
  public static final String MINUTE = "分钟";
  public static final String SECOND = "秒";

  /**
   * 格式化时间，格式为 yyyyMMddHHmmss
   *
   * @param localDateTime LocalDateTime
   * @return 格式化后的字符串
   */
  public static String formatFullTime(LocalDateTime localDateTime) {
    return formatFullTime(localDateTime, FULL_TIME_PATTERN);
  }

  /**
   * 格式化当前时间，格式为 yyyyMMddHHmmss
   *
   * @return 格式化后的字符串
   */
  public static String formatFullTimeNow() {
    return formatFullTime(LocalDateTime.now(), FULL_TIME_PATTERN);
  }

  /**
   * 根据传入的格式，格式化时间
   *
   * @param localDateTime LocalDateTime
   * @param format        格式
   * @return 格式化后的字符串
   */
  public static String formatFullTime(LocalDateTime localDateTime, String format) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
    return localDateTime.format(dateTimeFormatter);
  }

  /**
   * 格式化时间，格式为 yyyy-MM-dd HH:mm:ss
   *
   * @param date Date
   * @return 格式化后的字符串
   */
  public static synchronized String formatDate(Date date) {
    return getDateFormat(date, FULL_TIME_SPLIT_PATTERN);
  }

  /**
   * 根据传入的格式，格式化时间
   *
   * @param date   Date
   * @param format 格式
   * @return 格式化后的字符串
   */
  public static String getDateFormat(Date date, String format) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINA);
    return simpleDateFormat.format(date);
  }

  /**
   * 格式化 CST类型的时间字符串
   *
   * @param date   CST类型的时间字符串
   * @param format 格式
   * @return 格式化后的字符串
   * @throws ParseException 异常
   */
  public static String formatCstTime(String date, String format) throws ParseException {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CST_TIME_PATTERN, Locale.US);
    Date usDate = simpleDateFormat.parse(date);
    return getDateFormat(usDate, format);
  }

  /**
   * Date 转 LocalDateTime
   *
   * @param instant instant
   * @return LocalDateTime
   */
  public static LocalDateTime instantToLocalDateTime(Instant instant) {
    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
  }

  /**
   * 获取 Date 毫秒数
   *
   * @param date date
   * @return long
   */
  public static Long toEpochMilli(Date date) {
    return instantToLocalDateTime(date.toInstant()).atZone(ZoneId.systemDefault()).toInstant()
        .toEpochMilli();
  }

  /**
   * 获取 LocalDateTime 毫秒数
   *
   * @param localDateTime localDateTime
   * @return long
   */
  public static Long toEpochMilli(LocalDateTime localDateTime) {
    return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }


  /**
   * 格式化 Instant
   *
   * @param instant Instant
   * @param format  格式
   * @return 格式化后的字符串
   */
  public static String formatInstant(Instant instant, String format) {
    return instantToLocalDateTime(instant).format(DateTimeFormatter.ofPattern(format));
  }

  /**
   * 判断当前时间是否在指定时间范围
   *
   * @param from 开始时间
   * @param to   结束时间
   * @return 结果
   */
  public static boolean between(LocalTime from, LocalTime to) {
    LocalTime now = LocalTime.now();
    return now.isAfter(from) && now.isBefore(to);
  }

  /**
   * 计算耗时
   *  <pre>
   *    start：
   *  </pre>
   * @param startTime 开始时间
   * @param endTime   结束时间
   * @return 时间（%s天%s小时%s分%s秒）
   */
  public static String convertTimeConsume(Date startTime, Date endTime) {
    long cutMilli = DateUtil.toEpochMilli(endTime) - DateUtil.toEpochMilli(startTime);
    long day = cutMilli / 1000 / 60 / 60 / 24;
    long hr = cutMilli / 1000 / 60 / 60 % 24;
    long min = cutMilli / 1000 / 60 % 60;
    long sec = cutMilli / 1000 % 60;
    if (day != 0) {
      return String.format("%s天%s小时%s分%s秒", day, hr, min, sec);
    } else if (hr != 0) {
      return String.format("%s小时%s分%s秒", hr, min, sec);
    } else if (min != 0) {
      return String.format("%s分%s秒", min, sec);
    } else if (sec != 0) {
      return String.format("%s秒", sec);
    } else {
      return String.format("%s秒", 0);
    }
  }
}
