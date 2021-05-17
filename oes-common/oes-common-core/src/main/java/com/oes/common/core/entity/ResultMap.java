/*
 *
 * Copyright 2017-2021 chachae@foxmail.com(chenyuexin)
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.oes.common.core.entity;

import java.util.HashMap;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chachae
 * @date 2021/2/15
 * @since v1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResultMap extends HashMap<String, Object> {

  private static final long serialVersionUID = -8422981954211556220L;

  public ResultMap putKV(String key, Object value) {
    this.put(key, value);
    return this;
  }

}
