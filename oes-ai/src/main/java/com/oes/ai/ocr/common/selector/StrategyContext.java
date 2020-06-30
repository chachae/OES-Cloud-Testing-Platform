package com.oes.ai.ocr.common.selector;

import cn.hutool.core.util.StrUtil;
import com.oes.ai.ocr.common.entity.BaseOcrEntity;
import com.oes.ai.ocr.common.entity.IdCardInfo;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * OCR光学识别接口选择器
 *
 * @author chachae
 * @version v1.0
 * @date 2020/6/30 14:17
 */
@Component
public class StrategyContext {

  @Value("${oes.ocr.supplier}")
  private String supplier;

  /**
   * key:value => beanName:interface
   */
  private final Map<String, OcrIdCardServiceStrategy> strategyMap = new ConcurrentHashMap<>();

  @Autowired
  public StrategyContext(Map<String, OcrIdCardServiceStrategy> strategyMap) {
    strategyMap.forEach(this.strategyMap::put);
  }

  public IdCardInfo ocrIdCard(BaseOcrEntity entity) {
    if (StrUtil.isNotBlank(supplier)) {
      String beanName = SupplierEnum.getBeanName(supplier);
      return strategyMap.get(beanName).ocrIdCard(entity);
    }
    return null;
  }

}
