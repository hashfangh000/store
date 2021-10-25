package com.fh.store.controller;

import com.fh.store.entity.District;
import com.fh.store.service.IDistrictService;
import com.fh.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("districts")

public class DistrictController extends BaseController{

    @Autowired
    private IDistrictService districtService;

    @GetMapping({"","/"})
    public JsonResult<List<District>> getByParent(String parent){
        List<District> data = districtService.getByParent(parent);
        return new JsonResult<>(OK,data);
    }
}
