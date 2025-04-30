package com.sia.task.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.gdal.gdal.Dataset;
import org.gdal.gdal.TranslateOptions;
import org.gdal.gdal.gdal;
import org.springframework.stereotype.Service;

//import com.sia.task.fileDTO.FileDTO;
import com.sia.task.fileDTO.FileRequestDTO;

@Service
public class ConvertCOGService {
  public List<FileRequestDTO> fileConvertCOG(List<FileRequestDTO> downloadedFileList){
    System.out.println("ConvertCOGService.java fileConvertCOG() 호출");

    List<FileRequestDTO> convertedFileList = new ArrayList<FileRequestDTO>();
    for(FileRequestDTO file : downloadedFileList){
      // 파일 경로
      Path path = file.getFilePath();
      String fileName = file.getOriginalFileName();
      System.out.println(fileName);
      System.out.println(fileName.substring(0, fileName.lastIndexOf(".")));
      String baseName = fileName.substring(0, fileName.lastIndexOf("."));

      Path convertedFiles = path.getParent().resolve("convertedFiles");
      try{
        Files.createDirectories(convertedFiles);
        System.out.println("변환된 파일 디렉토리 생성 : "+convertedFiles);
      } catch(IOException e){
        System.out.println("error : "+e);
        throw new RuntimeException("변환된 파일 디렉토리 생성 실패 :"+e);
      }
      System.out.println("디렉토리 생성완료");
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
        convertedFileName = baseName+"_to_cog.tif";
        converted = convertedFiles.resolve(convertedFileName);
        int seq = 1;
        while (Files.exists(converted)){
          convertedFileName = String.format("%s_cog_%d.tif", baseName, seq++);
          converted = convertedFiles.resolve(convertedFileName);
        }
      }
      
      // TranslateOptions객체 인자가 Vector임
      // Vector는 ArrayList와 같이 List 인터페이스를 상속받는 컬렉션 프레임워크
      Vector<String> options = new Vector<String>();

        options.add("-of"); options.add("COG"); // 출력 포맷 COG(Cloud Optimized GeoTiff)드라이버 설정
        //options.add("-co"); options.add("COMPRESS=DEFLATE"); // 압축 방식 DEFLATE 설정
        //options.add("-co"); options.add("PREDICTOR=2"); // 예측기 설정
        //options.add("-co"); options.add("TILED=YES"); // 타일 단위로 저장
        //options.add("-co"); options.add("BLOCKSIZE=512"); // 가로세로 크기 512x512설정 (16배수 권장)
        //options.add("-co"); options.add("COPY_SRC_OVERVIEWS=YES"); // 오버뷰 복사

      
      System.out.println("변환 시작");
      System.out.println("path : "+path.toString());
      synchronized(gdal.class){
        Dataset srcDataset = gdal.Open(path.toString());
        System.out.println("원본파일 오픈");
        if(srcDataset == null){
          throw new RuntimeException("원본파일을 열지 못함");
        }
        System.out.println("변환옵션 설정");
        TranslateOptions translateOptions = new TranslateOptions(options);
        System.out.println("변환 전");
        gdal.Translate(converted.toString(), srcDataset, translateOptions);
        System.out.println("변환 후");
        srcDataset.delete();
  
        file.setConvertedFilePath(converted);
        file.setConvertedFileName(convertedFileName);
        convertedFileList.add(file);
        System.out.println("변환 완료" +file.getConvertedFilePath());
      }
    }
    return convertedFileList;
  }
}
