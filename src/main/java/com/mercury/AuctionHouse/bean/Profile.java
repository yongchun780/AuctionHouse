package com.mercury.AuctionHouse.bean;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AH_PROFILE")
public class Profile implements GrantedAuthority {
    @Id
    private int id;
    @Column
    private String type;

    public Profile(int id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return type;
    }
}
