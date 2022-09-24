package com.example.userregistrations.controller;

import com.example.userregistrations.dto.LoginDto;
import com.example.userregistrations.dto.ResponseDto;
import com.example.userregistrations.dto.UserDto;
import com.example.userregistrations.model.User;
import com.example.userregistrations.service.Iuserservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
    @RequestMapping("/user")
    public class UserController {
        @Autowired
        Iuserservice service;
        /**
         * Post Api for insert user data
         */
        @PostMapping("/insert")
        public ResponseEntity<ResponseDto> AddUserDetails(@Valid @RequestBody UserDto userDto) {
            String token = service.insertRecord(userDto);
            ResponseDto respDTO = new ResponseDto("*** Data Added successfully ***", token);
            return new ResponseEntity(respDTO, HttpStatus.CREATED);
        }
        /**
         * Get Api for geting All user
         */
        @GetMapping("/findAll")
        public ResponseEntity<ResponseDto> findAllUser() {
            User response = service.findAll();
            ResponseDto responseDTO = new ResponseDto("** All User List ** ", response);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        }

        /**
         * Get Api for retrieve user by id
         */
        @GetMapping("/get/{Id}")
        public ResponseEntity<ResponseDto> FindById(@PathVariable int Id) {
            User response = service.FindById(Id);
            ResponseDto responseDto = new ResponseDto("***All Details of Person using Id***", response);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
    @GetMapping("/gets/{Id}")
    public User FindByIds(@PathVariable int Id) {
        User response = service.FindByIds(Id);
        return response;
    }

        // dipssonawane123+dips@gmail.com
        /**
         * Get Api for retrieve user data by email
         */
        @GetMapping("/email/{email}")
        public ResponseEntity<ResponseDto> getDataByemail(@PathVariable String email) {
            User response = service.getByEmail(email);
            ResponseDto respDTO = new ResponseDto("*** Data by using email ***", response);
            return new ResponseEntity<>(respDTO, HttpStatus.OK);
        }
        /**
         * Put Api for uadate data by email
         */
        @PutMapping("/edit/{email}")
        public ResponseEntity<ResponseDto> updateById(@Valid @RequestBody UserDto userDTO, @PathVariable String email) {
            User response = service.editByEmail(userDTO, email);
            ResponseDto respDTO = new ResponseDto(" **** Person details is updated *****", response);
            return new ResponseEntity<>(respDTO, HttpStatus.OK);
        }
        /**
         * Post Api for retrieve  user by token
         */
        @GetMapping("/retrieve/{token}")
        public ResponseEntity<ResponseDto> getUserDetails(@Valid @PathVariable String token) {
            User response = service.getDataByToken(token);
            ResponseDto respDTO = new ResponseDto("Data retrieved successfully", response);
            return new ResponseEntity(respDTO, HttpStatus.CREATED);
        }

        /**
         * Post Api for Login for particular user
         */
        @PostMapping("/login")
        public ResponseEntity<ResponseDto> loginUser(@RequestBody LoginDto loginDTO) {
            User response = service.loginUser(loginDTO);
            ResponseDto responseDTO = new ResponseDto("Login Successful!", response);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        /**
         * Get Api for Forgot password with email*/
        @GetMapping("/forgotpassword/{email}")
        public ResponseEntity<ResponseDto> forgotPasswordByemail(@PathVariable String email) {
            String response = service.forgotPassword(email);
            ResponseDto respDTO = new ResponseDto("*** Link send successfully ***", response);
            return new ResponseEntity<>(respDTO, HttpStatus.OK);
        }
        /**
         * Post Api for resetPassword user data
         */
        @PostMapping("/resetPassword")
        public ResponseEntity<String> resetPassword(@RequestBody LoginDto loginDto) {
            String response = service.resetPassword(loginDto);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        /**
         * Delete Api for deleting data using token and id match with first name
         */
        @DeleteMapping("/delete/{id}/{token}")
        public ResponseEntity<ResponseDto> retriveData(@PathVariable int id ,@PathVariable String token ){
            String user =service.deleteByid(id,token);
            ResponseDto response =new ResponseDto("Delete data by token",user);
            return new  ResponseEntity(response,HttpStatus.ACCEPTED);
        }
    }

