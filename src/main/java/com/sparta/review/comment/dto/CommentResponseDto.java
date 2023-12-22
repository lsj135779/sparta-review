package com.sparta.review.comment.dto;

import com.sparta.review.CommonResponseDto;
import com.sparta.review.comment.entity.Comment;
import com.sparta.review.post.dto.PostResponseDto;
import lombok.Getter;

@Getter
public class CommentResponseDto extends CommonResponseDto {
    private final String content;

    public CommentResponseDto(Comment comment) {
        this.content = comment.getContent();
    }
}
