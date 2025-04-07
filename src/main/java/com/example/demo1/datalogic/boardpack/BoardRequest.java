package com.example.demo1.datalogic.boardpack;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequest {
    private String title;     // 게시글 제목
    private String content;   // 게시글 내용
    private Long authorId;    // 작성자 ID (게시글 생성 시 필요)

}