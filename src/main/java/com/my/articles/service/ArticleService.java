package com.my.articles.service;

import com.my.articles.dao.ArticleDAO;
import com.my.articles.dto.ArticleDTO;
import com.my.articles.entity.Article;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.groovy.runtime.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@Slf4j
public class ArticleService {
    @Autowired
    ArticleDAO dao;

    public List<ArticleDTO> showAllArticles() {
        List<Article> articles = dao.getAllArticle();
        if (ObjectUtils.isEmpty(articles)) {
            return Collections.emptyList();
        }
        return articles.stream().map(entity -> ArticleDTO.fromEntity(entity)).toList();

    }

    public ArticleDTO findOneArticle(Long id) {
        Article article = dao.findOneArticle(id);
        if(ObjectUtils.isEmpty(article)) return null;
        return ArticleDTO.fromEntity(article);
    }

    public void deleteArticle(Long id) {
        dao.deleteArticle(id);
    }

    public void addArticle(ArticleDTO dto) {
        dao.addArticle(ArticleDTO.fromDTO(dto));
    }

    public void updateArticle(ArticleDTO dto) {
        dao.updateArticle(dto);
    }
}
