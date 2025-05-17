package com.example.tseytwa.tinder.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "links")
@Data
public class Links {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Profile user_id;
    private String social;
    private String link;
}
