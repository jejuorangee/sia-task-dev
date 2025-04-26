package com.sia.task.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Vector;

import org.gdal.gdal.Dataset;
import org.gdal.gdal.TranslateOptions;
import org.gdal.gdal.gdal;
import org.springframework.stereotype.Service;

import com.sia.task.fileDTO.FileDTO;

@Service
public class ConvertCOGService {
  public void oneFileConvertCOG(List<FileDTO> downloadedFileList){
    
    for(FileDTO file : downloadedFileList){
      // 파일 경로
      Path path = file.getFilePath();
      String fileName = file.getName();
      String baseName = fileName.substring(fileName.lastIndexOf("."+1));

      Path convertedFiles = path.getParent().resolve("convertedFiles");
      try{
        Files.createDirectories(convertedFiles);
        System.out.println("변환된 파일 디렉토리 생성 : "+convertedFiles);
      } catch(IOException e){
        System.out.println("error : "+e);
        throw new RuntimeException("변환된 파일 디렉토리 생성 실패 :"+e);
      }

      String convertedFileName = "";
      Path converted;
      if (downloadedFileList.size() > 1) {
        convertedFileName = baseName+"-to-cog.tif";
        converted = convertedFiles.resolve(convertedFileName);
        int seq = 1;
        while (Files.exists(converted)){
          convertedFileName = String.format("%s-cog-%d.tif", baseName, seq++);
          converted = convertedFiles.resolve(convertedFileName);
        }
      }
      else{
        convertedFileName = baseName+"to_cog.tif";
        converted = convertedFiles.resolve(convertedFileName);
        int seq = 1;
        while (Files.exists(converted)){
          convertedFileName = String.format("%s_cog_%d.tif", baseName, seq++);
          converted = convertedFiles.resolve(convertedFileName);
        }
      }


      // // gdal 변환 옵션
      // String[] options = new String[]{
      //   "-of", "COG", // 출력 포맷 COG(Cloud Optimized GeoTiff)드라이버 설정
      //   "-co", "COMPRESS=DEFLATE", // 압축 방식 DEFLATE 설정
      //   "-co", "PREDICTOR=2", // 예측기 설정
      //   "-co", "TILED=YES", // 타일 단위로 저장
      //   "-co", "BLOCKSIZE=512", // 가로세로 크기 512x512설정 (16배수 권장)
      //   "-co", "COPY_SRC_OVERVIEWS=YES" // 오버뷰 복사
      // };
      
      Vector<String> options = new Vector<String>();

        options.add("-of"); options.add("COG"); // 출력 포맷 COG(Cloud Optimized GeoTiff)드라이버 설정
        options.add("-co"); options.add("COMPRESS=DEFLATE"); // 압축 방식 DEFLATE 설정
        options.add("-co"); options.add("PREDICTOR=2"); // 예측기 설정
        options.add("-co"); options.add("TILED=YES"); // 타일 단위로 저장
        options.add("-co"); options.add("BLOCKSIZE=512"); // 가로세로 크기 512x512설정 (16배수 권장)
        options.add("-co"); options.add("COPY_SRC_OVERVIEWS=YES"); // 오버뷰 복사

      // try{
      //   // 변환
      //   gdal.Translate(convertedFileName, null, new TranslateOptions(options)).excute(
      //     converted.toString()
      //     gdal.Open(path.toString()),
      //     new Translate.Options(options)
      //   );
      Dataset srcDataset = gdal.Open(convertedFiles.toString());
      TranslateOptions translateOptions = new TranslateOptions(options);
      gdal.Translate(converted.toString(), srcDataset, translateOptions);
      srcDataset.delete();

      file.setConvertedFilePath(converted);
      System.out.println("변환 완료" +file.getConvertedFilePath());
    }
  }
}
