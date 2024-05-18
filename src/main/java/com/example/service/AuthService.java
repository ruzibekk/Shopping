package com.example.service;

import com.example.dto.ApiResponse;
import com.example.dto.SmsDTO;
import com.example.dto.auth.AuthRequestDTO;
import com.example.dto.auth.AuthRequestProfileDTO;
import com.example.dto.auth.AuthResponseDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.AppLanguage;
import com.example.enums.RoleEnum;
import com.example.exps.GeneralStatus;
import com.example.exps.ItemNotFoundException;
import com.example.repository.ProfileRepository;
import com.example.util.JwtUtil;
import com.example.util.MD5Util;
import com.example.util.PhoneUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final ProfileRepository profileRepository;
    private final SmsHistoryService smsHistoryService;
    private final PasswordEncoder passwordEncoder;
    private final PersonRoleService personRoleService;



    public ApiResponse<String> registration(AuthRequestDTO dto) {
        boolean validate = PhoneUtil.validatePhone(dto.getPhoneNumber());
        //validate phone number
        if (!validate) {
            log.info("Phone not valid! phone={}", dto.getPhoneNumber());
            return new ApiResponse<>("Telefon raqam no'togri kiritilgan!", 400, true);
        }

        Optional<ProfileEntity> profileEntity = profileRepository.findByPhoneAndVisibleIsTrue(dto.getPhoneNumber());

        if (profileEntity.isPresent()) {
            if (profileEntity.get().getStatus().equals(GeneralStatus.ACTIVE) || profileEntity.get().getStatus().equals(GeneralStatus.BLOCK)) {
                log.warn("PhoneNumber already exist {}", dto.getPhoneNumber());
                return new ApiResponse<>("Telefon raqam oldin ro'yxatdan o'tgan!", 400, true);
            }

            if (profileEntity.get().getVisible()) {
                log.warn("PhoneNumber already exist. Visible true {}", dto.getPhoneNumber());
                return new ApiResponse<>("Telefon raqam oldin ro'yxatdan o'tgan!", 400, true);
            }
//            if (profileEntity.get().getStatus().equals(GeneralStatus.NOT_ACTIVE)) {
//                // send sms for complete registration
//                smsHistoryService.sendRegistrationSms(dto.getPhoneNumber());
//                return new ApiResponse<>(200, false);
//            }
        }
        //user create
        ProfileEntity userEntity = new ProfileEntity();
        userEntity.setName(dto.getName());
        userEntity.setSurname(dto.getSurname());
        userEntity.setPhone(dto.getPhoneNumber());
        userEntity.setPassword(MD5Util.getMd5(dto.getPassword()));
        userEntity.setStatus(GeneralStatus.NOT_ACTIVE);
        userEntity.setGenderType(dto.getGender());
        userEntity.setLogin(dto.getLogin());

        profileRepository.save(userEntity);
        // send sms verification code
        smsHistoryService.sendRegistrationSms(dto.getPhoneNumber());
        //client role
        personRoleService.create(userEntity.getId(), RoleEnum.ROLE_USER);
        return new ApiResponse<>(200, false, "Success");
    }

    public ApiResponse<AuthResponseDTO> profileRegistrationVerification(SmsDTO dto, AppLanguage language) {
        boolean validate = PhoneUtil.validatePhone(dto.getPhone());
        if (!validate) {
            log.info("Phone not Valid! phone = {}", dto.getPhone());
            return new ApiResponse<>("Telefon raqam no'togri kiritilgan!", 400, true);
        }

        Optional<ProfileEntity> userOptional = profileRepository.findByPhoneAndVisibleIsTrue(dto.getPhone());
        if (userOptional.isEmpty()) {
            log.warn("Client not found! phone = {}", dto.getPhone());
            throw new ItemNotFoundException("Bunday foydalanuchi mavjuda emas!");
        }
        ProfileEntity profile = userOptional.get();
        if (!profile.getStatus().equals(GeneralStatus.NOT_ACTIVE)) {
            log.warn("Profile Status Blocked! Phone = {}", dto.getPhone());
            return new ApiResponse<>("Foydalanuvchi statusi xato!", 400, true);
        }

        ApiResponse<String> smsResponse = smsHistoryService.checkSmsCode(dto.getPhone(), dto.getCode());
        if (smsResponse.getIsError()) {
            return new ApiResponse<>(400, true, null);
        }
        // change client status
        profileRepository.updateStatus(profile.getId(), GeneralStatus.ACTIVE);
        AuthResponseDTO responseDTO = getClientAuthorizationResponse(userOptional.get(), language);
        return new ApiResponse<>(200, false, responseDTO);

    }

    private AuthResponseDTO getClientAuthorizationResponse(ProfileEntity entity, AppLanguage language) {
        AuthResponseDTO dto = new AuthResponseDTO();
        dto.setId(entity.getId());
        dto.setLogin(entity.getLogin());
        dto.setSurname(entity.getSurname());
        dto.setPhone(entity.getPhone());
        dto.setName(entity.getName());
        dto.setRoleList(personRoleService.getProfileRoleList(entity.getId()));
        String jwt = JwtUtil.encode(entity.getId(), entity.getPhone(), dto.getRoleList());
        dto.setJwt(jwt);
        return dto;
    }

    public ApiResponse<AuthResponseDTO> profileLogin(AuthRequestProfileDTO dto, AppLanguage language) {
        boolean validate = PhoneUtil.validatePhone(dto.getPhone());
        if (!validate) {
            log.info("Phone not valid! phone = {}", dto.getPhone());
            return new ApiResponse<>("Telefon raqam no'togri kiritilgan!", 400, true);
        }
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleIsTrue(dto.getPhone());
        if (optional.isEmpty()) {
            log.warn("Client not found! phone = {}", dto.getPhone());
            return new ApiResponse<>("Bunday foydalanuchi mavjuda emas!", 400, true);
        }
        ProfileEntity profile = optional.get();
        if (!profile.getStatus().equals(GeneralStatus.ACTIVE)) {
            log.warn("Profile Status Blocked! Phone = {}", dto.getPhone());
            return new ApiResponse<>("Foydalanuchi blocklangan!", 400, true);
        }

        if (!passwordEncoder.matches(dto.getPassword(), profile.getPassword())) {
            log.warn("Password wrong! username = {}", dto.getPassword());
            return new ApiResponse<>("Username yoki parol xato!", 400, true);
        }
        return new ApiResponse<>(200, false, getClientAuthorizationResponse(profile, language));
    }

}
