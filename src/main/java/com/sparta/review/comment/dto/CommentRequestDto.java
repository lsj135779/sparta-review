package com.sparta.review.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    @NotBlank(message = "내용을 입력하세요.")
    @Size(max = 500, message = "내용은 500자 까지 입력 가능합니다.")
    private String content;
}
