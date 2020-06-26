package com.oes.common.core.cache;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/24 21:06
 */
public interface ICacheService<E> {

  void save(String key, E e);

  default E get(String key) {
    return null;
  }

}
