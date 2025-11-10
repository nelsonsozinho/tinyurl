package com.nmsalone.tinyurl.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name="tiny_url")
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="original_url", nullable = false)
    private String originalUrl;

    @Column(name="tiny_url")
    private String tinyUrl;

    @Column(nullable = false, name="date_register")
    private LocalDate register;

    @Column(nullable = false, name="date_expiring")
    private LocalDate expiring;

}
