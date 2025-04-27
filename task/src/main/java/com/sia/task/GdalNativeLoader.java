package com.sia.task;

import org.gdal.gdal.gdal;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class GdalNativeLoader {
  // @PostConstruct
  // 스프링 컨테이너가 해당 빈을 생성하고
  // 모든 DI가 끝나면 한번 호출해주는 메서드
  // 라는 어노테이션
  @PostConstruct
  public void nativeLibLoad() throws Exception{
    

    // gdal 드라이버 등록
    gdal.SetConfigOption("GDAL_DATA", System.getenv("GDAL_DATA"));
    gdal.SetConfigOption("PROJ9_LIB", System.getenv("PROJ9_LIB"));
    gdal.AllRegister();
  }
}
