package com.kychow.jayjoska.models;


/* @brief Extends Place model. but Destination model will be used to show chosen locations for user's final itinerary*/
public class Destination extends Place {
    private int minutesSpent;

    public Destination() {
        super();
        this.minutesSpent = 0;
    }

    public int getMinutesSpent() {
        return minutesSpent;
    }
}
