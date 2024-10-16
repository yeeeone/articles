package com.my.articles.controller;

import com.my.articles.dto.ArticleDTO;
import com.my.articles.dto.CommentDTO;
import com.my.articles.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("articles")
public class CommentController {
    @Autowired
    ArticleService articleService;

    //댓글 추가
    @PostMapping("addComment")
    public String addComment(@ModelAttribute("newComment") CommentDTO comment, @RequestParam("id") Long id) {
        log.info("### new comment : " + comment + "article id : " + id);

        ArticleDTO article = articleService.findOneArticle(id);
        comment.setId(null);
        articleService.addComment(comment);

        log.info("### add comment to article : " + article.getComments());
        articleService.updateArticle(article);
        String url = "redirect:" + article.getId();
        return url;
    }

    //댓글 수정
    @GetMapping("{id}/{commentId}/update")
    public String viewUpdateComment(@PathVariable("id") Long id, @PathVariable("commentId") Long commentId, Model model) {
        CommentDTO comment = articleService.findOneComment(commentId);
        log.info("### get comment : " + comment);
        model.addAttribute("comment", comment);
        String url = "redirect:/articles/" + id;
        return url;
    }

    @PostMapping("updateComment")
    public String updateComment() {
        String url = "redirect:";
        return url;
    }

    //댓글 삭제
    @GetMapping("{id}/{commentId}/delete")
    public String deleteComment(@PathVariable("id") Long id, @PathVariable("commentId") Long commentId) {
        log.info("### delete comment id : " + commentId);
        articleService.deleteComment(id, commentId);

        String url = "redirect:/articles/"+id;
        return url;
    }
}
