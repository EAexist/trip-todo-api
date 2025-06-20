package com.matchalab.trip_todo_api.model;

import lombok.Builder;

@Builder
public record KakaoProfile(
        Long id,
        String email,
        String name,
        String nickname,
        String profileImageUrl,
        String thumbnailImageUrl,
        String phoneNumber,
        String ageRange,
        String birthday,
        String birthdayType,
        String birthyear,
        String gender,
        Boolean isKorean) {
};
