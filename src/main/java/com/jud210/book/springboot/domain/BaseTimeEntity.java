package com.jud210.book.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
// JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우,
// 필드들(createdDate, modifiedDate)도 칼럼으로 인식하도록 함.
@EntityListeners(AuditingEntityListener.class)
// BaseTimeEntity 클래스에 Auditing 기능을 포함시킴.
public abstract class BaseTimeEntity {

    @CreatedDate // Entity 생성시, 생성 시간 자동 저장
    private LocalDateTime createdDate;

    @LastModifiedDate // Entity 수정시, 수정 시간 자동 저장
    private LocalDateTime modifiedDate;
}
