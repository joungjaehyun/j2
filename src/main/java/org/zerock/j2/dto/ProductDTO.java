package org.zerock.j2.dto;

import java.util.ArrayList;
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
    @Builder.Default
    private List<String> images = new ArrayList<>();

    // 등록/수정 업로드된  파일 데이터를 수집하는 용도
    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>();

}
