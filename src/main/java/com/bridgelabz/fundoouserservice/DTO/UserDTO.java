package com.bridgelabz.fundoouserservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;
@Data
@AllArgsConstructor
@ToString
public class UserDTO {
    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$",message = "Invalid Name...!")
    private String name;
    @Pattern(regexp = "(\\w+[.+-]?)*@\\w+(\\.+[a-zA-Z]{2,4})*", message = "Invalid Email, Enter correct Email")
    private String emailId;
    @Pattern(regexp = "(?=.*?[A-Z])(?=.*?\\d)(?=.*?[!@#$%^&*_()+-])[A-Za-z\\d!@#$%^&()*+_-]{8,}"
            , message = "Password must contain (One Upper Case, lower case,special character, numeric digits) and minimum of 8 characters")
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @NotNull
    private Date DOB;
    @Pattern(regexp = "^[6789][0-9]{9}$")
    private String phoneNo;
    private String profilePic;
    private boolean isActive;
    private boolean isDeleted;
}
