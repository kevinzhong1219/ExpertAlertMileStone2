package com.example.expertalert;

import java.util.Comparator;

/**
 * A custom comparator that compare groceries by expire date
 */
public class DateComparator implements Comparator<Grocery> {

    /**
     * Compare two given groceries by date
     * @param grocery1 first grocery
     * @param grocery2 second grocery
     * @return
     */
    @Override
    public int compare(Grocery grocery1, Grocery grocery2){
        String date1 = grocery1.getDate();
        String date2 = grocery2.getDate();
        if(date1 != date2){
            return date1.compareTo(date2);
        }
        else{
            String imageId1 = grocery1.getImageId();
            String imageId2 = grocery2.getImageId();
            return imageId1.compareTo(imageId2);
        }

    }
}
