package com.sparta.review.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostRequestDto {
    @NotBlank(message = "제목을 입력하세요.")
    @Size(max = 500, message = "제목은 500자 까지 입력 가능합니다.")
    private String title;

    @NotBlank(message = "내용을 입력하세요.")
    @Size(max = 5000, message = "내용은 5000자 까지 입력 가능합니다.")
    private String content;
}
