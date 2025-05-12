package com.example.demo1.datalogic.boardpack;

import com.example.demo1.datalogic.userpack.User;
import com.example.demo1.datalogic.userpack.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public BoardService(BoardRepository boardRepository,
                        UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    /** 게시글 생성 */
    public BoardResponse createBoard(BoardRequest req) {
        // 1) SecurityContext에서 email 꺼내기
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        // 2) 엔티티 생성/저장 (나머지 동일)
        Board b = new Board();
        b.setTitle(req.getTitle());
        b.setContent(req.getContent());
        b.setAuthor(author);
        Board saved = boardRepository.save(b);

        return BoardResponse.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .content(saved.getContent())
                .authorUsername(author.getUsername())
                .createdAt(saved.getCreatedAt())
                .updatedAt(saved.getUpdatedAt())
                .build();
    }

    /** 게시글 목록 조회 */
    @Transactional(readOnly = true)
    public List<BoardResponse> getAllBoards() {
        return boardRepository.findAll().stream()
                .map(board -> BoardResponse.builder()
                        .id(board.getId())
                        .title(board.getTitle())
                        .content(board.getContent())
                        .authorUsername(board.getAuthor().getUsername())
                        .createdAt(board.getCreatedAt())
                        .updatedAt(board.getUpdatedAt())
                        .build()
                )
                .toList();
    }

    /** 게시글 상세 조회 */
    @Transactional(readOnly = true)
    public BoardResponse getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found: " + boardId));

        return BoardResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .authorUsername(board.getAuthor().getUsername())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .build();
    }

    /** 게시글 수정 */
    public BoardResponse updateBoard(Long boardId, BoardRequest boardRequest) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found: " + boardId));

        board.setTitle(boardRequest.getTitle());
        board.setContent(boardRequest.getContent());
        Board updated = boardRepository.save(board);

        return BoardResponse.builder()
                .id(updated.getId())
                .title(updated.getTitle())
                .content(updated.getContent())
                .authorUsername(updated.getAuthor().getUsername())
                .createdAt(updated.getCreatedAt())
                .updatedAt(updated.getUpdatedAt())
                .build();
    }

    /** 게시글 삭제 */
    public void deleteBoard(Long boardId) {
        if (!boardRepository.existsById(boardId)) {
            throw new RuntimeException("Board not found: " + boardId);
        }
        boardRepository.deleteById(boardId);
    }
}
