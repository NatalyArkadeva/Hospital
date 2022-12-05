package com.nataly.hospital.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "price_list")
@Builder
public class PriceList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private double price;

    @ManyToMany(mappedBy = "priceList", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Contract> contracts;

    public PriceList(@NonNull String title, @NonNull double price) {
        this.title = title;
        this.price = price;
    }
}
