package com.my.articles.dto;

import com.my.articles.entity.Article;
import com.my.articles.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private String nickname;
    private String body;

    //Entity to DTO
    public static CommentDTO fromEntity(Comment comment) {
        return new CommentDTO(comment.getId(), comment.getNickname(), comment.getBody());
    }

    //DTO to Entity
    public static Comment fromDTO(CommentDTO dto) {
        Comment comment = new Comment();
        comment.setId(dto.getId());
        comment.setNickname(dto.getNickname());
        comment.setBody(dto.getBody());
        return comment;
    }
}
