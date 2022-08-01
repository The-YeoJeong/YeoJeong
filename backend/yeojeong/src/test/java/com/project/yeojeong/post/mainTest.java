package com.project.yeojeong.post;

import com.project.yeojeong.dto.ConditionDto;
import com.project.yeojeong.dto.PostDto;
import com.project.yeojeong.entity.Post;
import com.project.yeojeong.repository.PostRepository;
import com.project.yeojeong.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@SpringBootTest
@Transactional
public class mainTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    public String getFilePath(Post post) {
        int start = post.getPostContent().indexOf("<img src=\"/image/") + 17;

        String filePath = "";
        if (start != 16) {
            int end = 0;
            for (int i = start; i < post.getPostContent().length(); i++) {
                if (Character.toString(post.getPostContent().charAt(i)).equals("\"")) {
                    end = i - 1;
                    break;
                }
            }

            String num = "";
            for (int i = start; i < end + 1; i++) {
                num += Character.toString(post.getPostContent().charAt(i));
            }

            filePath = "/api/image/" + num;
        }
        return filePath;
    }

    @Test
    @DisplayName("Main Top3 test")
    public void mainPostTop3Test(){
        for (Post post : postRepository.findTopList()) {
            PostDto PostDto = Post.createPostDto(post, getFilePath(post));
            System.out.println(PostDto.getPostNo());
        }
    }

    @Test
    @DisplayName("Main Post list test")
    public void mainPostTest(){
        String[] regionList = {"서울"};
        ConditionDto conditionDto = new ConditionDto();
        conditionDto.setPostContent(false);
        conditionDto.setOrder(true);
        conditionDto.setPeriod(0);
        conditionDto.setRegionName(regionList);
        conditionDto.setSearchContent("글");

        Pageable pageable = PageRequest.of(0, 3);

        ArrayList list = (ArrayList) postService.postList(conditionDto, pageable).get("postList");
        for (Object data : list) {
            PostDto postDto = (PostDto) data;
            System.out.println(postDto.toString());
        }
    }
}
