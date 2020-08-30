package com.isakatirci.hoaxify.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserRequestModel {
    @NotNull
    @Size(min = 5, max = 255)
    @UniqueUsername
    private String userName;
    @NotNull
    @Size(min = 5, max = 255)
    private String displayName;
    @NotNull
    @Size(min = 8, max = 255)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$")
    private String password;
}
