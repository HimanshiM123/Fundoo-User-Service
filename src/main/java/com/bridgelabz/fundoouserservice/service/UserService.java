package com.bridgelabz.fundoouserservice.service;

import com.bridgelabz.fundoouserservice.DTO.UserDTO;
import com.bridgelabz.fundoouserservice.exception.UserException;
import com.bridgelabz.fundoouserservice.model.UserModel;
import com.bridgelabz.fundoouserservice.repository.IUserRepository;
import com.bridgelabz.fundoouserservice.util.Response;
import com.bridgelabz.fundoouserservice.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService{
    @Autowired
    IUserRepository repository;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MailService mailService;
    @Override
    public Response register(UserDTO userDTO) {
        UserModel userModel = new UserModel(userDTO);
        userModel.setCreatedAt(LocalDateTime.now());
        repository.save(userModel);
        String body = "User Added Successfully with id :" + userModel.getId();
        String subject = "User Added Successfully ";
        mailService.send(userModel.getEmailId(), body, subject);
        return new Response("User Added successfully", 200, userModel);
    }

    @Override
    public Response getUser(long id) {
        Optional<UserModel> userModel = repository.findById(id);
        return new Response("User Added successfully", 200, userModel.get());
    }

    @Override
    public Response getAllUserData(String token) {
       Long userId = tokenUtil.decodeToken(token);
       Optional<UserModel> isUserPresent = repository.findById(userId);
       if (isUserPresent.isPresent()){
           List<UserModel> getAllUserData = repository.findAll();
           if (getAllUserData.size() > 0)
                 return new Response("User Added successfully", 200, getAllUserData);
           else
               throw new UserException(400, "No data Found");
       }
       throw new UserException(400, "Token Wrong");
    }

    @Override
    public Response updateUser(long id, UserDTO userDTO, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isUserPresent = repository.findById(userId);
        if (isUserPresent.isPresent()){
            isUserPresent.get().setName(userDTO.getName());
            isUserPresent.get().setEmailId(userDTO.getEmailId());
            isUserPresent.get().setPassword(userDTO.getPassword());
            isUserPresent.get().setCreatedAt(userDTO.getCreatedAt());
            isUserPresent.get().setUpdatedAt(userDTO.getUpdatedAt());
            isUserPresent.get().setDOB(userDTO.getDOB());
            isUserPresent.get().setPhoneNo(userDTO.getPhoneNo());
            isUserPresent.get().setProfilePic(userDTO.getProfilePic());
            String body = "User updated Successfully with id  :" + isUserPresent.get().getId();
            String subject = "User updated Successfully....";
            mailService.send(isUserPresent.get().getEmailId(), body, subject);
            return new Response("Update Admin Details", 200, isUserPresent.get());
        }
        throw new UserException(400, "Not Found");
    }

    @Override
    public Response login(String email, String password) {
        Optional<UserModel> isEmailPresent = repository.findByEmailId(email);
        if (isEmailPresent.isPresent()) {
            if (isEmailPresent.get().getPassword().equals(password)) {
                String token = tokenUtil.crateToken(isEmailPresent.get().getId());
                return new Response("Login successful", 200, token);
            }
            throw new UserException(400, "Invalid Credential");
        }
        throw new UserException(400, "User Not Found");
    }

    @Override
    public Response changePassword(long id, String token, String newPassword) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isIdPresent = repository.findById(userId);
        if (isIdPresent.isPresent()){
            isIdPresent.get().setPassword(passwordEncoder.encode(newPassword));
            repository.save(isIdPresent.get());
            return new Response("Password Changed Successfully", 200, isIdPresent.get());
        } else {
            throw new UserException(400, "User Not Found");
        }
    }

    @Override
    public Response resetPassword(String emailId) {
        Optional<UserModel> isEmailPresent = repository.findByEmailId(emailId);
        if (isEmailPresent.isPresent()){
            String token = tokenUtil.crateToken(isEmailPresent.get().getId());
            String url = "http://localhost:8083/user/resetPassword" + token;
            String subject = "Reset Password";
            String body = "To Reset Password Click This Link\n" + url + "For Reset Use this Token\n" + token;
            mailService.send(isEmailPresent.get().getEmailId(), url, subject);
            return new Response("Reset Password", 200, isEmailPresent);
        }
        throw new UserException(400, "Email not Found");
    }

    @Override
    public Response addProfilePic(MultipartFile profilePath, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isUserPresent = repository.findById(userId);
            if (isUserPresent.isPresent()) {
                isUserPresent.get().setProfilePic(String.valueOf(profilePath));
                repository.save(isUserPresent.get());
                String body = "user Profile Path change successfully with id" + isUserPresent.get().getId();
                String subject = "User Profile Path change successfully in LMS";
                mailService.send(isUserPresent.get().getEmailId(), subject, body);
                return new Response("successfully validate", 200, isUserPresent);
            }
        throw new UserException(400, "Admin not found");
    }

    @Override
    public Response deletePermanently(Long id, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isUserPresent = repository.findById(userId);
        if (isUserPresent.isPresent()){
            repository.delete(isUserPresent.get());
            return new Response("User moved in trash", 200, isUserPresent);
        }
        throw new UserException(400, "User Not Found");
    }

    @Override
    public Response deleteUser(Long id, String token) {

        Long userId = tokenUtil.decodeToken(token);
        Optional<UserModel> userModel = repository.findById(userId);
        if (userModel.isPresent()){
            if (userModel.get().isDeleted()==true){
                userModel.get().setActive(false);
                userModel.get().setDeleted(true);
               repository.save(userModel.get());
               return new Response("Deleted User Permanently", 200, null);
            } else {
                return new Response("User Not Found", 200, null);
            }
        }
        throw new UserException(400, "Token Wrong");
    }

    @Override
    public Response restore(String token, Long id) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserModel> userModel = repository.findById(userId);
        if (userModel.isPresent()){
            if (userModel.get().isDeleted()==true){
                userModel.get().setActive(true);
                userModel.get().setDeleted(false);
                repository.save(userModel.get());
                return new Response("User Restored", 200, null);
            } else {
                return new Response("User Not Found", 200, null);
            }
        }
        throw new UserException(400, "Token Wrong");
    }
}
