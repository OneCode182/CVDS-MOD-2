package eci.cvds.mod2.modules;

import lombok.Getter;

@Getter
public class CustomUserDetails {
    private final String id;
    private final String userName;
    private final String email;
    private final String name;
    private final String role;
    private final String specialty;

    public CustomUserDetails(String id, String userName, String email, String name, String role, String specialty) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.name = name;
        this.role = role;
        this.specialty = specialty;
    }



}

