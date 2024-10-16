package com.my.articles.dao;

import com.my.articles.entity.Article;
import com.my.articles.entity.Comment;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Transactional
@Slf4j
public class CommentDAO {
    @Autowired
    EntityManager em;

    public Long deleteComment(Long id){
        Comment comment = em.find(Comment.class, id);
        em.remove(comment);
        return comment.getArticle().getId();
    }

    public void insertComment(Long articleId, Comment comment) {
        log.info("### comment dao - new comment : " + comment);
        Article article = em.find(Article.class, articleId);
        comment.setArticle(article);
        article.getComments().add(comment);
        em.persist(article);
    }

    public Comment findByIdComment(Long commentId) {
        return em.find(Comment.class, commentId);
    }

    public void updateComment(Comment comment) {
        Comment updateComment = em.find(Comment.class, comment.getId());
        updateComment.setBody(comment.getBody());
    }
}
