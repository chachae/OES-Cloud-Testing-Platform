package com.oes.common.core.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oes.common.core.constant.PageResultConstant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页工具
 *
 * @author chachae
 * @since 2019/12/21 11:01
 */
public class PageUtil {

  private PageUtil() {
  }

  /**
   * Page 数据处理，预防redis反序列化报错
   */
  public static <T> Map<String, Object> toPage(IPage<T> page) {
    // HashMap 初始化容量大小为2的幂次方，所以2^1=2
    Map<String, Object> map = new HashMap<>(1);
    map.put(PageResultConstant.ROWS, page.getRecords());
    map.put(PageResultConstant.TOTAL, page.getTotal());
    return map;
  }

  /**
   * Page 数据处理，预防redis反序列化报错
   */
  public static <T> Map<String, Object> toPage(T records, long total) {
    Map<String, Object> map = new HashMap<>(1);
    map.put(PageResultConstant.ROWS, records);
    map.put(PageResultConstant.TOTAL, total);
    return map;
  }

  /**
   * List<T> 分页
   */
  public static <T> Map<String, Object> toPage(int page, int size, List<T> list) {
    if (list == null || list.isEmpty()) {
      return toPage(null, 0);
    } else {
      int start = (page - 1) * size;
      int end = start + size;
      if (start > list.size()) {
        return toPage(null, 0);
      } else if (end >= list.size()) {
        return toPage(list.subList(start, list.size()), list.size());
      } else {
        return toPage(list.subList(start, end), list.size());
      }
    }
  }
}
