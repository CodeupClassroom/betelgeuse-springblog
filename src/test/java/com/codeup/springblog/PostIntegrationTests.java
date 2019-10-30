package com.codeup.springblog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringblogApplication.class)
@AutoConfigureMockMvc
public class PostIntegrationTests {

    @Autowired
    private MockMvc mvc;

    // Sanity Test, just to make sure the mvc bean is working
    @Test
    public void contextLoads() throws Exception {
        assertThat(mvc).isNotNull();
    }

    // https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/util/AntPathMatcher.html
    @Test
    public void testShowCreatePostPage() throws Exception {
        this.mvc.perform(get("/posts/create"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

}
