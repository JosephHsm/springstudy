package com.example.demo1.datalogic;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity  // JPA 엔티티임을 선언
@Table(name = "user")  // 테이블 명 지정
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String gender;
    private int age;
}

