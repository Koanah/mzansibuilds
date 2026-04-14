package com.mzansibuilds.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "app_user")
@Data // autogenerates all getters, setters,equals() , hashcode() and toString() at compile time
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // every username is unique
    @Column(unique = true, nullable = false)
    private String username;

    // user email
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    //upon reg , username, email & password are req
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}