package org.zerock.j2.entity;
// 상품 관련의 Entity

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@ToString(exclude = "images")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;
    
    private String pname;
    
    private String pdesc;

    private String writer;

    private int price;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<ProductImage> images = new ArrayList<>();

    // 상품을 추가하는 method
    public void addImage(String name){

        ProductImage pImage = ProductImage.builder().fname(name)
        .ord(images.size()).build();

        images.add(pImage);
    }
    // 이미지 파일들을 싹 비워주는 method
    public void clearImages(){
        images.clear();
    }

    public void changePrice(int price){
        this.price = price;
    }



}
