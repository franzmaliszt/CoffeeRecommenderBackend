package com.coffeever.coffeever.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user1")
public class User {

    @Id
    private long google_id;

    @Column
    private String name;

    @Column
    private String mail;

    @Column
    private String favorites;

    public void addFavorites(String favorites) {
        String newEntry = favorites + ", ";
        this.favorites += newEntry;
    }

    public void deleteFavorite(String slug){
        String toBeDeleted = slug + ", ";
        this.favorites = this.favorites.replace(toBeDeleted, "");
    }

    public User(long google_id, String favorites) {
        this.google_id = google_id;
        this.favorites = favorites;
    }
}
