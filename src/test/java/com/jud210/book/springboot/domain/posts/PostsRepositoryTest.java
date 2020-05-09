package com.jud210.book.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {


    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void testPostsSaveAndLoad() {
        // given
        String title = "Example title";
        String content = "Example content";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("example@gmail.com")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void testBaseTimeEntityRegister() {
        // given
        LocalDateTime startOf2020 = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        postsRepository.save(Posts.builder()
                .title("TitleName")
                .content("Hello,World!")
                .author("hyuk")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>> " +
                "createDate=" + posts.getCreatedDate() +
                ", modifiedDate=" + posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(startOf2020);
        assertThat(posts.getModifiedDate()).isAfter(startOf2020);
    }

}
