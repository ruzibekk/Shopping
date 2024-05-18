package com.example.config.details;


import com.example.entity.ProfileEntity;
import com.example.enums.AppLanguage;
import com.example.enums.RoleEnum;
import com.example.exps.GeneralStatus;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    protected String id;
    private String phone;
    private String password;
    private Boolean visible;
    private Boolean isOnline = false; // true when connect to chat
    private List<SimpleGrantedAuthority> roleList = new LinkedList<>();
    private GeneralStatus status;
    private String profileConsultingId; // consultingId
    private String firebaseId; // consultingId
    private AppLanguage currentLang; // consultingId

    public CustomUserDetails(ProfileEntity entity, RoleEnum roles) {
        this.id = entity.getId();
        this.phone = entity.getPhone();
        this.status = entity.getStatus();
        this.password = entity.getPassword();
        this.visible = entity.getVisible();
        this.firebaseId = entity.getFireBaseId();

        roleList.add(new SimpleGrantedAuthority(roles.name()));

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setProfileConsultingId(String profileConsultingId) {
        this.profileConsultingId = profileConsultingId;
    }
}
