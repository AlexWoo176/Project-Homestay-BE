package com.codegym.aribnb.message.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

public class SignUpForm {
    @NotBlank(message = "fullName is mandatory")
    @Size(min = 3, max = 36)
    private String name;

    @NotBlank(message = "userName is mandatory")
    @Size(min = 3, max = 36)
    private String username;

    @NotBlank(message = "Email is mandatory")
    @Size(max = 36)
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 36)
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
