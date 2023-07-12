package com.mercury.AuctionHouse.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "AH_USER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @SequenceGenerator(name = "ah_user_seq_gen", sequenceName = "AH_USER_SEQ", allocationSize = 1)
    @GeneratedValue(generator="ah_user_seq_gen", strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String username;
    @Column
    private String password;
    @ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.DETACH)
    private Profile profile;
    // todo: cascade = ?
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, mappedBy = "seller")
    private List<Item> ownedItems;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH, mappedBy = "buyer")
    private List<Item> purchasedItems;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Profile> list = new ArrayList<>();
        list.add(profile);
        return list;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return profile.getId() != 4;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
