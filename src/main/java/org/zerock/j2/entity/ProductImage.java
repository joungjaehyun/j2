package org.zerock.j2.entity;
// 상품에 들어가는 이미지

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable // 자동으로 FK가 생성된다.
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {

    private String fname;

    private int ord;

    

}
