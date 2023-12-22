package com.sparta.review.post.dto;

import com.sparta.review.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostListResponseDto {
    private final Long id;
    private final String nickname;
    private final String title;
    private final LocalDateTime createdAt;

    public PostListResponseDto(Post post) {
        this.id = post.getId();
        this.nickname = post.getUser().getNickname();
        this.title = post.getTitle();
        this.createdAt = post.getCreatedAt();
    }
}
