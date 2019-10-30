package com.codeup.springblog;

import com.codeup.springblog.models.User;
import com.codeup.springblog.repos.UserRepository;
import com.codeup.springblog.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpSession;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringblogApplication.class)
@AutoConfigureMockMvc
public class AdsIntegrationTests {

    private User testUser;

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserRepository userDao;

    @Autowired
    UserService userService;

    @Before
    public void setup(){
        testUser = userDao.findByUsername("testUser");
        if(testUser == null){
            User newUser = new User();
            newUser.setUsername("testUser");
            newUser.setPassword("pass");
            newUser.setEmail("testUser@codeup.com");
            testUser = userDao.save(newUser);
        }
        userService.authenticate(testUser);
    }

    // Sanity Test, just to make sure the mvc bean is working
    @Test
    public void contextLoads() {
        assertThat(mvc).isNotNull();
    }

    @Test
    public void testAdsIndex() throws Exception {
        this.mvc.perform(get("/ads"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Latest ads")));
    }

    @Test
    public void testCreateAd() throws Exception {

        HttpSession session = this.mvc.perform(post("/login").param("username", "fer").param("password", "pass"))
                .andExpect(status().is(HttpStatus.FOUND.value()))
                .andExpect(redirectedUrl("/ads"))
                .andReturn()
                .getRequest()
                .getSession();

        Assert.assertNotNull(session);

        this.mvc.perform(
                post("/ads/create")
                    .session((MockHttpSession) session)
                    .param("title", "test")
                    .param("description", "for sale"))
                .andExpect(status().isOk());
    }

}
