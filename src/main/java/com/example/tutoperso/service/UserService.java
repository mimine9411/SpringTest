package com.example.tutoperso.service;

import com.example.tutoperso.exception.*;
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
    public User createUser(User user) throws UserAlreadyExistException, NotValidUsernameException {
        if(user.getUsername().isEmpty())
            throw new NotValidUsernameException("Le pseudo ne peut être vide !");
        userRepository.findOneByUsername(user.getUsername().toLowerCase()).ifPresent(existingUser -> {
            throw new UserAlreadyExistException("Ce pseudo exitse déjà !");
        });
        User userToAdd = new User(user.getNom(), user.getPrenom(), user.getUsername(), user.getMotDePasse(), user.getAdresse(), user.getEmail());
        userRepository.save(userToAdd);
        return userToAdd;
    }

    @Override
    public User updateUser(User userDetail) throws UserNotFoundException {
        User userFound = userRepository.findOneByUsername(userDetail.getUsername()).orElseThrow(()->new UserNotFoundException("Utilisateur introuvable !"));
        userDetail.setId(userFound.getId());
        User updatedUser = userRepository.save(userDetail);
        return updatedUser;
    }

    @Override
    public void deleteUser(String username) throws UserNotFoundException {
        User userToDelete = userRepository.findOneByUsername(username).orElseThrow(() -> new UserNotFoundException());
        userRepository.delete(userToDelete);
    }

    @Override
    public User getUser(String username) throws UserNotFoundException {
        return userRepository.findOneByUsername(username).orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable !"));
    }

    @Override
    public Page<User> getAllUsers(int page, int size) throws IncorrectPageParamException, IncorrectSizePageParamException{
        if(page < 0)
            throw new IncorrectPageParamException("Le nombre de page doit être positif !");
        if(size < 1)
            throw new IncorrectSizePageParamException("La page doit être au moins de taille 1 !");
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }
}
