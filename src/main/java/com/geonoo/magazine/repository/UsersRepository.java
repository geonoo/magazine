package com.geonoo.magazine.repository;

import com.geonoo.magazine.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
        Optional<Users> findByEmail(String email);
}
