package com.example.tutoperso.service;

import com.example.tutoperso.exception.UserAlreadyExistException;
import com.example.tutoperso.exception.UserNotFoundException;
import com.example.tutoperso.model.User;
import com.example.tutoperso.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional //Lire doc
public class UserService implements IUserService
{
    @Autowired
    UserRepository userRepository;

    @Override
    public User createUser(User user) throws UserAlreadyExistException {
        userRepository.findOneByUsername(user.getUsername().toLowerCase()).ifPresent(existingUser -> {
            throw new UserAlreadyExistException("Ce pseudo exitse déjà !");
        });
        User userToAdd = new User(user.getNom(), user.getPrenom(), user.getUsername(), user.getMotDePasse(), user.getAdresse(), user.getEmail());
        userRepository.save(userToAdd);
        return userToAdd;
    }

    @Override
    public User updateUser(Long id, User userDetail) throws UserNotFoundException {
        User userToUpdate = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        userRepository.findOneByUsername(userDetail.getUsername().toLowerCase()).ifPresent(existingUser -> {
            if(existingUser.getId() != userToUpdate.getId())
                throw new UserAlreadyExistException("Ce pseudo existe déjà !");
        });
        User user = new User(userDetail.getNom(), userDetail.getPrenom(), userDetail.getUsername(), userDetail.getMotDePasse(), userDetail.getAdresse(), userDetail.getEmail());
        user.setId(userToUpdate.getId());
        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    @Override
    public void deleteUser(Long id) throws UserNotFoundException {
        User userToDelete = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        userRepository.delete(userToDelete);
    }

    @Override
    public User getUser(Long id) throws UserNotFoundException {
        User found = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable !"));
        return found;
    }

    @Override
    public Page<User> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }
}
