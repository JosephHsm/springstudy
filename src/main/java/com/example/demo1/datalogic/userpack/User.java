package com.example.demo1.datalogic.userpack;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;               // PK

    private String username;       // 예: "hong2025"
    private String email;          // 예: "newhong@example.com"

    @Column(nullable = false)
    private String password;    // <-- 추가

    @CreationTimestamp             // INSERT 시점 자동 기록
    private LocalDateTime createdAt;

    @UpdateTimestamp               // UPDATE 시점 자동 갱신
    private LocalDateTime updatedAt;
}
