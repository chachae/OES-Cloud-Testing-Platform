package com.oes.server.exam.basic.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/19 20:37
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("score")
public class ScoreController {

}
