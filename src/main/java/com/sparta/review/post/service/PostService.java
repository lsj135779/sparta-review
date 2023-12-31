package com.sparta.review.post.service;

import com.sparta.review.comment.entity.Comment;
import com.sparta.review.comment.repository.CommentRepository;
import com.sparta.review.post.dto.PostListResponseDto;
import com.sparta.review.post.dto.PostRequestDto;
import com.sparta.review.post.dto.PostResponseDto;
import com.sparta.review.post.entity.Post;
import com.sparta.review.post.repository.PostRepository;
import com.sparta.review.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public void createPost(PostRequestDto postRequestDto, User user) {
        Post post = new Post(postRequestDto, user);
        postRepository.save(post);
    }

    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글은 없습니다."));

        List<Comment> commentList = commentRepository.findByPostIdOrderByCreatedAtDesc(postId);
        post.setCommentList(commentList);

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

    @Transactional
    public PostResponseDto patchPost(Long postId, PostRequestDto postRequestDto, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        if (!Objects.equals(post.getUser().getId(), user.getId())) {
            throw new IllegalArgumentException("게시글 작성자만 수정이 가능합니다.");
        }

        post.setContent(postRequestDto.getContent());
        post.setTitle(postRequestDto.getTitle());

        return new PostResponseDto(post);
    }

    @Transactional
    public void deletePost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        if (!Objects.equals(post.getUser().getId(), user.getId())) {
            throw new IllegalArgumentException("게시글 작성자만 수정이 가능합니다.");
        }

        postRepository.delete(post);
    }
}
