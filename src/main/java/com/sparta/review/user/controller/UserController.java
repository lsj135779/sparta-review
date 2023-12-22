package com.sparta.review.user.controller;

import com.sparta.review.CommonResponseDto;
import com.sparta.review.jwt.JwtFilter;
import com.sparta.review.jwt.JwtUtil;
import com.sparta.review.user.dto.UserRequestDto;
import com.sparta.review.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> signup(@RequestBody @Valid UserRequestDto userRequestDto, BindingResult bindingResult) {
        // Validation 결과 확인
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getDefaultMessage()).append("; ");
            }
            return ResponseEntity.badRequest().body(new CommonResponseDto(errorMessage.toString(), HttpStatus.BAD_REQUEST.value()));
        }
        // 회원가입 로직
        try {
            userService.signup(userRequestDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.ok().body(new CommonResponseDto("회원가입을 완료했습니다.", HttpStatus.OK.value()));
    }

    @PostMapping("/check-nickname")
    public String checkNickname(@RequestParam String nickname) {
        if (userService.isNicknameExists(nickname)) {
            return "닉네임이 이미 사용 중입니다.";
        } else {
            return "사용 가능한 닉네임입니다.";
        }
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto> login(@RequestParam String nickname, @RequestParam String password, HttpServletResponse response) throws UnsupportedEncodingException {
        try {
            userService.login(nickname, password);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

        String token = jwtUtil.createToken(nickname);
        token = URLEncoder.encode(token, "UTF-8");
        // 클라이언트에게 JWT를 Cookie로 전달
        response.addCookie(new Cookie(JwtUtil.AUTHORIZATION_HEADER, token));
        return ResponseEntity.ok().body(new CommonResponseDto("로그인을 완료했습니다.", HttpStatus.OK.value()));
    }
}
