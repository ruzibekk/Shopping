package com.example.repository;

import com.example.entity.ProfileEntity;
import com.example.exps.GeneralStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, String> {


    Optional<ProfileEntity> findByPhoneAndVisibleIsTrue(String phone);

    @Modifying
    @Transactional
    @Query("update ProfileEntity set status = ?2 where  id = ?1")
    void updateStatus(String id, GeneralStatus active);

}
