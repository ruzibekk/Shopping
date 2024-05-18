package com.example.repository;

import com.example.entity.sms.SmsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface SmsRepository extends JpaRepository<SmsEntity, String> {

    Long countByPhoneAndCreatedDateBetween(String phone, LocalDateTime from, LocalDateTime to);
}
