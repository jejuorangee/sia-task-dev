package com.sia.task;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.gdal.gdal.gdal;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class GdalNativeLoader {
  // @PostConstruct
  // 스프링 컨테이너가 해당 빈을 생성하고
  // 모든 DI가 끝나면 한번 호출해주는 메서드
  // 라는 표시
  @PostConstruct
  public void nativeLibLoad() throws IOException{
    // 라이브러리 경로
    String resourceDir = "nativelib/window";

    // 네이티브 라이브러리
    String[] dllLibs = {
      "gdalalljni.dll", // JNI 브릿지
      "gdal.dll", // GDAL
      "tiff.dll", // tiff 처리
      "proj_9.dll" // 프로젝션 처리
    };

    // OS 임시 경로에 gdal_native
    Path tmpFolder = Files.createTempDirectory("gdal_native");
    // JVM이 정상 종료될 때 디렉토리 삭제
    tmpFolder.toFile().deleteOnExit();

    ClassLoader classLoader = getClass().getClassLoader();
    for(String dll : dllLibs){
      InputStream in = classLoader.getResourceAsStream(resourceDir+"/"+dll);
      if(in == null){
        throw new IOException("찾지 못함 : "+resourceDir+"/"+dll);
      }
      Path out = tmpFolder.resolve(dll);
      Files.copy(in, out, StandardCopyOption.REPLACE_EXISTING);

      // 확인 로그
      System.out.println(out.toAbsolutePath().toString());
    }
    // gdal 드라이버 등록
    gdal.AllRegister();
  }
}
