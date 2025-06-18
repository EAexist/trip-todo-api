package com.matchalab.trip_todo_api.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.matchalab.trip_todo_api.model.KakaoProfile;
import com.matchalab.trip_todo_api.model.User;
import com.matchalab.trip_todo_api.repository.UserRepository;
import com.matchalab.trip_todo_api.utils.Utils;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private UserRepository userRepository;

    /**
     * Provide the details of an Trip with the given id.
     */
    @PostMapping("/kakao")
    public ResponseEntity<User> kakaoLogin(@RequestBody KakaoProfile kakaoProfile) {
        try {

            Boolean isCreated = true;
            Optional<User> userOptional = userRepository.findByKakaoId(kakaoProfile.id());

            if (userOptional.isPresent()) {
                isCreated = false;
            }

            User user = userOptional.orElse(userRepository.save(new User(kakaoProfile)));

            return ResponseEntity.status(isCreated ? HttpStatus.CREATED : HttpStatus.SEE_OTHER)
                    .location(Utils.getLocation((user.getId()))).body(user);
        } catch (HttpClientErrorException e) {
            throw e;
        }
    }

    /**
     * Provide the details of an Trip with the given id.
     */
    @PostMapping("/google")
    public ResponseEntity<User> googleLogin(@RequestBody KakaoProfile googleProfile) {
        try {

            Boolean isCreated = true;
            Optional<User> userOptional = userRepository.findByKakaoId(googleProfile.id());

            if (userOptional.isPresent()) {
                isCreated = false;
            }

            User user = userOptional.orElse(userRepository.save(new User(googleProfile)));

            return ResponseEntity.status(isCreated ? HttpStatus.CREATED : HttpStatus.SEE_OTHER)
                    .location(Utils.getLocation((user.getId()))).body(user);
        } catch (HttpClientErrorException e) {
            throw e;
        }
    }

}
