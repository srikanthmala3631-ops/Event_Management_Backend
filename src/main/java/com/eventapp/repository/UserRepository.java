package com.eventapp.repository;

import com.eventapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA gives us basic CRUD methods (save, findAll, findById, delete...)
 * for free just by extending JpaRepository. We only need to declare extra
 * finder methods, like findByEmail below, and Spring writes the SQL for us.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
