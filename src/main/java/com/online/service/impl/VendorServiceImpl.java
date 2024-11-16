package com.online.service.impl;

import com.online.domain.VendorDetail;
import com.online.repository.VendorDao;
import com.online.resourse.VenderResourse;
import com.online.service.VendorService;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Data
@Transactional
@Service
public class VendorServiceImpl implements VendorService {

    private final VendorDao vendorDao;
    private final ModelMapper modelMapper;

    @Autowired
    public VendorServiceImpl(VendorDao vendorDao, ModelMapper modelMapper) {
        this.vendorDao = vendorDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<VenderResourse> getAllVenders() {
        List<VendorDetail> vendorList = vendorDao.findAll();
        return modelMapper.map(vendorList, new TypeToken<List<VenderResourse>>() {}.getType());
    }

    @Override
    public VenderResourse addVender(VenderResourse venderResourse) {
        VendorDetail savedVendor = vendorDao.save(modelMapper.map(venderResourse, VendorDetail.class));
        return modelMapper.map(savedVendor, VenderResourse.class);
    }

    @Override
    public VenderResourse updateVender(VenderResourse venderResourse) {
        VendorDetail updatedVendor = vendorDao.save(modelMapper.map(venderResourse, VendorDetail.class));
        return modelMapper.map(updatedVendor, VenderResourse.class);
    }

    @Override
    public void deleteVender(int id) {
        vendorDao.deleteById(id);
    }

    @Override
    public VenderResourse getVender(int id) {
        Optional<VendorDetail> vendorDetail = vendorDao.findById(id);
        return vendorDetail.map(vendor -> modelMapper.map(vendor, VenderResourse.class))
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
    }
}
