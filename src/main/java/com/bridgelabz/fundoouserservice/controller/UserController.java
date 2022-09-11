package com.bridgelabz.fundoouserservice.controller;

import com.bridgelabz.fundoouserservice.DTO.UserDTO;
import com.bridgelabz.fundoouserservice.service.IUserService;
import com.bridgelabz.fundoouserservice.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/*
      Purpose : UserController to process Data API's
      @author : Himanshi Mohabe
      version : 1.0
     */

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;


    /*
     *@Purpose:to add admin details into the Admin Repository
     * @Param : AdminDTO
     */

    @PostMapping(value = "/registerUser")
    ResponseEntity<Response> registerUser(@Valid @RequestBody UserDTO userDTO) {
        Response response = userService.register(userDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

     /*
     *@Purpose : to get list of admin details in the Admin Repository using id
      @Param  : id
     */

    @GetMapping("/getUser/{id}")
    ResponseEntity<Response> getUser(@PathVariable long id){
        Response response = userService.getUser(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     *@Purpose : Ability to get list of admin details in the Admin Repository
      @Param : token
     */

    @GetMapping("/getUser")
    ResponseEntity<Response> getAllAdmin(@RequestHeader String token){
        Response response = userService.getAllUserData(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     @Purpose : Able to update admin details into the Admin Repository
     @Param : AdminDTO, id and token
     */

    @PutMapping("updateUser/{id}")
    ResponseEntity<Response> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable long id, @RequestHeader String token ){
        Response response = userService.updateUser(id, userDTO, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

     /*
     @Purpose : Able to Access existing admin details by using login in the Admin Repository
     @Param : email and password
     */

    @PostMapping("/login")
    ResponseEntity<Response> login(@RequestParam String email, @RequestParam String password){
        Response response = userService.login(email, password);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     @Purpose : Ability to Update Password of admin in the Admin Repository
     @Param : token and newPassword
     */

    @PutMapping("/changePassword")
    ResponseEntity<Response> changePassword(@PathVariable long id, @RequestHeader String token, @RequestParam String newPassword){
        Response response = userService.changePassword(id, token, newPassword);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     @Purpose : Able to Reset Password of admin in the Admin Repository
     @Param : EmailId
     */

    @PutMapping("/resetPassword")
    ResponseEntity<Response> resetPassword(@RequestParam String emailId){
        Response response = userService.resetPassword(emailId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    @Purpose : Ability to Add Profile of admin user in the Admin Repository
    @Param : id and token
    */
    @PostMapping("/addprofilepic")
    ResponseEntity<Response> addProfilePic(@RequestParam(value = "File") MultipartFile profilePath, @PathVariable String token) {
        Response response = userService.addProfilePic(profilePath, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/deleteUser/{id}")
    ResponseEntity<Response> deleteUser(@PathVariable Long id, @RequestHeader String token){
        Response response = userService.deleteUser(id, token);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @DeleteMapping("/deletePermanently")
    ResponseEntity<Response> deletePermanently(@PathVariable Long id, @RequestHeader String token){
        Response response = userService.deletePermanently(id, token);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
