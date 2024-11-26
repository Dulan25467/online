package com.online.service;

import com.online.resource.UserResourse;

public interface UserService {
    UserResourse registerUser(UserResourse UserResourse);
    UserResourse login(String username, String password);
}
