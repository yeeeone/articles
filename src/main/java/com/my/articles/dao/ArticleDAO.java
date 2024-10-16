package com.my.articles.dao;

import com.my.articles.dto.ArticleDTO;
import com.my.articles.dto.CommentDTO;
import com.my.articles.entity.Article;
import com.my.articles.entity.Comment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.persistence.Query;

import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class ArticleDAO {
    @Autowired
    EntityManager em;

    public List<Article> getAllArticle() {
        String sql = "SELECT a FROM Article a ORDER BY a.id DESC";
        Query query = em.createQuery(sql);
        List<Article> articles = query.getResultList();
        log.info("### article service - articles :"+articles.toString());
        return articles;
    }

    public Article findOneArticle(Long id) {
        String sql = "SELECT a FROM Article a WHERE a.id = :id";
        TypedQuery<Article> query = em.createQuery(sql, Article.class).setParameter("id", id);
        Article article = query.getSingleResult();
        log.info("### article service - article :"+article.toString());
        return article;
    }

    public void deleteArticle(Long id) {
        Article article = em.find(Article.class, id);
        em.remove(article);
    }

    public void addArticle(Article article) {
        log.info("### article service - new article : " + article);
        em.persist(article);
    }

    public void updateArticle(ArticleDTO dto) {
        log.info("### article service - updated article : " + dto);

        Article article = em.find(Article.class, dto.getId());
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setComments(dto.getComments());
    }

    public void addComment(Comment comment) {
        log.info("### article DAO - add comment : " + comment);
        em.persist(comment);
    }

    public void deleteComment(Long id, Long commentId) {
        Article article = em.find(Article.class, id);
        Comment comment = em.find(Comment.class, commentId);
        log.info("### delete comment : " + comment);
        article.getComments().remove(comment);
        em.remove(comment);
    }

    public Comment findOneComment(Long commentId) {
        String sql = "SELECT c FROM Comment c WHERE c.id = :id";
        TypedQuery<Comment> query = em.createQuery(sql, Comment.class).setParameter("id", commentId);
        Comment comment = query.getSingleResult();
        log.info("### article DAO - comment :" + comment.toString());
        return comment;
    }
}
