package com.coffeever.coffeever.service;

import com.coffeever.coffeever.model.CoffeeMerged;

import java.util.ArrayList;
import java.util.List;

public class ItemBasedFiltering {

    public List<CoffeeMerged> bestMatch(CoffeeMerged pref, Iterable<CoffeeMerged> allBeans) {

        List<CoffeeMerged> bestMatches = new ArrayList<>();
        int n = 5;                                                        // Amount of item to be returned
        double gap;
        for (int i = 0; i < n; i++) {
            CoffeeMerged closestBean = new CoffeeMerged(0, 0, 0, 0, 0, "");
            gap = 22;                                                     // Max difference possible
            for (CoffeeMerged bean : allBeans) {
                if (bestMatches.contains(bean) || pref.getDecaf() != bean.getDecaf()) // Don't compare if it already in the list or decaf is different
                    continue;
                double distance = euclidean(pref, bean);
                if (distance < gap) {
                    gap = distance;
                    closestBean = bean;
                }
            }
            bestMatches.add(closestBean);
        }
        return bestMatches;
    }

    // Gets b1 as ideal coffee, b2 as the currently compared item. Maps them in 5D space as 2 points and find the
    // distance between them.
    private double euclidean(CoffeeMerged b1, CoffeeMerged b2) {
        String[] b1_keys, b2_keys;

        b1_keys = b1.getKeywords().replaceAll("\\s+", "").toLowerCase().split(",");

        if (b2.getKeywords() == null)
            b2_keys = new String[0];
        else
            b2_keys = b2.getKeywords().replaceAll("\\s+", "").toLowerCase().split(",");

        // Count how many of the keywords have matches in compared bean
        int count = 0;
        for (String i : b1_keys)
            for (String j : b2_keys)
                if (i.equals(j))
                    count++;

        return Math.sqrt(Math.pow((b1.getAroma() - b2.getAroma()), 2) +
                Math.pow((b1.getAcidity() - b2.getAcidity()), 2) +
                Math.pow((b1.getBody() - b2.getBody()), 2) +
                Math.pow((b1.getFlavor() - b2.getFlavor()), 2) +
                Math.pow((b1_keys.length - count), 2)
        );
    }

    public List<CoffeeMerged> bestMatchForFavs(List<CoffeeMerged> favs, Iterable<CoffeeMerged> allBeans) {

        float avgAroma = 0, avgAcidity = 0, avgBody = 0, avgFlavor = 0;           // Initialized attributes to store average values
        ArrayList<String> keywords = new ArrayList<>();                         // To store the union of all the keywords
        List<CoffeeMerged> bestMatches = new ArrayList<>();                     // To store the coffees to be returned

        for (CoffeeMerged sample : favs){                                       // To calculate attributes of average coffee
            avgAroma += sample.getAroma()/(float)favs.size();
            avgAcidity += sample.getAcidity()/(float)favs.size();
            avgBody += sample.getBody()/(float)favs.size();
            avgFlavor += sample.getFlavor()/(float)favs.size();

            if (sample.getKeywords() != null){
                for (String key : sample.getKeywords().replaceAll("\\s+", "").toLowerCase().split(",")){
                    if (!keywords.contains(key)){
                        keywords.add(key);
                    }
                }
            }
        }

        String allKeys = String.join(", ", keywords);                   // Turn the keywords into a string again
        CoffeeMerged pref = new CoffeeMerged(Math.round(avgAroma), Math.round(avgAcidity), Math.round(avgBody), Math.round(avgFlavor), 0, allKeys);

        int n = 3;                                                        // Amount of item to be returned
        double gap;
        for (int i = 0; i < n; i++) {
            CoffeeMerged closestBean = new CoffeeMerged(0, 0, 0, 0, 0, "");     // Best match in each iteration
            gap = 22;                                            // Max difference possible

            for (CoffeeMerged bean : allBeans) {
                if (bestMatches.contains(bean) || favs.contains(bean))   // Don't compare if it already in the list or
                    continue;                                            // favorites.
                double distance = euclidean(pref, bean);
                if (distance < gap) {                  // True if bean is more similar than the currently compared one
                    gap = distance;                    // Update the similarity
                    closestBean = bean;                // Update the currently compared one
                }
            }
            bestMatches.add(closestBean);              // Add the bean to the list to be returned
        }
        return bestMatches;
    }
}