package com.sparta.review.comment.dto;

import com.sparta.review.CommonResponseDto;
import com.sparta.review.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentListResponseDto extends CommonResponseDto {
    private final Long id;
    private final String content;
    private final LocalDateTime createdAt;

    public CommentListResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }
}
