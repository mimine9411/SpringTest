package com.example.tutoperso.controller;

import com.example.tutoperso.model.User;
import com.example.tutoperso.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    @Autowired // A revoir
    private UserService userService;

    @ApiOperation(value = "Création d'un utilisateur")
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user)  {
        User newUser = userService.createUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(location).body(newUser);
    }

    @ApiOperation(value = "Modification d'un utilisateur par son ID")
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(updatedUser.getId()).toUri();
        return ResponseEntity.created(location).body(updatedUser);
    }

    @ApiOperation(value = "Suppression d'un utilisateur par son ID")
    @DeleteMapping("/delete")
    public ResponseEntity deleteUser(@RequestParam String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Récupération d'un utilisateur par son ID")
    @GetMapping("/get")
    public ResponseEntity<User> getUser(@RequestParam String username) {
        return ResponseEntity.ok().body( userService.getUser(username));
    }

    @ApiOperation(value = "Récupération de tout les utilisateurs par page")
    @GetMapping("/get/all")
    public ResponseEntity<Page<User>> getAllUser(@RequestParam int page, @RequestParam int size) {
        Page<User> pageUser = userService.getAllUsers(page, size);
        return ResponseEntity.ok().body(pageUser);
    }
}
