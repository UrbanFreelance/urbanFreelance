package com.urbanlance.user.dto;

import com.urbanlance.user.model.Industry;
import com.urbanlance.user.model.Profile;

import java.util.List;

public record PatchProfileRequestDto(
         String preferredName,
         String headline,
         List<Industry> industries,
         Profile.Gender gender
) {
}
