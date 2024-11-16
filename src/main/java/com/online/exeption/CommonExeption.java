package com.online.exeption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonExeption extends RuntimeException {
    private String message;
    private String errorCode;
    private String status;
    private String timestamp;
    private String path;

}
