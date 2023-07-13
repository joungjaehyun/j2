package org.zerock.j2.dto;

import java.util.List;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    // 기본적으로 Entity와 맞춰두고 variation을 준다

    private Long pno;
    private String pname;
    private String pdesc;
    private int price;

    // DB 처리용
    private List<String> images;

    // 등록/수정 업로드된  파일 데이터를 수집하는 용도
    private List<MultipartFile> files;

}
