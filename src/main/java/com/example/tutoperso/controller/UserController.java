package com.example.tutoperso.controller;

import com.example.tutoperso.model.User;
import com.example.tutoperso.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Api(description = "Gestion des utilisateurs")
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "Création d'un utilisateur")
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user)  {
        User newUser = userService.createUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(location).body(newUser); // api/create/{id}
    }

    @ApiOperation(value = "Modification d'un utilisateur par son pseudo")
    @PostMapping("/update")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(updatedUser.getId()).toUri();
        return ResponseEntity.created(location).body(updatedUser); // api/create/{id}
    }

    @ApiOperation(value = "Suppression d'un utilisateur par son ID")
    @PostMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @ApiOperation(value = "Récupération d'un utilisateur par son ID")
    @PostMapping("/get/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User deletedUser = userService.getUser(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(location).body(deletedUser);
    }

    @ApiOperation(value = "Récupération de tout les utilisateurs par page")
    @PostMapping("/get/all")
    public ResponseEntity<List<User>> getAllUser(@RequestParam int page, @RequestParam int size) {
        Page<User> pageUser = userService.getAllUsers(page, size);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(location).body(pageUser.getContent());
    }
}
