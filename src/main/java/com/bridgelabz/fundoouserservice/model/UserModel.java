package com.bridgelabz.fundoouserservice.model;

import com.bridgelabz.fundoouserservice.DTO.UserDTO;
import lombok.Data;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "User")
@Data
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String emailId;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Date DOB;
    private String phoneNo;
    private String profilePic;
    private boolean isActive;
    private boolean isDeleted;

    public UserModel(UserDTO userDTO) {
        this.name = userDTO.getName();
        this.emailId = userDTO.getEmailId();
        this.password = userDTO.getPassword();
        this.createdAt = userDTO.getCreatedAt();
        this.updatedAt = userDTO.getUpdatedAt();
        this.DOB = userDTO.getDOB();
        this.phoneNo = userDTO.getPhoneNo();
        this.profilePic = userDTO.getProfilePic();
        this.isActive = userDTO.isActive();
        this.isDeleted = userDTO.isDeleted();
    }

    public UserModel() {

    }
}
