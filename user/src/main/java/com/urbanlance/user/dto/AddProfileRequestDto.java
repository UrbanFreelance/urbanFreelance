package com.urbanlance.user.dto;

import com.urbanlance.user.model.Industry;

public record AddProfileRequestDto(

         String firstname,
         String lastname,
         String profilePhoto,
         String headline,
         Industry industry,
         String gender
) {
}
