package com.example.demo1.datalogic.likepack;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class BoardLikeController {

    private final BoardLikeService boardLikeService;

    public BoardLikeController(BoardLikeService boardLikeService) {
        this.boardLikeService = boardLikeService;
    }

    /**
     * 1) 게시글 좋아요 등록 - POST /api/boards/{boardId}/likes
     */
    @PostMapping("/api/boards/{boardId}/likes")
    public ResponseEntity<LikeResponse> addLike(@PathVariable Long boardId,
                                                @RequestBody LikeRequest request) {
        // Request: { "userId": 1 }
        // Response: { "id": 1, "boardId": 1, "userId": 1, "likedAt": "2025-01-05T10:00:00" }
        LikeResponse response = boardLikeService.addLike(boardId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * 2) 게시글 좋아요 취소 - DELETE /api/boards/{boardId}/likes
     */
    @DeleteMapping("/api/boards/{boardId}/likes")
    public ResponseEntity<Void> removeLike(@PathVariable Long boardId,
                                           @RequestBody LikeRequest request) {
        // Request: { "userId": 1 }
        boardLikeService.removeLike(boardId, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 3) 게시글 좋아요 목록 조회 - GET /api/boards/{boardId}/likes
     */
    @GetMapping("/api/boards/{boardId}/likes")
    public ResponseEntity<List<LikeResponse>> getBoardLikes(@PathVariable Long boardId) {
        List<LikeResponse> responseList = boardLikeService.getBoardLikes(boardId);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    /**
     * 4) 유저가 좋아요한 글 목록 조회 - GET /api/users/{userId}/likes
     */
    @GetMapping("/api/users/{userId}/likes")
    public ResponseEntity<List<LikeResponse>> getUserLikes(@PathVariable Long userId) {
        List<LikeResponse> responseList = boardLikeService.getUserLikes(userId);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
}