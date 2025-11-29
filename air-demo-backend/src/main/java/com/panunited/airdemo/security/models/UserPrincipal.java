package com.panunited.airdemo.security.models;

import java.util.*;

import com.panunited.airdemo.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;


@SuppressWarnings("serial")
public class UserPrincipal implements UserDetails {
    private Long id;
    private String name;
    private String username;
    @JsonIgnore
    private String password;
    private Set<Role> roles;

    private Map<String, Object> metadata;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long id, String name, String username, String password,
                         Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public UserPrincipal(Long id, String name, String username, String password,
                         Collection<? extends GrantedAuthority> authorities, Set<Role> roles, Map<String, Object> metadata) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.roles = roles;
        this.metadata = metadata;
    }

    public static UserPrincipal create(Long id, String name, String username, String password,
                                       Collection<? extends GrantedAuthority> authorities) {
        if (authorities == null) {
            authorities = new ArrayList<GrantedAuthority>();
        }

        return new UserPrincipal(id, name, username, password, authorities);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }


    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}