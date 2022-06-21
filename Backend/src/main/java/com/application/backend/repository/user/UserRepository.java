package com.application.backend.repository.user;

import com.application.backend.entities.User;

import java.util.List;

public interface UserRepository {
    List<User> allUser();
    User addUser(User user);
    User findUser(String email);
    void userActivity(String email);
    User updateUser(User user, String email);
    void deleteUser(String email);
}
