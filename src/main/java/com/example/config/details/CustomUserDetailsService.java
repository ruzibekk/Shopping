package com.example.config.details;

import com.example.entity.ProfileEntity;
import com.example.enums.RoleEnum;
import com.example.repository.ProfileRepository;
import com.example.repository.profile.PersonRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PersonRoleRepository personRoleRepository;

    /**
     * Profile
     */
    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        Optional<ProfileEntity> profileOptional = profileRepository.findByPhoneAndVisibleIsTrue(phone);
        if (profileOptional.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }
        ProfileEntity profileEntity = profileOptional.get();
        RoleEnum role = personRoleRepository.findPersonRoleEnum(profileEntity.getId());
        return new CustomUserDetails(profileEntity, role);
    }

}
