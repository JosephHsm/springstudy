package com.example.demo1.datalogic.likepack;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class LikeResponse {
    private Long id;            // 좋아요 PK
    private Long boardId;       // 어떤 게시글에 좋아요
    private Long userId;        // 누가 좋아요
    private LocalDateTime likedAt;
}