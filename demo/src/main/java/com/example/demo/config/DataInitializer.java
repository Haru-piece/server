package com.example.demo.config;

import com.example.demo.persistence.BadgeRepository;
import com.example.demo.model.BadgeEntity;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
    private BadgeRepository badgeRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // 뱃지 데이터 저장
        BadgeEntity createKing = BadgeEntity.builder().name("CreateKing").build();
       
        badgeRepository.save(createKing);

        // 추가적인 챌린지 데이터 저장
        // ...
    }
}