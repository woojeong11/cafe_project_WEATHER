package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.ProductImageDTO;

@Mapper
public interface ProductImageMapper {
    //이미지넣기
    public int insertImage(ProductImageDTO image);
    //이미지목록
    public List<ProductImageDTO> selectimageList(Long no);
    //물품번호전달하면 이미지1개번호전달
    public Long selectImageNoOne(Long productno);
    //이미지 src사용
    public ProductImageDTO selectImageOne(Long no);

    //물품번호전달하면 이미지1개번호전달
    public ProductImageDTO selectImageNoOne2(Long productno);
    // 이미지삭제
    public int deleteImageOne(Long no);
    // <!-- 물품번호로 안에있는 이미지일괄삭제 -->
    public int deleteImageBatch(Long productno);

    //이미지 src사용(productno 기반)
    public ProductImageDTO selectImageOneByproductno(Long no);

}
