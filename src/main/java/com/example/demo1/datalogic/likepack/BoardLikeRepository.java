package com.example.demo1.datalogic.likepack;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

    // 특정 게시글에 대한 좋아요 전체 조회
    List<BoardLike> findByBoardId(Long boardId);

    // 특정 사용자에 대한 좋아요 전체 조회
    List<BoardLike> findByUserId(Long userId);

    // 특정 게시글 + 특정 사용자가 좋아요 한 레코드 조회
    Optional<BoardLike> findByBoardIdAndUserId(Long boardId, Long userId);
}