package com.azelewski.exercise.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @Column(name = "nazwa")
    @JsonProperty("nazwa")
    private String name;
    @Column(name = "data_ksiegowania")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("data_ksiegowania")
    private LocalDate date;
    @Column(name = "koszt_USD")
    @JsonProperty("koszt_USD")
    private BigDecimal priceUSD;
    @Column(name = "koszt_PLN")
    @JsonProperty("koszt_PLN")
    private BigDecimal pricePLN;

    public Computer(String name, LocalDate date, BigDecimal priceUSD) {
        this.name = name;
        this.date = date;
        this.priceUSD = priceUSD;
    }
}
