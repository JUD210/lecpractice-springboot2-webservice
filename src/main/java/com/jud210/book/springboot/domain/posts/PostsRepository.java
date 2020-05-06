package com.jud210.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

// @Repository 필요 없음.
// Entity 클래스와 기본 Entity Repository는 함께 위치해야만 하기 때문에, domain 패키지에서 함께 관리.
public interface PostsRepository extends JpaRepository<Posts, Long> {

}
