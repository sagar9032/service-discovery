package com.discover.userservice.service;

import java.util.List;
import com.discover.userservice.model.User;

public interface UserService {
    User createUser(User newUser);
    User retrieveUserById(Long id);
    List<User> retrieveAllUsers();
    User updateUser(Long id, User editUser);
    void deleteById(Long id);
}