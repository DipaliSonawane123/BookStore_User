package com.example.userregistrations.service;

import com.example.userregistrations.Repo.UserRepo;
import com.example.userregistrations.dto.LoginDto;
import com.example.userregistrations.dto.UserDto;
import com.example.userregistrations.exception.userException;
import com.example.userregistrations.model.User;
import com.example.userregistrations.util.EmailSenderService;
import com.example.userregistrations.util.TokenUtil;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements Iuserservice {
    @Autowired
    UserRepo userRepo;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    EmailSenderService emailSender;

    @Override
    public String insertRecord(UserDto userDto) throws userException {
        User user = new User(userDto);
        userRepo.save(user);
        String token = tokenUtil.createToken(user.getUserId());
        emailSender.sendEmail(user.getEmail(), "Added Your Details", "http://localhost:8080/user/retrieve/" + token);
        return token;
    }
    @Override
    public User FindById(int id) {
        Optional<User> user = userRepo.findById(id);
        if(user.isPresent())
            return user.get();
        else{
            throw new userException("Id is not found");
        }
    }

    @Override
    public User getByEmail(String email) {
        User user = userRepo.findByEmail(email);
        return user;
    }

    @Override
    public User editByEmail(UserDto userDTO, String email) {
        User editdata = userRepo.findByEmail(email);
        if (editdata != null) {
            editdata.setFirstName(userDTO.getFirstName());
            editdata.setLastName(userDTO.getLastName());
            editdata.setEmail(userDTO.getEmail());
            editdata.setAddress(userDTO.getAddress());
            editdata.setDOB(userDTO.getDOB());
            editdata.setPassword(userDTO.getPassword());
            User user = userRepo.save(editdata);
            String token = tokenUtil.createToken(editdata.getUserId());
            emailSender.sendEmail(editdata.getEmail(), "Added Your Details/n", "http://localhost:8080/user/retrieve/" + token);
            return user;
        } else {
            throw new userException("email:" + email + " is not present ");
        }

    }

    @Override
    public User getDataByToken(String token) {
        int Userid = tokenUtil.decodeToken(token);
        Optional<User> existingData = userRepo.findById(Userid);
        if (existingData.isPresent()) {
            return existingData.get();
        } else
            throw new userException("Invalid Token");
    }

    @Override
    public User loginUser(LoginDto loginDto) {
        Optional<User> userDetails = Optional.ofNullable(userRepo.findByEmail(loginDto.getEmail()));
        if (userDetails.isPresent()) {
            //String pass = login.get().getPassword();
            if (userDetails.get().getPassword().equals(loginDto.getPassword())) {
                emailSender.sendEmail(userDetails.get().getEmail(), "About Login", "Login Successful!");
                return userDetails.get();
            } else
                emailSender.sendEmail(userDetails.get().getEmail(), "About Login", "Invalid password!");
            throw new userException("Wrong Password!");
        } else
            throw new userException("Login Failed, Wrong email or password!!!");
    }

    @Override
    public String forgotPassword(String email) {
        User editdata = userRepo.findByEmail(email);
        if (editdata != null) {
            emailSender.sendEmail(editdata.getEmail(), "About Login", "http://localhost:8080/user/resetPassword/"+email);
            return "Reset link send sucessfully";
        } else
            throw new userException("Login Failed, Wrong email or password!!!");
    }

    @Override
    public String resetPassword(LoginDto loginDTO) {
        Optional<User> userDetails = Optional.ofNullable(userRepo.findByEmail(loginDTO.getEmail()));
        String password = loginDTO.getPassword();
        if (userDetails.isPresent()) {
            userDetails.get().setPassword(password);
            userRepo.save(userDetails.get());
            return "Password Changed";
        } else
            return "Invalid Email Address";
    }


    @Override
    public User findAll() {
        List<User> user = userRepo.findAll();
        return user.get(0);

    }
    @Override
    public String deleteByid(int id, String token) {
        Optional<User> user =userRepo.findById(id);
        int userid = tokenUtil.decodeToken(token);
        Optional <User> userToken =userRepo.findById(userid);
        if(user.get().getFirstName().equals(userToken.get().getFirstName())){
            userRepo.deleteById(id);
            return user.get()+"User is deleted for this ID";
        }
        else
            throw new userException("Data is not match");

    }

    @Override
    public User FindByIds(int id) {
        Optional<User> user = userRepo.findById(id);
        if(user.isPresent())
            return user.get();
        else{
          return null;
        }

    }
}












