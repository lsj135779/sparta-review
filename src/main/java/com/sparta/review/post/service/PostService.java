package com.sparta.review.post.service;

import com.sparta.review.post.dto.PostListResponseDto;
import com.sparta.review.post.dto.PostRequestDto;
import com.sparta.review.post.dto.PostResponseDto;
import com.sparta.review.post.entity.Post;
import com.sparta.review.post.repository.PostRepository;
import com.sparta.review.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        return new PostResponseDto(post);
    }

    public List<PostListResponseDto> getPostList() {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();

        List<PostListResponseDto> postListResponseDtos = new ArrayList<>();
        for (Post post : postList) {
            postListResponseDtos.add(new PostListResponseDto(post));
        }

        return postListResponseDtos;
    }
}
