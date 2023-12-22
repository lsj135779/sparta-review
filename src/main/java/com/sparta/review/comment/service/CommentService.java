package com.sparta.review.comment.service;

import com.sparta.review.comment.dto.CommentRequestDto;
import com.sparta.review.comment.entity.Comment;
import com.sparta.review.comment.repository.CommentRepository;
import com.sparta.review.post.entity.Post;
import com.sparta.review.post.repository.PostRepository;
import com.sparta.review.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public void postComment(Long post_id, CommentRequestDto commentRequestDto, User user) {
        Post post = postRepository.findById(post_id).orElseThrow(() -> new IllegalArgumentException("해당 게시글은 없습니다."));

        Comment comment = new Comment(commentRequestDto, user, post);

        commentRepository.save(comment);
    }
}
