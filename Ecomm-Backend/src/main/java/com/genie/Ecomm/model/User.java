package com.genie.Ecomm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import com.genie.Ecomm.model.Role;
import java.util.List;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name ;
    private String email;
    private String contact;
    private String username;



    private String password;
    @ManyToMany(fetch =FetchType.EAGER)
    @JoinTable (
            name="user_id",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns  = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;


    public User(long id, String name, String email, String contact,String username, String password, Set<Role> roles, List<Orders> order) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.contact= contact;
        this.username = username;
        this.password = password;

        this.roles = roles;
        this.order = order;
    }

    public User() {
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Orders> order;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Orders> getOrder() {
        return order;
    }

    public void setOrder(List<Orders> order) {
        this.order = order;
    }
}
