package com.bridgelabz.fundoouserservice.repository;

import com.bridgelabz.fundoouserservice.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByEmailId(String email);
}
