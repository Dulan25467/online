package com.online.repository;

import com.online.domain.VendorDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorDao extends JpaRepository<VendorDetail,Long> {
    List<VendorDetail> findAll();

}
