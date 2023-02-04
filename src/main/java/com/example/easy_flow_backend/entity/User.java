package com.example.easy_flow_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
//    @GeneratedValue(strategy = GenerationType.AUTO)
    protected String id;
    @Column(nullable = false)
    protected String username;
    @Column(nullable = false)
    @JsonIgnore
    protected String password;
    @Column(nullable = false)
    protected boolean active = true; // TODO set to false by default until gmail verification
    @Column(nullable = false)
    //@ColumnDefault("")
    protected String roles = "";
    @Column(nullable = false)
    //@ColumnDefault("")
    protected String permissions = "";

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public List<String> getPermessionList() {
        if (!this.permissions.isEmpty())
            return Arrays.asList(this.permissions.split(","));
        return new ArrayList<>();
    }

    public List<String> getRoleList() {
        if (!this.roles.isEmpty())
            return Arrays.asList(this.roles.split(","));
        return new ArrayList<>();
    }

}