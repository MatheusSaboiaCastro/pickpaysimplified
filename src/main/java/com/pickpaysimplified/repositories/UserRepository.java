package com.pickpaysimplified.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pickpaysimplified.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByDocument(String document);

    Optional<User> findUserById(Long id);

}
