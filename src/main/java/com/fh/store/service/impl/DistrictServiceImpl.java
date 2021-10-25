package com.fh.store.service.impl;

import com.fh.store.entity.District;
import com.fh.store.mapper.DistrictMapper;
import com.fh.store.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictServiceImpl implements IDistrictService {
    @Autowired
    private DistrictMapper districtMapper;
    @Override
    public List<District> getByParent(String parent) {
        List<District> districtList = districtMapper.findByParent(parent);
        //将无效数据设置为空 再传输数据，可以节省流量 提升效率
        for (District district : districtList) {
            district.setId(null);
            district.setParent(null);
        }
        return districtList;
    }

    @Override
    public String getNameByCode(String code) {
        return districtMapper.findNameByCode(code);
    }
}
