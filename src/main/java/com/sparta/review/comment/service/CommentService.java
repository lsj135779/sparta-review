package com.sparta.review.comment.service;

import com.sparta.review.comment.dto.CommentListResponseDto;
import com.sparta.review.comment.dto.CommentRequestDto;
import com.sparta.review.comment.dto.CommentResponseDto;
import com.sparta.review.comment.entity.Comment;
import com.sparta.review.comment.repository.CommentRepository;
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
import java.util.Objects;

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

    public CommentResponseDto patchComment(Long commentId, PostRequestDto postRequestDto, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        if (!Objects.equals(comment.getUser().getId(), user.getId())) {
            throw new IllegalArgumentException("댓글 작성자만 수정이 가능합니다.");
        }

        comment.setContent(postRequestDto.getContent());

        return new CommentResponseDto(comment);
    }

    public void deleteComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        if (!Objects.equals(comment.getUser().getId(), user.getId())) {
            throw new IllegalArgumentException("댓글 작성자만 수정이 가능합니다.");
        }

        commentRepository.delete(comment);
    }

    public List<CommentListResponseDto> getCommentList(Long postId) {
        List<Comment> commentList = commentRepository.findAllByOrderByCreatedAtDesc();

        List<CommentListResponseDto> commentListResponseDtos = new ArrayList<>();
        for (Comment comment : commentList) {
            commentListResponseDtos.add(new CommentListResponseDto(comment));
        }

        return commentListResponseDtos;
    }
}
