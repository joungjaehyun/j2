package org.zerock.j2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FileBoardListDTO {
    // List 를 추출할때 필요한 데이터만 모아놓은것 
    // 게시판번호 제목 
    // 이미지의 uuid 그리고 이미지 파일 이름
    private Long bno;
    private String title;
    private String uuid;
    private String fname;
}
