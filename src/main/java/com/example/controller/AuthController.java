package com.example.controller;


import com.example.dto.ApiResponse;
import com.example.dto.SmsDTO;
import com.example.dto.auth.*;
import com.example.enums.AppLanguage;
import com.example.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

//    private final ProfileService profileService;

    /**
     * Client
     */
    @PostMapping("/profile/registration")
    public ResponseEntity<ApiResponse<String>> registration(@RequestBody @Valid AuthRequestDTO dto) {
        return ResponseEntity.ok(authService.registration(dto));
    }



    @PostMapping("/profile/registration/verification")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> registrationVerification(@RequestBody @Valid SmsDTO dto,
                                                                                 @RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage language) {
        return ResponseEntity.ok(authService.profileRegistrationVerification(dto, language));
    }


    @PostMapping("/profile/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@RequestBody @Valid AuthRequestProfileDTO dto,
                                                                     @RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage language) {
        return ResponseEntity.ok(authService.profileLogin(dto, language));
    }
//
//    @PostMapping("/profile/reset-password")
//    public ResponseEntity<ApiResponse<String>> resetPassword(@RequestBody @Valid AuthResetProfileDTO dto) {
//        return ResponseEntity.ok(authService.resetPasswordRequest(dto));
//    }
//
//    @PutMapping("/profile/reset/confirm")
//    public ResponseEntity<ApiResponse<AuthResponseDTO>> resetPasswordConfirm(@Valid @RequestBody ResetPasswordConfirmDTO dto,
//                                                                             @RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage language) {
//        return ResponseEntity.ok(authService.resetPasswordConfirm(dto, language));
//    }
//
//    @GetMapping("")
//    public ResponseEntity<ApiResponse<Boolean>> getProfileByNickName(@RequestBody AuthNickNameDTO dto) {
//        return ResponseEntity.ok(profileService.getProfileByNickName(dto));
//    }
//
//    @PutMapping("/profile/resend/sms")
//    public ResponseEntity<ApiResponse<String>> resendSms(@RequestParam("phone") String phone,
//                                                         @RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage language) {
//        return ResponseEntity.ok(authService.resendSmsCode(phone, language));
//    }
//
//    /**
//     * Consulting
//     */
//    @PostMapping("/consulting/login")
//    public ResponseEntity<ApiResponse<AuthResponseDTO>> consultingLogin(@RequestBody @Valid AuthRequestProfileDTO dto) {
//        return ResponseEntity.ok(authService.consultingLogin(dto));
//    }
//
//
//    @PostMapping("/consulting/reset-password")
//    public ResponseEntity<ApiResponse<String>> resetPasswordConsulting(@RequestBody @Valid AuthResetProfileDTO dto) {
//        return ResponseEntity.ok(authService.resetPasswordConsultingRequest(dto));
//    }
//
//    @PutMapping("/consulting/reset/confirm")
//    public ResponseEntity<ApiResponse<AuthResponseDTO>> resetPasswordConfirmConsulting(@Valid @RequestBody ResetPasswordConfirmDTO dto) {
//        log.info("Consulting Reset password confirm {}", dto);
//        return ResponseEntity.ok(authService.resetPasswordConsultingConfirm(dto));
//    }


}
