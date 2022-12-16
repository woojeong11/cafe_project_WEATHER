package com.example.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.ProductDTO;
import com.example.entity.Product;

@Mapper
public interface ProductMapper {
    //아이템작성
    public int insertProduct(ProductDTO pro);

    public long productSeq();

    //아이템전체목록
    public List<Product> selectList();

    //아이템 삭제
    public int deleteProduct(Long no);

    //아이템1개 조회
    public Product productOne(Long no);

    //아이템 카테고리별 목록
    public List<ProductDTO> selectCategoryList(Map<String, Object> map);

    // dto전체리스트
    public List<ProductDTO> selectListt();

    //이전글다음글
    public ProductDTO selectNextandPre(Long no);

    //이전글
    public Long prevpageFAQ(Long no);

    //다음글
    public Long nextpageFAQ(Long no);
    
    //랜덤3개추첨
    public List<ProductDTO> randomProduct();

    //메인용 랜덤12개추첨
    public List<ProductDTO> mainRandomProduct(Map<String, Object> map);
    
    //메인용 판매순 8개
    public List<ProductDTO> mainSoldProduct(Map<String, Object> map);
    
    //페이지네이션 글개수
    public Long countList(String category);

    //카테고리별 목록
    public List<ProductDTO> selectCategory(String category);

    


}
