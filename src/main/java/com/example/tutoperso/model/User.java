package com.example.tutoperso.model;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.springframework.lang.Nullable;

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

    public User(Long id, String nom, String prenom, String username, String motDePasse, String adresse, String email)
    {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.username = username;
        this.motDePasse = motDePasse;
        this.email = email;
    }

    public User makeUsername(String username)
    {
        this.username = username;
        return this;
    }

    public User makeNom(String username)
    {
        this.nom = username;
        return this;
    }

    public User makeId(long id) {
        this.id = id;
        return this;
    }
}
