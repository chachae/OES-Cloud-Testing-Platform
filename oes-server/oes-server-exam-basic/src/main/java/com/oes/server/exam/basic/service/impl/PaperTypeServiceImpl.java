package com.oes.server.exam.basic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.entity.exam.PaperType;
import com.oes.server.exam.basic.mapper.PaperTypeMapper;
import com.oes.server.exam.basic.service.IPaperTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PaperTypeServiceImpl extends ServiceImpl<PaperTypeMapper, PaperType> implements
    IPaperTypeService {

}