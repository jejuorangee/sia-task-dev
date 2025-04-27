package com.sia.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class TaskApplication {

	public static void main(String[] args) {
		// System.setProperty("gdal.debug", "true");
		// .env 파일 로드(루트 경로)
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

		// .env의 값을 JVM 전역 속성(System properties)으로 등록
		System.setProperty("SPRING_APPLICATION_NAME", dotenv.get("SPRING_APPLICATION_NAME", "TASK"));
		System.setProperty("USERNAME", dotenv.get("USERNAME"));
		System.setProperty("ACCESS_KEY_ID", dotenv.get("ACCESS_KEY_ID"));
		System.setProperty("SECRET_ACCESS_KEY", dotenv.get("SECRET_ACCESS_KEY"));
		System.setProperty("BUCKET_NAME", dotenv.get("BUCKET_NAME"));
		System.setProperty("ORIGINAL_BUCKET_NAME", dotenv.get("ORIGINAL_BUCKET_NAME"));
		System.setProperty("REGION", dotenv.get("REGION"));

		// Spring boot 실행
		SpringApplication.run(TaskApplication.class, args);
	}

}
