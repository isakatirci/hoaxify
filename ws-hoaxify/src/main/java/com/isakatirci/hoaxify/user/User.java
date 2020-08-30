package com.isakatirci.hoaxify.user;

import lombok.Data;

import javax.annotation.processing.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    private long Id;
    private String userName;
    private String displayName;
    private String password;
}
