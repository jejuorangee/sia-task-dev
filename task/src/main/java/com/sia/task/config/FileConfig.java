package com.sia.task.config;

import org.apache.tika.Tika;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileConfig {
  // 파일 타입 체크를 위한 라이브러리 Bean 등록
    @Bean
    public Tika getTika() {
        return new Tika();
    }
}
