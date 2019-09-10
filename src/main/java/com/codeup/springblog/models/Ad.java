package com.codeup.springblog.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ads")
public class Ad {

    @Id @GeneratedValue
    private long id;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = false)
    private String description;

    @OneToOne
    private User user;

    // This one does not create a column
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ad")
    private List<AdImage> images;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="ads_categories",
            joinColumns={@JoinColumn(name="ad_id")},
            inverseJoinColumns={@JoinColumn(name="category_id")}
    )
    private List<AdCategory> categories;

    // Useful to create a new instance of the Ad
    public Ad() {
    }

    // Constructor for everything for the R of (CRUD)
    public Ad(long id, String title, String description, User user, List<AdImage> imgs, List<AdCategory> categories) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.user = user;
        this.images = imgs;
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<AdImage> getImages() {
        return images;
    }

    public void setImages(List<AdImage> images) {
        this.images = images;
    }

    public List<AdCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<AdCategory> categories) {
        this.categories = categories;
    }
}
