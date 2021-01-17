package com.coffeever.coffeever.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coffee_merged")
public class CoffeeMerged {

    @Id
    @Column(name = "slug")
    private String slug;

    @Column(name = "aroma")
    private int aroma;

    @Column(name = "acidity")
    private int acidity;

    @Column(name = "body")
    private int body;

    @Column(name = "flavor")
    private int flavor;

    @Column(name = "roast")
    private String roast;

    @Column(name = "region")
    private String region;

    @Column(name = "decaf")
    private int decaf;

    @Column(name = "name")
    private String name;

    @Column(name = "roaster")
    private String roaster;

    @Column(name = "rating")
    private int rating;

    @Column(name = "location")
    private String location;

    @Column(name = "blind_assessment")
    private String blindAssessment;

    @Column(name = "notes")
    private String notes;

    @Column(name = "who_should_drink")
    private String whoShouldDrink;

    @Column(name = "keywords")
    private String keywords;


    public CoffeeMerged(int aroma,int acidity,int body,int flavor,int decaf,String keyword) {
        this.aroma = aroma;
        this.acidity = acidity;
        this.body = body;
        this.flavor = flavor;
        this.decaf=decaf;
        this.keywords = keyword;
    }
}