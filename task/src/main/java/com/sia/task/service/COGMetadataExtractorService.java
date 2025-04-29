package com.sia.task.service;

import java.util.ArrayList;
import java.util.List;

// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.Vector;

import org.gdal.gdal.Dataset;
import org.gdal.gdal.gdal;
import org.gdal.gdalconst.gdalconstConstants;
import org.springframework.stereotype.Service;

//import com.sia.task.fileDTO.FileDTO;
import com.sia.task.fileDTO.FileRequestDTO;

@Service
public class COGMetadataExtractorService {
  public List<FileRequestDTO> extractData(List<FileRequestDTO> fileList){
    System.out.println("COGMetadataExtractorService.java extractData() 호출");
    List<FileRequestDTO> metadataList = new ArrayList<FileRequestDTO>();
    for (FileRequestDTO file : fileList) {
      Dataset dataset = gdal.Open(file.getConvertedFilePath().toString(), gdalconstConstants.GA_ReadOnly);
      if(dataset == null){
        throw new RuntimeException("gdal이 파일을 열지 못했습니다");
      }
      // Map<String, Object> meta = new HashMap<String, Object>();
      
      // Vector<String> domains = dataset.GetMetadataDomainList();
      // for (String data : domains) {
      //   Map<String, String> metadata = dataset.GetMetadata_Dict(data);
      //   metadata.forEach((key,value)-> meta.put(data + ":"+key,value));
      // }
  
      // FileDTO metaFile = new FileDTO();
      // double[] gt = dataset.GetGeoTransform();
      // meta.put("origin_x", gt[0]);
      // meta.put("pixel_width", gt[1]);
      // meta.put("origin_y", gt[3]);
      // meta.put("pixel_height", gt[5]);
      // meta.put("projection", dataset.GetProjectionRef());
      file.setWidth(dataset.getRasterXSize());
      file.setHeight(dataset.getRasterYSize());
      file.setBand_count(dataset.getRasterCount());
      
      System.out.println("fileName : "+file.getConvertedFileName());
      System.out.println("width : "+file.getWidth());
      System.out.println("height : "+file.getHeight());
      System.out.println("band_conut : "+file.getBand_count());
      
      //meta.put("gdal_info_json", gdal.Info(dataset, new String[]{"-json"}));
      dataset.delete();
      metadataList.add(file);
    }
    return metadataList;
  }
}