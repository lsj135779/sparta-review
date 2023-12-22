package com.sparta.review.post.controller;

import com.sparta.review.CommonResponseDto;
import com.sparta.review.post.dto.PostListResponseDto;
import com.sparta.review.post.dto.PostRequestDto;
import com.sparta.review.post.dto.PostResponseDto;
import com.sparta.review.post.entity.Post;
import com.sparta.review.post.service.PostService;
import com.sparta.review.user.details.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CommonResponseDto> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        FieldError fieldError = result.getFieldError();
        String errorMessage = fieldError.getDefaultMessage();

        CommonResponseDto responseDto = new CommonResponseDto(errorMessage, HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(responseDto);
    }
    @PostMapping
    public ResponseEntity<CommonResponseDto> postPost(@RequestBody @Valid PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.createPost(postRequestDto, userDetails.getUser());

        return ResponseEntity.ok().body(new CommonResponseDto("게시글 작성을 완료했습니다.", HttpStatus.OK.value()));
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<CommonResponseDto> getPost(@PathVariable Long post_id) {
        try {
            PostResponseDto postResponseDto = postService.getPost(post_id);
            return ResponseEntity.ok().body(postResponseDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @GetMapping
    public ResponseEntity<List<PostListResponseDto>> getPostList() {

        List<PostListResponseDto> postListResponseDtos = postService.getPostList();

        return ResponseEntity.ok().body(postListResponseDtos);
    }

    @PatchMapping("/{post_id}")
    public ResponseEntity<CommonResponseDto> patchPost(@PathVariable Long post_id, @RequestBody PostRequestDto postRequestDto,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            PostResponseDto postResponseDto = postService.patchPost(post_id, postRequestDto,userDetails.getUser());
            return ResponseEntity.ok().body(postResponseDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @DeleteMapping("/{post_id}")
    public ResponseEntity<CommonResponseDto> deletePost(@PathVariable Long post_id,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            postService.deletePost(post_id,userDetails.getUser());
            return ResponseEntity.ok().body(new CommonResponseDto("게시글 삭제를 완료했습니다.", HttpStatus.OK.value()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
