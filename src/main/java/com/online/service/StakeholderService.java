package com.online.service;

import com.online.resource.StakeholderResourse;
import org.springframework.stereotype.Service;


@Service
public interface StakeholderService {
    StakeholderResourse login(String username, String password);
    StakeholderResourse register(StakeholderResourse stakeholderResourse);
    StakeholderResourse delete(int id);
    StakeholderResourse update(StakeholderResourse stakeholderResourse);

}
