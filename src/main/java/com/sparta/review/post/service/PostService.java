package com.sparta.review.post.service;

import com.sparta.review.post.dto.PostRequestDto;
import com.sparta.review.post.dto.PostResponseDto;
import com.sparta.review.post.entity.Post;
import com.sparta.review.post.repository.PostRepository;
import com.sparta.review.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public void createPost(PostRequestDto postRequestDto, User user) {
        Post post = new Post(postRequestDto, user);
        postRepository.save(post);
    }

    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글은 없습니다."));
        PostResponseDto postResponseDto = new PostResponseDto(post);
        return postResponseDto;
    }
}
