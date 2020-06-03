package com.example.tutoperso.service;

import com.example.tutoperso.exception.UserAlreadyExistException;
import com.example.tutoperso.exception.UserNotFoundException;
import com.example.tutoperso.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IUserService
{
    User createUser(User user) throws UserAlreadyExistException;
    User updateUser(User newUser) throws UserNotFoundException;
    void deleteUser(String username) throws UserNotFoundException;
    User getUser(String username) throws UserNotFoundException;
    Page<User> getAllUsers(int page, int size);
}
