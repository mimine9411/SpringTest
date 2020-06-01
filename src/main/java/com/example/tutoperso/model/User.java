package com.example.tutoperso.model;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;

@Entity
@Table(name="users", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
@Data
public class User
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String username;
    private String motDePasse;
    private String adresse;
    @Email(message = "L'email saisis est incorrect !")
    private String email;

    public User(String nom, String prenom, String username, String motDePasse, String adresse, String email)
    {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.username = username;
        this.motDePasse = motDePasse;
        this.email = email;
    }

    public User()
    {

    }
}