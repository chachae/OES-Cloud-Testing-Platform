package com.oes.common.core.validator;

import javax.validation.groups.Default;

/**
 * 新增校验分组
 * 继承 {@link Default} 可以让没有指定分组的中字段属性默认加入校验，{@link Update} 更新分组同理。
 *
 * @author chachae
 * @date 2020/9/28 20:19
 * @version v1.0
 */
public interface Insert extends Default {

}
