package com.splitspend.auth.repository;

import com.splitspend.auth.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(@NotNull @Email String email);
}
