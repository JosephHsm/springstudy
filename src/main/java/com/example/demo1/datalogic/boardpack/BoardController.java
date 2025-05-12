package com.example.demo1.datalogic.boardpack;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    /** 게시글 생성 (POST) */
    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(@RequestBody @Valid BoardRequest boardRequest) {
        BoardResponse response = boardService.createBoard(boardRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /** 게시글 목록 조회 (GET) */
    @GetMapping
    public ResponseEntity<List<BoardResponse>> getAllBoards() {
        List<BoardResponse> responses = boardService.getAllBoards();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    /** 게시글 상세 조회 (GET) */
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponse> getBoard(@PathVariable Long boardId) {
        BoardResponse response = boardService.getBoard(boardId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /** 게시글 수정 (PUT) */
    @PutMapping("/{boardId}")
    public ResponseEntity<BoardResponse> updateBoard(@PathVariable Long boardId,
                                                     @RequestBody BoardRequest boardRequest) {
        BoardResponse response = boardService.updateBoard(boardId, boardRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /** 게시글 삭제 (DELETE) */
    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
