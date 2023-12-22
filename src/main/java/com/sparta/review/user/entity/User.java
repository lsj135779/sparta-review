package com.sparta.review.user.entity;

import com.sparta.review.comment.entity.Comment;
import com.sparta.review.post.entity.Post;
import com.sparta.review.user.dto.UserRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Post> boardList;

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList;

    public User(String nickname, String encode) {
        this.nickname = nickname;
        this.password = encode;
    }
}
