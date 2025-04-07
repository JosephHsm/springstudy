package com.example.demo1.datalogic.likepack;


import com.example.demo1.datalogic.boardpack.Board;
import com.example.demo1.datalogic.boardpack.BoardRepository;
import com.example.demo1.datalogic.userpack.User;
import com.example.demo1.datalogic.userpack.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BoardLikeService {

    private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public BoardLikeService(BoardLikeRepository boardLikeRepository,
                            BoardRepository boardRepository,
                            UserRepository userRepository) {
        this.boardLikeRepository = boardLikeRepository;
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    /**
     * 1) 게시글 좋아요 등록 (POST /api/boards/{boardId}/likes)
     *    Request: { "userId": 1 }
     */
    public LikeResponse addLike(Long boardId, LikeRequest request) {
        // 1. 게시글, 사용자 확인
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found: " + boardId));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found: " + request.getUserId()));

        // 2. 이미 좋아요를 눌렀는지 확인(중복 방지). 정책에 따라 처리
        boardLikeRepository.findByBoardIdAndUserId(boardId, request.getUserId())
                .ifPresent((like) -> {
                    throw new RuntimeException("Already liked this board by user: " + request.getUserId());
                });

        // 3. Like 엔티티 생성 & 저장
        BoardLike boardLike = new BoardLike();
        boardLike.setBoard(board);
        boardLike.setUser(user);
        BoardLike savedLike = boardLikeRepository.save(boardLike);

        // 4. 응답 DTO 변환
        return LikeResponse.builder()
                .id(savedLike.getId())
                .boardId(boardId)
                .userId(request.getUserId())
                .likedAt(savedLike.getLikedAt())
                .build();
    }

    /**
     * 2) 게시글 좋아요 취소 (DELETE /api/boards/{boardId}/likes)
     *    Request: { "userId": 1 }
     */
    public void removeLike(Long boardId, LikeRequest request) {
        // 1. 해당 좋아요 존재 여부 확인
        BoardLike like = boardLikeRepository.findByBoardIdAndUserId(boardId, request.getUserId())
                .orElseThrow(() -> new RuntimeException("Like not found for boardId=" + boardId
                        + ", userId=" + request.getUserId()));
        // 2. 삭제
        boardLikeRepository.delete(like);
    }

    /**
     * 3) 게시글 좋아요 목록 조회 (GET /api/boards/{boardId}/likes)
     */
    @Transactional(readOnly = true)
    public List<LikeResponse> getBoardLikes(Long boardId) {
        List<BoardLike> likes = boardLikeRepository.findByBoardId(boardId);
        return likes.stream()
                .map(l -> LikeResponse.builder()
                        .id(l.getId())
                        .boardId(l.getBoard().getId())
                        .userId(l.getUser().getId())
                        .likedAt(l.getLikedAt())
                        .build()
                )
                .toList();
    }

    /**
     * 4) 유저가 좋아요한 글 목록 조회 (GET /api/users/{userId}/likes)
     */
    @Transactional(readOnly = true)
    public List<LikeResponse> getUserLikes(Long userId) {
        List<BoardLike> likes = boardLikeRepository.findByUserId(userId);
        return likes.stream()
                .map(l -> LikeResponse.builder()
                        .id(l.getId())
                        .boardId(l.getBoard().getId())
                        .userId(l.getUser().getId())
                        .likedAt(l.getLikedAt())
                        .build()
                )
                .toList();
    }
}