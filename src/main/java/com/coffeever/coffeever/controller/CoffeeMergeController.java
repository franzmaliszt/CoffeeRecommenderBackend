package com.coffeever.coffeever.controller;


import com.coffeever.coffeever.model.CoffeeMerged;
import com.coffeever.coffeever.model.Pagination;
import com.coffeever.coffeever.model.User;
import com.coffeever.coffeever.service.CoffeeMergeService;
import com.coffeever.coffeever.service.UserCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CoffeeMergeController {

    @Autowired
    CoffeeMergeService coffeeMergeService;
    UserCrudService userCrudService;

    @GetMapping("/getSingleCoffee")
    public CoffeeMerged singleCoffee(@RequestBody CoffeeMerged coffeeMerged) {

        return coffeeMergeService.getCoffeeById(coffeeMerged);
    }

    @GetMapping("/getSomeCoffee")
    public List<CoffeeMerged> findAllBeans(@RequestBody Pagination page) {

        return coffeeMergeService.getAllCoffees(page.getOffset(), page.getLimit());
    }

    @GetMapping("/getAllCoffees")
    public List<CoffeeMerged> findAllBeans() {

        return coffeeMergeService.getAllCoffees();
    }

    @GetMapping("/findBestMatch")
    public List<CoffeeMerged> findBestMatches(@RequestBody CoffeeMerged coffeeMerged) {

        return  coffeeMergeService.findBestMatch(coffeeMerged.getAroma(),coffeeMerged.getAcidity(),
                coffeeMerged.getBody(),coffeeMerged.getFlavor(),
                coffeeMerged.getDecaf(),coffeeMerged.getKeywords());

    }

    @GetMapping("/addFavorite")
    public void addFavorite(@RequestBody User user){

        coffeeMergeService.addFavorite(user.getGoogle_id(), user.getFavorites());
    }

    @GetMapping("/deleteUserFavorite")
    public void deleteUserFavorite(@RequestBody User user){

        coffeeMergeService.deleteUserFavorite(user.getGoogle_id(), user.getFavorites());
    }

    @GetMapping("/getUserFavorite")
    public List<CoffeeMerged> getUserFavorite(@RequestBody User user){

        return coffeeMergeService.getUserFavorites(user.getGoogle_id());
    }

    @GetMapping("/findBasedOnFavs")
    public List<CoffeeMerged> findBasedOnFavs(@RequestBody User user){

        return coffeeMergeService.findBasedOnFavs(user.getGoogle_id());
    }
}