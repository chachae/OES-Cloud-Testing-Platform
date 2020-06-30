package com.oes.ai.ocr.common.selector;

import com.oes.ai.ocr.common.entity.BaseOcrEntity;
import com.oes.ai.ocr.common.entity.IdCardInfo;

/**
 * 统一策略接口
 *
 * @author chachae
 * @version v1.0
 * @date 2020/6/30 01:18
 */
public interface OcrIdCardServiceStrategy {

  IdCardInfo ocrIdCard(BaseOcrEntity entity);

}
