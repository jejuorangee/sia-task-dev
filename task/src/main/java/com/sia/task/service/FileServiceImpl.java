package com.sia.task.service;

import java.util.List;
import java.util.stream.Collectors;

//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

//import com.sia.task.fileDTO.FileDTO;
import com.sia.task.fileDTO.FileRequestDTO;
import com.sia.task.fileDTO.FileResponseDTO;
import com.sia.task.fileEntity.FileEntity;
import com.sia.task.repositoty.FileRepositoty;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{
  
  private final FileRepositoty fileRepositoty;

  // @Override
  // @Transactional
  // public List<FileDTO> selectAll(FileRequestDTO requestDTO) {
  //   FileEntity entity = requestDTO.toEntity();
  //   return fileRepositoty.findAll(entity);
  // }

  // @Override
  // @Transactional
  // public boolean insert(FileDTO fileDTO) {
  //   throw new UnsupportedOperationException("Unimplemented method 'insert'");
  // }

  @Override
  public List<FileResponseDTO> selectAll() {
    // 모든 FileEntity 조회
    List<FileEntity> entities = fileRepositoty.findAll();
    // 엔티티 → 응답 DTO로 변환
    return entities.stream()
      .map(FileResponseDTO::fromEntity)
      .collect(Collectors.toList());
  }
  
  @Override
  public String insert(FileRequestDTO fileRequestDTO) {
    // 요청 DTO를 Entity로 변환
    FileEntity entity = fileRequestDTO.toEntity();
    // 저장 후 저장된 파일의 이름 반환
    return fileRepositoty.save(entity).getConvertedFileName();
  }

  @Override
  public List<FileResponseDTO> insertAll(List<FileRequestDTO> fileList) {
    // 요청 DTO 리스트를 Entity 리스트로 변환
    List<FileEntity> entities = fileList.stream()
      .map(FileRequestDTO::toEntity)
      .collect(Collectors.toList());
      // Entity 리스트를 DB에 일괄 저장
      List<FileEntity> saved = fileRepositoty.saveAll(entities);
      // 저장된 Entity 리스트를 응답 DTO 리스트로 변환 후 반환
      return saved.stream()
        .map(FileResponseDTO::fromEntity)
        .collect(Collectors.toList());
  }
}