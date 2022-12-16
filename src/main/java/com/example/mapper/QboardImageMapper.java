package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.QboardImageDTO;

@Mapper
public interface QboardImageMapper {
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

    // //물품번호전달하면 이미지1개번호전달
    // public QboardImageDTO selectImageNoOne2(Long productno);

}
