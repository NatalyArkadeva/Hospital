package com.nataly.hospital.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contracts")
@Builder
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;
    @NonNull
    private int number;
    private double cost;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "contract_price",
            joinColumns = @JoinColumn(name = "contract_id"),
            inverseJoinColumns = @JoinColumn(name = "price_list_id"))
    private List<PriceList> priceList;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User patient;

    public Contract(List<PriceList> priceList, double cost, User patient) {
        this.date = LocalDate.now();
        this.priceList = priceList;
        this.cost = cost;
        this.patient = patient;
    }
}
