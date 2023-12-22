package com.sparta.review.post.dto;

import com.sparta.review.CommonResponseDto;
import com.sparta.review.post.entity.Post;
import lombok.Getter;

import java.lang.management.LockInfo;
import java.time.LocalDateTime;

@Getter
public class PostResponseDto extends CommonResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
    }
}
