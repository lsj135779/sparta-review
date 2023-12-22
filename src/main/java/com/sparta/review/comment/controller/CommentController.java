package com.sparta.review.comment.controller;

import com.sparta.review.CommonResponseDto;
import com.sparta.review.comment.dto.CommentRequestDto;
import com.sparta.review.comment.service.CommentService;
import com.sparta.review.user.details.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

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

    @PostMapping("/{post_id}")
    public ResponseEntity<CommonResponseDto> postComment(@PathVariable Long post_id, @RequestBody @Valid CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.postComment(post_id, commentRequestDto, userDetails.getUser());
        return ResponseEntity.ok().body(new CommonResponseDto("댓글 작성을 완료했습니다.", HttpStatus.OK.value()));
    }
}
