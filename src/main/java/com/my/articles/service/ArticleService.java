package com.my.articles.service;

import com.my.articles.dto.ArticleDTO;
import com.my.articles.entity.Article;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Slf4j
public class ArticleService {
    @Autowired
    EntityManager em;

    public List<ArticleDTO> showAllArticles() {
        String sql = "SELECT a FROM Article a";
        Query query = em.createQuery(sql);
        List<Article> articles = query.getResultList();
        log.info("### article service - articles :"+articles.toString());
        return articles.stream().map(entity -> ArticleDTO.fromEntity(entity)).toList();
    }

    public ArticleDTO findOneArticle(Long id) {
        String sql = "SELECT a FROM Article a WHERE a.id = :id";
        TypedQuery<Article> query = em.createQuery(sql, Article.class).setParameter("id", id);
        Article article = query.getSingleResult();
        log.info("### article service - article :"+article.toString());
        return ArticleDTO.fromEntity(article);
    }

    public void deleteArticle(Long id) {
        String sql = "DELETE FROM Article a WHERE a.id = :id";
        Query query = em.createQuery(sql).setParameter("id", id);
        query.executeUpdate();  // Executes the delete operation
    }

    public void addArticle(ArticleDTO dto) {
        log.info("### article service - new article : " + dto);
        Article article = new Article();
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        em.persist(article);
    }

    public void updateArticle(ArticleDTO dto) {
        log.info("### article service - updated article : " + dto);

        Article article = em.find(Article.class, dto.getId());

        if (article != null) {
            // Step 2: Modify the entity fields
            article.setTitle(dto.getTitle());
            article.setContent(dto.getContent());

            // Step 3: Merge the changes (optional if using @Transactional)
            em.merge(article); // This is optional as the entity is already managed.
        } else {
            throw new EntityNotFoundException("Article not found for ID: " + dto.getId());
        }
    }
}
