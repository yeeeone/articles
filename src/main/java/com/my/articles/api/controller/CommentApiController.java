package com.my.articles.api.controller;

import com.my.articles.api.exception.ApiResponse;
import com.my.articles.api.exception.BadRequestException;
import com.my.articles.dto.CommentDTO;
import com.my.articles.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//RestController = Controller + ResponseBody
@RestController
public class CommentApiController {
    private final CommentService commentService;

    public CommentApiController(CommentService commentService) {
        this.commentService = commentService;
    }

    //Exception test
    @GetMapping("/api/exception")
    public String exHandler() {
        throw new BadRequestException("TEST");
    }

    //댓글 조회
    @GetMapping("/api/comments/{commentId}")
    public ResponseEntity<?> commentSearch(@PathVariable("commentId") Long commentId) {
        CommentDTO dto = extracted(commentId, "댓글 조회에 실패했습니다.");
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    //댓글 추가
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<?> commentCreate(@PathVariable("articleId") Long articleId, @RequestBody CommentDTO dto) {
        commentService.insertComment(articleId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder().message("댓글 생성 성공").build());
    }

    //댓글 수정
    @PatchMapping("/api/comments/{commentId}")
    public ResponseEntity<?> commentUpdate(@PathVariable("commentId") Long commentId, @RequestBody CommentDTO dto) {
        // 수정할 댓글 존재 확인
        CommentDTO result = extracted(commentId, "댓글 수정에 실패했습니다.");
        // 댓글 수정
        dto.setId(commentId);
        commentService.updateComment(dto);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder().message("댓글 수정 성공").build());
    }


    //댓글 삭제
    @DeleteMapping("/api/comments/{commentId}")
    public ResponseEntity<?> commentDelete(@PathVariable("commentId") Long commentId) {
        // 수정할 댓글 존재 확인
        CommentDTO result = extracted(commentId, "댓글 삭제에 실패에 실패했습니다.");
        //댓글 삭제
        commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder().message("댓글 삭제 성공").build());
    }


    private CommentDTO extracted(Long commentId, String msg) {
        Map<String, Object> findComment = commentService.findByIdComment(commentId);
        if (ObjectUtils.isEmpty(findComment.get("dto"))) {
            throw new BadRequestException(msg);
        }
        return (CommentDTO) findComment.get("dto");
    }
}
