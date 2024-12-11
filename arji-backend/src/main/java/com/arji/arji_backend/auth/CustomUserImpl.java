//package com.arji.arji_backend.auth;
//
//import com.arji.arji_backend.models.User;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//@NoArgsConstructor
//@Data
//public class CustomUserImpl implements UserDetails {
//
//    private Long id;
//
//    private String username;
//
//    private String password;
//
//    private Collection<? extends GrantedAuthority> authorities;
//
//    public CustomUserImpl(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
//        this.id = id;
//        this.username = username;
//        this.password = password;
//        this.authorities = authorities;
//    }
//
//    public static CustomUserImpl build(User user) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//
//
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return UserDetails.super.isAccountNonExpired();
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return UserDetails.super.isAccountNonLocked();
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return UserDetails.super.isCredentialsNonExpired();
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return UserDetails.super.isEnabled();
//    }
//}
