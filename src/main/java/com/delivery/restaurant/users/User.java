package com.delivery.restaurant.users;

import com.delivery.restaurant.cart.Cart;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Name
    @Column(name = "name")
    private String name;

    //Email
    @Column(name = "email")
    private String email;

    //Password
    @Column(name = "password")
    private String password;

    @Column(name = "roles")
    private String roles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Cart cart;

    public Set<String> getRolesSet() {
        return new HashSet<>(Arrays.asList(roles.split(",")));
    }

    public void setRolesSet(Set<String> rolesSet) {
        this.roles = String.join(",", rolesSet);
    }

}
