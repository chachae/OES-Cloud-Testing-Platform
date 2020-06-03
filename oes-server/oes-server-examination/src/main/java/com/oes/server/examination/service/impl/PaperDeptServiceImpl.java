package com.oes.server.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.server.examination.entity.system.PaperDept;
import com.oes.server.examination.mapper.PaperDeptMapper;
import com.oes.server.examination.service.IPaperDeptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PaperDeptServiceImpl extends ServiceImpl<PaperDeptMapper, PaperDept> implements
    IPaperDeptService {

}