package com.example.demo1.datalogic.boardpack;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class BoardResponse {
    private Long id;
    private String title;
    private String content;
    private String authorUsername;          // 작성자 ID
    private LocalDateTime createdAt;  // 생성 시각
    private LocalDateTime updatedAt;  // 수정 시각(수정 시만 표시 가능)
}
