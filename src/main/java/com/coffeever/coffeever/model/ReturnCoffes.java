package com.coffeever.coffeever.model;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReturnCoffes {

    private int aroma;

    private int acidity;

    private int body;

    private int flavor;

    private String keyword;

    public ReturnCoffes(int aroma, int acidity, int body, int flavor, String keyword) {
        this.aroma = aroma;
        this.acidity = acidity;
        this.body = body;
        this.flavor = flavor;
        this.keyword = keyword;
    }
}
