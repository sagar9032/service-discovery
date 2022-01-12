package com.discover.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.discover.userservice.model.User;

public interface UserRepository extends JpaRepository<User, Long> {}