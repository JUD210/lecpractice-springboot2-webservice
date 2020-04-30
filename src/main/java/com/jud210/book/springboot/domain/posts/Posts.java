package com.jud210.book.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // 클래스 내 모든 필드의 Getter 메소드 자동 생성
@NoArgsConstructor // 기본 생성자 자동 추가 == public Posts() {}
@Entity // 테이블과 링크될 클래스 (SalesManager -> sales_manager table)
public class Posts {

    @Id // PK 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 생성 규칙 for auto_increment
    private Long id;
    // 주민번호와 같은 비즈니스상 유니크 키나, 여러 키를 조합한 복합키로 PK를 잡을 경우 발생되는 상황
    // 1. FK를 맺을 때 다른 테이블에서 복합키 전부를 갖고 있거나, 중간 테이블을 하나 더 둬야 하는 상황
    // 2. 인덱스에 좋은 영향을 끼치지 못 함.
    // 3. 유니크한 조건이 변경될 경우 PK 전체를 수정해야 하는 일이 발생.
    // 그러므로, 주민등록번호, 복합키 등은 유니크 키로 별도로 추가하는 것을 추천.

    @Column(length = 500, nullable = false) // 테이블의 열
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    // @Column 선언 안 해도 모든 필드는 자동적으로 column이 된다.
    private String author;

    @Builder
    // 해당 클래스의 빌더 패턴 클래스 생성
    // 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}














































