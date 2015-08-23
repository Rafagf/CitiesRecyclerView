package com.example.rafagarcia.citiesrecyclerview;

/**
 * Created by Rafael Garcia on 23/08/15.
 */
public class City {

    private String name;
    private String urlImage;

    public City(String name, String urlImage){

        this.name = name;
        this.urlImage = urlImage;
    }

    public String getName(){

        return name;
    }

    public String getUrlImage(){

        return urlImage;
    }
}
