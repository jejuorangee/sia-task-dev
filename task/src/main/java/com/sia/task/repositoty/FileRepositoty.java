package com.sia.task.repositoty;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sia.task.fileEntity.FileEntity;

public interface FileRepositoty extends JpaRepository<FileEntity, String>{
  List<FileEntity> findAll();
  
}
