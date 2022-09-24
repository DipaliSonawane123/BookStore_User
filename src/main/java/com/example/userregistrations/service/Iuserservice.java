package com.example.userregistrations.service;

import com.example.userregistrations.dto.LoginDto;
import com.example.userregistrations.dto.UserDto;
import com.example.userregistrations.model.User;

public interface Iuserservice {
    String insertRecord(UserDto addressDto);



    User FindById(int id);

    User getByEmail(String email);

    User editByEmail(UserDto userDTO, String email_address);

    User getDataByToken(String token);

    User loginUser(LoginDto loginDto);

    String forgotPassword(String email);

    String resetPassword(LoginDto loginDto);

    User findAll();

    String deleteByid(int id, String token);

    User FindByIds(int id);
}

