package com.codeup.springblog;

import com.codeup.springblog.controllers.AdController;
import com.codeup.springblog.models.Ad;
import com.codeup.springblog.models.AdImage;
import com.codeup.springblog.models.User;
import com.codeup.springblog.repos.UserRepository;
import com.codeup.springblog.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AdsControllerIntegrationTests {

    private User testUser;

    @Autowired
    AdController adController;

    @Autowired
    UserRepository userDao;

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setup(){
        testUser = userDao.findByUsername("testUser");
        // Creates the test user if not exists
        if(testUser == null){
            User newUser = new User();
            newUser.setUsername("testUser");
            newUser.setPassword(passwordEncoder.encode("pass"));
            newUser.setEmail("testUser@codeup.com");
            testUser = userDao.save(newUser);
        }
        userService.authenticate(testUser);
    }

    @Test
    @Transactional
    public void testCreateAd(){
        Ad newAd = new Ad();

        List<AdImage> images = new ArrayList<>();
        images.add(new AdImage("https://codeup.com/wp-content/uploads/2019/10/codeup_classroom-san-antonio.jpg", newAd));
        images.add(new AdImage("https://codeup.com/wp-content/uploads/2019/10/code-up-learning-class-8.jpg", newAd));
        newAd.setTitle("test");
        newAd.setDescription("random");
        newAd.setImages(images);
        newAd.setCategories(null);

        adController.createAd(newAd);

    }

}
