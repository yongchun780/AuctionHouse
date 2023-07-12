package com.mercury.AuctionHouse.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AH_ITEM")
public class Item {
    @Id
    @SequenceGenerator(name = "ah_item_seq_gen", sequenceName = "AH_ITEM_SEQ", allocationSize = 1)
    @GeneratedValue(generator="ah_item_seq_gen", strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String name;
    @Column
    private int startingPrice;
    @Column
    private int currentPrice;
    @Column
    private int payOffPrice;
    @Column
    private Timestamp expireTime;
    @Column
    private String info;
    @Column
    private boolean sold;
    // where to write the expiration logic --> AOP: @Around(getAll())
    @ManyToOne
    private User seller;
    @ManyToOne
    private User buyer;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStartingPrice() {
        return startingPrice;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public int getPayOffPrice() {
        return payOffPrice;
    }

    public Timestamp getExpireTime() {
        return expireTime;
    }

    public String getInfo() {
        return info;
    }

    public boolean isSold() {
        return sold;
    }
    @JsonIgnore
    public User getSeller() {
        return seller;
    }
    @JsonIgnore
    public User getBuyer() {
        return buyer;
    }
}
