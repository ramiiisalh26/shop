package com.example.shop.user;

import java.util.Collection;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(
    name = "_user",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "student_email_uniqe",
            columnNames = "email"
        )
    }
)
public class User implements UserDetails{
    
    @Id
    @GeneratedValue( generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )

    private UUID user_id;

    @Column(
        // name = "firstName",
        nullable = false,
        columnDefinition = "TEXT"
    )
    private String first_name;

    @Column(
        nullable = false,
        columnDefinition = "TEXT"
    )
    private String last_name;

    @Column(
        name = "username",
        nullable = false,
        columnDefinition = "TEXT"
        // unique = true
    )
    private String username;

    @Column(
        name = "password",
        nullable = false,
        columnDefinition = "TEXT"
    )
    private String password;

    
    @Enumerated(EnumType.STRING)
    private Role role;

    // @OneToMany(mappedBy = "user")
    // @JoinColumn(name = "id")
    // private List<Token> tokens;

    private Boolean locked = false;
    private Boolean enabled = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        // Collections.singletonList(authority);
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
    
}
