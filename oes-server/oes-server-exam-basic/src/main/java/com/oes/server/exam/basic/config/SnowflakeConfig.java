package com.oes.server.exam.basic.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/8/10 17:25
 */
@Component
public class SnowflakeConfig {

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private long workerId = 0;
  private static final long DATA_CENTER_ID = 1;
  private final Snowflake snowflake = IdUtil.createSnowflake(workerId, DATA_CENTER_ID);

  @PostConstruct
  public void init() {
    workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
  }

  public synchronized long getId() {
    return snowflake.nextId();
  }

  public synchronized long snowflakeId(long workerId, long datacenterId) {
    return IdUtil.createSnowflake(workerId, datacenterId).nextId();
  }
}
