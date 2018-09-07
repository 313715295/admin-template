package com.admin.module.controller;


import com.admin.module.entity.City;
import com.admin.module.service.CityService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.admin.commons.base.BaseController;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zwq
 * @since 2018-09-06
 */
@RestController
@RequestMapping("/city")
public class CityController extends BaseController {

    @Resource
    private CityService service;

    @RequestMapping("/get")
    public City get() {
        return service.getById(1);
    }
}

