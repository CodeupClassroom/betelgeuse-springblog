package com.codeup.springblog.models;

import javax.persistence.*;

@Entity
@Table(name="ad_images")
public class AdImage {
    @Id @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String path;

    @ManyToOne
    @JoinColumn (name = "ad_id")
    private Ad ad;

    public AdImage() {
    }

    public AdImage(long id, String path, Ad ad) {
        this.path = path;
        this.ad = ad;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }
}