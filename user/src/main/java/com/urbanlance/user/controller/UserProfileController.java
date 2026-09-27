package com.urbanlance.user.controller;

import com.urbanlance.user.dto.AddProfileRequest;
import com.urbanlance.user.dto.PatchProfileRequestDto;
import com.urbanlance.user.dto.ProfileResponse;
import com.urbanlance.user.service.UserManagementService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@Validated
@RequestMapping("/profile")
public class UserProfileController {

    private final UserManagementService userManagementService;

    @PostMapping("/add/{id}")
    public Mono<ResponseEntity<ProfileResponse>> addProfile(@RequestBody @Valid AddProfileRequest request,
                                                            @PathVariable("id") @NotBlank String id){
        ProfileResponse response = userManagementService.addProfile(request,id);
        return Mono.just(ResponseEntity.ok(response));
    }

    @PatchMapping("/patch/{id}")
    public Mono<ResponseEntity<ProfileResponse>> patchProfile(@RequestBody @Valid PatchProfileRequestDto requestDto,
                                                              @PathVariable("id") @NotBlank String id){
        ProfileResponse response = userManagementService.patchProfile(requestDto,id);
        return Mono.just(ResponseEntity.ok(response));
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<String>> deleteProfile(@PathVariable("id") @NotBlank String id){
        userManagementService.deleteProfile(id);
        return Mono.just(ResponseEntity.ok("deleted successfully"));
    }
}
