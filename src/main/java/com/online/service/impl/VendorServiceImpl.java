package com.online.service.impl;

import com.online.domain.VendorDetail;
import com.online.repository.VendorDao;
import com.online.resource.VenderResourse;
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


}
