package com.example.easy_flow_backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Passenger extends User {
    static long counter=0;
    @OneToOne(optional = false)
    @JoinColumn(name = "wallet_id", referencedColumnName ="wallet_id")
    @Cascade(CascadeType.ALL)
    private Wallet wallet;

    @Column(name = "first_name",nullable = false)
    private String firstName;
    @Column(name = "last_name",nullable = false)
    private String lastName;
    @Column(name = "phone_number",nullable = false)
    private String phoneNumber;
    private String type;
    private String city;
    @Column(nullable = false)
    private Gender gender;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-mm-yyyy",shape = JsonFormat.Shape.STRING)
    @Column(name = "birth_day",nullable = false)
    private java.util.Date birthDay;

    public Passenger(Wallet wallet, String firstName, String lastName, String phoneNumber, String type, String city, Gender gender, Date birthDay, String username, String password) {
        super(username, password);
        roles = "PASSENGER";
        this.wallet = wallet;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.city = city;
        this.gender = gender;
        this.birthDay = birthDay;
        id="Passenger-"+ ++counter;
    }


}
