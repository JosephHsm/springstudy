package com.example.demo1.datalogic;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String todo;

    //1대 N 관계 => N쪽이 연관관계 주인

    @ManyToOne
    private User user;
}
