package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.ProductImageDTO;

@Service
public interface ProductImageService {
    
    //이미지넣기
    public int insertImage(ProductImageDTO image);
   
    //이미지목록
    public List<ProductImageDTO> selectimageList(Long no);

    //물품번호전달하면 이미지1개번호전달
    public Long selectImageNoOne(Long productno);  

    //이미지 src사용
    //이미지번호로 이미지정보가져가기
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
