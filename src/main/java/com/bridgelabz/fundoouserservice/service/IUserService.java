package com.bridgelabz.fundoouserservice.service;

import com.bridgelabz.fundoouserservice.DTO.UserDTO;
import com.bridgelabz.fundoouserservice.util.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface IUserService {
    Response register(UserDTO userDTO);

    Response getUser(long id);

    Response getAllUserData(String token);

    Response updateUser(long id, UserDTO userDTO, String token);

    Response login(String email, String password);

    Response changePassword(long id, String token, String newPassword);

    Response resetPassword(String emailId);

    Response addProfilePic(MultipartFile profilePath, String token);

    Response deleteUser(Long id, String token);

    Response deletePermanently(Long id, String token);

    Response restore(String token, Long id);
}
