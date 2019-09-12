package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Ad;
import com.codeup.springblog.models.User;
import com.codeup.springblog.repos.AdRepository;
import com.codeup.springblog.repos.UserRepository;
import com.codeup.springblog.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdController {

    private final AdRepository adDao;
    private final UserRepository userDao;

    public AdController(AdRepository adRepository, UserRepository userRepo){
        this.userDao = userRepo;
        this.adDao = adRepository;
    }

    @Autowired
    private EmailService emailService;

    @GetMapping("/ads")
    public String index(Model vModel) {
        Iterable<Ad> ads = adDao.findAll();
        vModel.addAttribute("ads", ads);
        return "ads/index";
    }

    @GetMapping("/ads/{id}")
    public String show(@PathVariable long id, Model viewModel) {
        Ad ad = adDao.findOne(id);
        viewModel.addAttribute("ad", ad);
        return "ads/show";
    }

    @GetMapping("/ads/search")
    public String show(@RequestParam(name = "term") String term, Model viewModel) {
        List<Ad> ads = adDao.searchByTitleLike(term);
        viewModel.addAttribute("ads", ads);
        return "ads/index";
    }

    @GetMapping("/ads/{id}/edit")
    public String edit(@PathVariable long id, Model viewModel) {
        Ad ad = adDao.findOne(id);
        viewModel.addAttribute("ad", ad);
        return "ads/edit";
    }

    @PostMapping("/ads/{id}/edit")
    public String update(@PathVariable long id,
                         @RequestParam(name = "title") String title,
                         @RequestParam(name = "description") String description,
                         Model viewModel) {
        Ad adToBeUpdated = adDao.findOne(id);
        adToBeUpdated.setTitle(title);
        adToBeUpdated.setDescription(description);
        adDao.save(adToBeUpdated);
        return "redirect:/ads/" + adToBeUpdated.getId();
    }

    @PostMapping("/ads/{id}/delete")
    public String delete(@PathVariable long id){
        adDao.delete(id);
        return "redirect:/ads";
    }

    @GetMapping("/ads/create")
    public String showCreateForm(Model model) {
        model.addAttribute("ad", new Ad());
        return "ads/create";
    }

    @PostMapping("/ads/create")
    public String createAd(
            @ModelAttribute Ad adPassedIn
    ) {
        User userSession = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User userDB = userDao.findOne(userSession.getId());
        adPassedIn.setUser(userDB);

        Ad savedAd = adDao.save(adPassedIn);
        emailService.prepareAndSend(
                savedAd,
                "Ad created",
                String.format("Ad with the id %d has been created", savedAd.getId()));
        return "redirect:/ads/" + savedAd.getId();
    }
}
