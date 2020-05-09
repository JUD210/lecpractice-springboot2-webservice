package com.jud210.book.springboot.web;

import com.jud210.book.springboot.domain.posts.Posts;
import com.jud210.book.springboot.domain.posts.PostsRepository;
import com.jud210.book.springboot.web.dto.PostsSaveRequestDto;
import com.jud210.book.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// HelloControllerTest와 달리 @WebMvcTest를 사용하지 않았다.
// 이는 @WebMvcTest의 경우 JPA 기능이 작동하지 않고, Controller와 ControllerAdvice 등
// 외부 연동과 관련된 부분만 활성화되기 때문이다.
//
// 지금 같이 JPA 기능까지 한 번에 테스트할 때는
// @SpringBootTest와 TestRestTemplate을 사용하면 된다.
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    public void testPostsRegister() throws Exception {
        // given
        String title = "Test example title";
        String content = "Test example content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("judicious210@gmail.com")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
        // long.class == Long.TYPE; //always true
        // int.class == Integer.TYPE; //always true
        // float.class == Float.TYPE; // always true

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }


    @Test
    public void testPostsEdit() throws Exception {
        // given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("Example title")
                .content("Example content")
                .author("example@gmail.com")
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "Updated title";
        String expectedContent = "Updated content";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }
}



