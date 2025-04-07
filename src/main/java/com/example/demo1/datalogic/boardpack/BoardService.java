package com.example.demo1.datalogic.boardpack;

import com.example.demo1.datalogic.userpack.User;
import com.example.demo1.datalogic.userpack.UserRepository;
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
    public BoardResponse createBoard(BoardRequest boardRequest) {
        // 1) User(authorId)가 존재하는지 확인
        User author = userRepository.findById(boardRequest.getAuthorId())
                .orElseThrow(() -> new RuntimeException("User not found: " + boardRequest.getAuthorId()));

        // 2) Board 엔티티 생성 & 설정
        Board board = new Board();
        board.setTitle(boardRequest.getTitle());
        board.setContent(boardRequest.getContent());
        board.setAuthor(author);   // 작성자 연결

        // 3) 저장
        Board saved = boardRepository.save(board);

        // 4) Board -> BoardResponse 변환
        return BoardResponse.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .content(saved.getContent())
                .authorId(author.getId())
                .createdAt(saved.getCreatedAt())
                // created 시점이므로 updatedAt은 null 또는 아직 값 미할당
                .build();
    }

    /** 게시글 목록 조회 */
    @Transactional(readOnly = true)
    public List<BoardResponse> getAllBoards() {
        return boardRepository.findAll()
                .stream()
                .map(board -> BoardResponse.builder()
                        .id(board.getId())
                        .title(board.getTitle())
                        .content(board.getContent())
                        .authorId(board.getAuthor().getId())
                        .createdAt(board.getCreatedAt())
                        .updatedAt(board.getUpdatedAt())
                        .build()
                ).toList();
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
                .authorId(board.getAuthor().getId())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .build();
    }

    /** 게시글 수정 */
    public BoardResponse updateBoard(Long boardId, BoardRequest boardRequest) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found: " + boardId));

        // title, content 갱신
        board.setTitle(boardRequest.getTitle());
        board.setContent(boardRequest.getContent());

        // authorId도 수정 가능하게 할지 여부는 정책에 따라 결정
        // 만약 작성자를 변경하려면, 추가로 userRepository에서 찾아서 설정

        Board updated = boardRepository.save(board);

        return BoardResponse.builder()
                .id(updated.getId())
                .title(updated.getTitle())
                .content(updated.getContent())
                .authorId(updated.getAuthor().getId())
                .createdAt(updated.getCreatedAt())
                .updatedAt(updated.getUpdatedAt()) // 수정 시각 자동 반영
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
