package com.urbanlance.user.dto;

import com.urbanlance.user.model.Industry;
import com.urbanlance.user.model.Profile;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record AddProfileRequest(
        String headline,
        @NotNull
        Profile.Gender gender,
        @NotNull
        List<Industry> industries,
        @NotBlank
         String country,
        @NotBlank
         String state,
        @NotBlank
         String district,
        @NotBlank
         String localAddress,
        @Positive
        @Max(value = 999999)
        int pinNumber
) {
}
