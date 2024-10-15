package com.my.articles.dto;

import com.my.articles.entity.Article;
import com.my.articles.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "article")
public class ArticleDTO {
    private Long id;
    private String title;
    private String content;
    private List<Comment> comments;

    //Entity to DTO
    public static ArticleDTO fromEntity(Article article) {
        return new ArticleDTO(article.getId(), article.getTitle(), article.getContent(), article.getComments());
    }

    //DTO to Entity
    public static Article fromDTO(ArticleDTO dto) {
        return new Article(dto.getId(), dto.getTitle(), dto.getContent(), dto.getComments());
    }
}
