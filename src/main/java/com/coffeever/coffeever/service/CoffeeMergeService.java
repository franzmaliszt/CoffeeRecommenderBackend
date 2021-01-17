package com.coffeever.coffeever.service;

import com.coffeever.coffeever.model.CoffeeMerged;
import com.coffeever.coffeever.model.User;
import com.coffeever.coffeever.repository.CoffeeMergedRepo;
import com.coffeever.coffeever.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoffeeMergeService {

    @Autowired
    CoffeeMergedRepo coffeeMergedRepo;
    @Autowired
    UserRepository userRepository;

    //databaseden id ile coffee döndürür
    public CoffeeMerged getCoffeeById(CoffeeMerged coffeeMerged) {
//        return coffeeMergedRepo.findById(coffeeMerged.getSlug()).orElse(null);
        return coffeeMergedRepo.findById(coffeeMerged.getSlug()).orElse(new CoffeeMerged());
    }

    // Return best match based on given 6 main parameters
    public List<CoffeeMerged> findBestMatch(int aroma, int acidity, int body, int flavor, int decaf, String keywords) {
        CoffeeMerged askedBean = new CoffeeMerged(aroma, acidity, body, flavor, decaf, keywords);
        ItemBasedFiltering itemBasedFiltering = new ItemBasedFiltering();

        return itemBasedFiltering.bestMatch(askedBean, getAllCoffees());
    }

    //  databasedeki coffee listesini döndürür
    public List<CoffeeMerged> getAllCoffees() {
        return coffeeMergedRepo.findAll();
    }

    //  databasedeki coffee listesini verilen index aralığında döndürür
    public List<CoffeeMerged> getAllCoffees(int offset, int limit) {
        List<CoffeeMerged> allCoffees = coffeeMergedRepo.findAll();

        return allCoffees.subList(offset, offset + limit);
    }

    //  Returns 3 coffees based on a user's favorites
    public List<CoffeeMerged> findBasedOnFavs(long google_id){
        ItemBasedFiltering itemBasedFiltering = new ItemBasedFiltering();   // To access filtering methods defined in ItemBasedFiltering

        //User user = userRepository.findById(google_id).orElse(null);  // To copy user with given id
        User user = userRepository.findById(google_id).orElse(new User());
        List<CoffeeMerged> favList= new ArrayList<CoffeeMerged>();          // To send favorite coffees in a list

        String[] favs = new String[0];                                      // To store the id's of favorites in a string array first
        if (user != null) {
            favs = user.getFavorites().replaceAll("\\s+", "").toLowerCase().split(",");
        }
        else
            return null;

        for (String sample : favs){                                         // To add CoffeeMerged objects in a list after reading their id's as String
//            favList.add(coffeeMergedRepo.findById(sample).orElse(null));
            favList.add(coffeeMergedRepo.findById(sample).orElse(new CoffeeMerged()));
        }

        return itemBasedFiltering.bestMatchForFavs(favList, getAllCoffees());                // Send a Coffee list, get another Coffee List to return
    }

    //  Adds a favorite coffee to a user's favorites.
    public void addFavorite(long google_id, String slug){

        User user = userRepository.findById(google_id).orElse(new User());
//        User user = userRepository.findById(google_id).orElse(null);
//        assert user != null;
        user.addFavorites(slug);

        userRepository.save(user);
    }

    public void deleteUserFavorite(long google_id, String slug){

//        User user = userRepository.findById(google_id).orElse(null);
        User user = userRepository.findById(google_id).orElse(new User());
//        assert user != null;
        user.deleteFavorite(slug);

        userRepository.save(user);
    }

    public List<CoffeeMerged> getUserFavorites(long google_id){

        List<CoffeeMerged> favList = new ArrayList<CoffeeMerged>();

        User user = userRepository.findById(google_id).orElse(new User());
//        User user = userRepository.findById(google_id).orElse(null);
//        assert user != null;

        String[] userFavs = user.getFavorites().replaceAll("\\s+","").split(",");
        for(String fav : userFavs){
//            favList.add(coffeeMergedRepo.findById(fav).orElse(null));
            favList.add(coffeeMergedRepo.findById(fav).orElse(new CoffeeMerged()));
        }

        return favList;
    }
}
