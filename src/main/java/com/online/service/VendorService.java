package com.online.service;

import com.online.resourse.VenderResourse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VendorService {
    List<VenderResourse> getAllVenders();
    VenderResourse getVender(int id);
    VenderResourse addVender(VenderResourse venderResourse);
    VenderResourse updateVender(VenderResourse venderResourse);
    void deleteVender(int id);
}
