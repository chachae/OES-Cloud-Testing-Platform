package com.oes.ai.function.ocr;

import com.oes.ai.entity.ocr.IdCardInfo;
import com.oes.ai.entity.ocr.QueryOcrEntity;

/**
 * 统一策略接口
 *
 * @author chachae
 * @version v1.0
 * @date 2020/6/30 01:18
 */
public interface IOcrIdCardServiceStrategy {

  IdCardInfo ocrIdCard(QueryOcrEntity entity);

}
