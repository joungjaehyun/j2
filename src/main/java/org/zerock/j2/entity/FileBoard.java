package org.zerock.j2.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.BatchSize;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "images") // exclude 제외한다. 
public class FileBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;

    private String content;

    private String writer;
    
    // Fetch eager시 한번에 필요한것들 조인해서 추출
    // => N+1 문제 발생 => 무조건 LAZY로
    // N+1 해결 방법 => BacthSize(일괄처리) 이방법도 목록추출은 가능하지만 상세보기일땐 문제가 생긴다.
    // size의 값만큼 1번에 처리한다.
    @BatchSize(size=20)
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="board")
    @Builder.Default
    private List<FileBoardImage> images = new ArrayList<>(); // 바꿀 수 없다, 지우면 안됨

    public void addImage(FileBoardImage boardImage){

        // 순번을 준다 size값으로
        boardImage.changeOrd(images.size());
        // 이미지 추가
        images.add(boardImage);
    }

    // 수정작업용
    public void cleanImages(){
        images.clear();
    }

}
