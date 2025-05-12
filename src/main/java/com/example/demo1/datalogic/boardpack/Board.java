package com.example.demo1.datalogic.boardpack;

import com.example.demo1.datalogic.userpack.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "board")  // DB 테이블 이름: board
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;           // 게시글 제목
    private String content;         // 게시글 내용

    // 작성자와의 연관관계 (User 엔티티와 N:1 매핑)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id") // 실제 DB 컬럼명
    private User author;            // 작성자 정보

    @CreationTimestamp
    private LocalDateTime createdAt;   // 생성 시각

    @UpdateTimestamp
    private LocalDateTime updatedAt;   // 수정 시각
}