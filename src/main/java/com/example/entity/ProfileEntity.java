package com.example.entity;


import com.example.enums.GenderType;
import com.example.exps.GeneralStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class ProfileEntity extends BaseEntity {

    @Column(name = "fire_base_id")
    private String fireBaseId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private GeneralStatus status;

    @Column(name = "temp_phone")
    private String tempPhone;

    @Column(name = "temp_sms_code")
    private String smsCode;

    @Column(name = "login")
    private String login;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GenderType genderType;

}
