package org.zerock.j2.dto;

import lombok.Data;

// 목록을 가져오기위한 DTO
@Data
public class ProductListDTO {
    
    private Long pno;
    private String pname;
    private int price;
    private String fname;
    
    private long reviewCnt;

    private double reviewAvg;

}
