package com.codeup.springblog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringblogApplication.class)
@AutoConfigureMockMvc
public class AdsIntegrationTests {

    @Autowired
    private MockMvc mvc;


    // Sanity Test, just to make sure the mvc bean is working
    @Test
    public void contextLoads() throws Exception {
        assertThat(mvc).isNotNull();
    }

    @Test
    public void testAdsIndex() throws Exception {
        this.mvc.perform(get("/ads"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Latest ads")));
    }



}
