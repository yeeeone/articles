package com.my.articles.dto;

import com.my.articles.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {
    private Long id;
    private String title;
    private String content;

    //Entity to DTO
    public static ArticleDTO fromEntity(Article article) {
        return new ArticleDTO(article.getId(), article.getTitle(), article.getContent());
    }

    //DTO to Entity
    public static Article fromDTO(ArticleDTO dto) {
        return new Article(dto.getId(), dto.getTitle(), dto.getContent());
    }
}
