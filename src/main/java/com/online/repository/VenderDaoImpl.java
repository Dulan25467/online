package com.online.repository;

import com.online.domain.VendorDetail;
import com.online.resourse.VenderResourse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VenderDaoImpl  {
    @Autowired
    private  VendorDao vendorDao;
    @Autowired
    private ModelMapper modelMapper;

    public List<VenderResourse> getAllVenders(){
        List<VendorDetail> venderList= vendorDao.findAll();
            return modelMapper.map(venderList,new TypeToken<List<VenderResourse>>(){}.getType());

    }
    public VenderResourse addVender(VenderResourse venderResourse){
        vendorDao.save(modelMapper.map(venderResourse,VendorDetail.class));
        return venderResourse;
    }
    public VenderResourse updateVender(VenderResourse venderResourse){
        vendorDao.save(modelMapper.map(venderResourse,VendorDetail.class));
        return venderResourse;
    }
    public void deleteVender(int id){
        vendorDao.deleteById(id);
    }
    public VenderResourse getVender(int id){
        return modelMapper.map(vendorDao.findById(id),VenderResourse.class);
    }


}
