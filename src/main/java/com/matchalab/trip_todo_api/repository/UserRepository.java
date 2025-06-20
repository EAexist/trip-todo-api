package com.matchalab.trip_todo_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matchalab.trip_todo_api.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByKakaoId(Long kakaoId);
}
