package com.example.userregistrations.dto;

import com.example.userregistrations.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDto {
    private String message;
    private Object object;

    public ResponseDto(String s, String response) {
        this.message = s;
        this.object = response;
    }

    public ResponseDto(String s, User details) {
        this.message = s;
        this.object = details;
    }
}






