package com.sparta.review.post.dto;

import com.sparta.review.CommonResponseDto;
import com.sparta.review.comment.dto.CommentResponseDto;
import com.sparta.review.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponseDto extends CommonResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final List<CommentResponseDto> commentList;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.commentList = post.getCommentList().stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }
}
