package com.my.articles.controller;

import com.my.articles.dto.ArticleDTO;
import com.my.articles.dto.CommentDTO;
import com.my.articles.entity.Comment;
import com.my.articles.service.ArticleService;
import com.my.articles.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/articles")
public class CommentController {
    private final CommentService commentService;

    //댓글 추가
    @PostMapping("{id}/comments")
    public String insertComment(@PathVariable("id") Long articleId, CommentDTO dto) {
        dto.setId(null);  // 새 댓글을 위한 ID를 null로 설정
        log.info("### new comment : " + dto);
        commentService.insertComment(articleId, dto);
        String url = "redirect:/articles/" + articleId;
        return url;
    }


    //댓글 삭제
    @GetMapping("comments/{id}")
    public String deleteComment(@PathVariable("id") Long id) {
        Long articleId = commentService.deleteComment(id);
        String url = "redirect:/articles/" + articleId;
        return url;
    }

    //댓글 수정
    @GetMapping("/comments/view/{id}")
    public String viewUpdateComment(@PathVariable("id") Long commentId, Model model) {
        Map<String, Object> map = commentService.findByIdComment(commentId);
        log.info("### find comment : " + map.get("dto"));

        model.addAttribute("dto", map.get("dto"));
        model.addAttribute("articleId", map.get("articleId"));  // articleId를 별도로 전달

        return "/articles/update-comment";
    }


    @PostMapping("/{article_id}/comments/{comment_id}")
    public String updateComment(@PathVariable("article_id") Long article_id, @PathVariable("comment_id") Long comment_id, CommentDTO dto) {
        log.info("### update comment : " + dto);
        dto.setId(comment_id);
        commentService.updateComment(dto);
        return "redirect:/articles/" + article_id;
    }
}
