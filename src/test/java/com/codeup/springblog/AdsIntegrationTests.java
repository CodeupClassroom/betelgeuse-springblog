package com.codeup.springblog;

import com.codeup.springblog.models.Ad;
import com.codeup.springblog.models.User;
import com.codeup.springblog.repos.AdRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpSession;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringblogApplication.class)
@AutoConfigureMockMvc
public class AdsIntegrationTests {

    private User testUser;
    HttpSession httpSession;

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserRepository userDao;

    @Autowired
    AdRepository adsDao;

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setup() throws Exception {

        testUser = userDao.findByUsername("testUser");

        // Creates the test user if not exists
        if(testUser == null){
            User newUser = new User();
            newUser.setUsername("testUser");
            newUser.setPassword(passwordEncoder.encode("pass"));
            newUser.setEmail("testUser@codeup.com");
            testUser = userDao.save(newUser);
        }

        // Throw a Post request to /login and expect a redirection to the Ads index page after being logged in
        httpSession = this.mvc.perform(post("/login").with(csrf())
                .param("username", "testUser")
                .param("password", "pass"))
                .andExpect(status().is(HttpStatus.FOUND.value()))
                .andExpect(redirectedUrl("/ads"))
                .andReturn()
                .getRequest()
                .getSession();
    }

    // Sanity Test, just to make sure the mvc bean is working
    @Test
    public void contextLoads() {
        assertThat(mvc).isNotNull();
    }

    @Test
    public void testIfUserSessionIsActive() throws Exception {
        // Make sure the returned session is not null
        Assert.assertNotNull(httpSession);
    }

    @Test
    public void testCreateAd() throws Exception {
        // Throw a Post request to /ads/create and expect a redirection to the Ad
        this.mvc.perform(
                post("/ads/create").with(csrf())
                    .session((MockHttpSession) httpSession)
                    .param("title", "test")
                    .param("description", "for sale"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testEditAd() throws Exception {
        Ad existingAd = adsDao.findAll().get(0);
        // Throw a Post request to /ads/{id}/edit and expect a redirection to the Ad
        this.mvc.perform(
                post("/ads/" + existingAd.getId() + "/edit").with(csrf())
                        .session((MockHttpSession) httpSession)
                        .param("title", "edited title")
                        .param("description", "edited description"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testShowAd() throws Exception {
        Ad existingAd = adsDao.findAll().get(0);
        this.mvc.perform(get("/ads/" + existingAd.getId()))
                .andExpect(status().isOk())
                // Test the dynamic content of the page
                .andExpect(content().string(containsString(existingAd.getDescription())));
    }

    @Test
    public void testAdsIndex() throws Exception {
        Ad existingAd = adsDao.findAll().get(0);
        this.mvc.perform(get("/ads"))
                .andExpect(status().isOk())
                // Test the static content of the page
                .andExpect(content().string(containsString("Latest ads")))
                // Test the dynamic content of the page
                .andExpect(content().string(containsString(existingAd.getTitle())));
    }

    @Test
    public void testDeleteAd() throws Exception {

        this.mvc.perform(
                post("/ads/create").with(csrf())
                        .session((MockHttpSession) httpSession)
                        .param("title", "ad to be deleted")
                        .param("description", "won't last long"))
                .andExpect(status().is3xxRedirection());

        Ad existingAd = adsDao.findByTitle("ad to be deleted");
        // Throw a Post request to ads/{id}/delete and expect a redirection to the Ads index
        this.mvc.perform(
                post("/ads/" + existingAd.getId() + "/delete").with(csrf())
                        .session((MockHttpSession) httpSession)
                        .param("id", String.valueOf(existingAd.getId())))
                .andExpect(status().is3xxRedirection());
    }
}