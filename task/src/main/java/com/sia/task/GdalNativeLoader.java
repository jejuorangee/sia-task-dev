package com.sia.task;

// import java.io.File;

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
    // String basePath = new File(".").getCanonicalPath();

    // //String gdalDll = basePath + "/nativelib/window/gdalalljni.dll";
    // System.out.println("로드 시도");
    // System.load(new File("./nativelib/window/gdalalljni.dll").getAbsolutePath());
    // System.out.println("dll 로드 성공");

    // String gdalDataPath = basePath + "/nativelib/window/gdal-data";
    // String projPath     = basePath + "/nativelib/window/proj";
    
    // // gdal.UseExceptions(); // 예외 상세보기

    
    // // gdal 드라이버 등록
    // gdal.SetConfigOption("GDAL_DATA", new File(gdalDataPath).getAbsolutePath());
    // gdal.SetConfigOption("PROJ9_LIB", new File(projPath).getAbsolutePath());
    // // gdal.SetConfigOption("GDAL_DATA", gdalDataPath);
    // // gdal.SetConfigOption("PROJ9_LIB", projPath);
    // System.out.println("옵션 설정 완료");
    gdal.SetConfigOption("GDAL_DATA", System.getenv("GDAL_DATA"));
    gdal.SetConfigOption("PROJ9_LIB", System.getenv("PROJ9_LIB"));
    gdal.AllRegister();
    System.out.println("gdal allRegister 성공");
  }
}
