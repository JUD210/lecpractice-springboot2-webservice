package com.jud210.book.springboot.service;

import com.jud210.book.springboot.domain.posts.Posts;
import com.jud210.book.springboot.domain.posts.PostsRepository;
import com.jud210.book.springboot.web.dto.PostsResponseDto;
import com.jud210.book.springboot.web.dto.PostsSaveRequestDto;
import com.jud210.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    // Transaction 도중 Rollback이 일어나면, 그 때까지 했던 모든 작업을 취소한다.
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    // DB에 Query를 날리는 부분이 없다. 이게 가능한 이유는 JPA의 영속성 컨텍스트 때문.
    // 영속성 컨텍스트(Persistence Context)란, Entity를 영구 저장하는 환경이다. 일종의 논리적 개념이며,
    // JPA의 핵심 내용은 Entity가 영속성 컨텍스트에 포함되어 있냐 아니냐로 갈림.
    //
    // JPA의 Entity Manager가 활성화된 상태로(Spring Data Jpa를 쓴다면 기본 옵션)
    // Transaction 안에서 DB로부터 데이터를 가져오면 이 데이터는 영속성 컨텍스트가 유지된 상태.
    //
    // 이 상태에서 해당 데이터의 값을 변경하면,
    // Transaction이 끝나는 시점에 해당 Table에 변경분을 반영함.
    //
    // 즉, Entity 객체의 값만 변경하면 별도로 Update Query를 날릴 필요가 없음.
    // 이 개념을 Dirty Checking 이라고 함.
    //
    // 참고하면 좋은 자료: https://gmlwjd9405.github.io/2019/02/01/orm.html
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional
    public PostsResponseDto findById (Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }
}
