package com.sia.task.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@RestController
@RequestMapping(value="/file", method=RequestMethod.GET)
public class S3Controller {

  @Value("${ACCESS_KEY_ID}")
  private String accessKey;
  @Value("${SECRET_ACCESS_KEY}")
  private String secretKey;
  @Value("${ORIGINAL_BUCKET_NAME}")
  private String bucketName;
  
  @GetMapping("/list")
  public ResponseEntity<List<S3ObjectSummary>> ListS3(){
    System.out.println("S3Controller.java /file/list 호출됨");

      AWSCredentials crd = new BasicAWSCredentials(accessKey, secretKey);
      AmazonS3 s3Client = AmazonS3ClientBuilder
              .standard()
              .withCredentials(new AWSStaticCredentialsProvider(crd))
              .withRegion(Regions.AP_NORTHEAST_2)
              .build();

    ObjectListing listing = s3Client.listObjects(bucketName /*, folderName */);
    List<S3ObjectSummary> allSummaries = new ArrayList<>();

    // (2) 루프를 돌면서 각 배치의 summaries를 모은다
    do {
        allSummaries.addAll(listing.getObjectSummaries());
        for (S3ObjectSummary objectSummary : allSummaries){
              System.out.println(objectSummary+"\n");
            }
        listing = s3Client.listNextBatchOfObjects(listing);
    } while (listing.isTruncated());

    // (3) 누적된 결과를 클라이언트로 반환
    return new ResponseEntity<>(allSummaries, HttpStatus.OK);
  }
}