package com.example.service.qboard;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.QboardImageDTO;

@Service
public interface QboardImageService {
    //이미지넣기
    public int insertImage(QboardImageDTO image);
    //이미지목록
    public List<QboardImageDTO> selectimageList(Long no);
    //이미지 src사용
    public QboardImageDTO selectImageOne(Long no);
    //글번호로 안에있는 이미지일괄삭제
    public int deleteQboardImage(Long no);
    // 이미지1개삭제
    public int deleteImageOne(Long no);
}
