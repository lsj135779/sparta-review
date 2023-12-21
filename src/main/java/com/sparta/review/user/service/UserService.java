package com.sparta.review.user.service;

import com.sparta.review.user.dto.UserRequestDto;
import com.sparta.review.user.entity.User;
import com.sparta.review.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public void signup(UserRequestDto userRequestDto) {
        String nickname = userRequestDto.getNickname();
        String password = userRequestDto.getPassword();
        String confirmPassword = userRequestDto.getConfirmPassword();

        // 닉네임과 같은 값이 포함된 경우 회원가입에 실패로 만들기
        if (password.contains(nickname)) {
            throw new IllegalArgumentException("Registration failed: 비밀번호에 닉네임이 포함되어 있습니다.");
        }
        // 비밀번호 확인은 비밀번호와 정확하게 일치하기
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Registration failed: 비밀번호 확인이 일치하지 않습니다.");
        }
        // 데이터베이스에 존재하는 닉네임을 입력한 채 회원가입 버튼을 누른 경우
        if (isNicknameExists(nickname)) {
            throw new IllegalArgumentException("Registration failed: 중복된 닉네임입니다.");
        }

        User user = new User(nickname, passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public boolean isNicknameExists(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
