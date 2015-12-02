package info.androidhive.materialdesign.model;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edvinas.Barickis on 12/2/2015.
 */
public class Restaurant {
    private String restName;
    private String address;
    private double lattitude;
    private double longtitude;
    private List<Meal> mealList;
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String adress) {
        this.address = adress;
    }

    public Meal getMeal(int id) {
        return mealList.get(id);
    }

    public List<Meal> getMealList () {
        return mealList;
    }
    public void setMealList(List<Meal> mealList) {
        if (mealList == null) {
            this.mealList = new ArrayList<Meal>();
        } else {
            this.mealList = mealList;
        }


    }

    public void addMeal (Meal meal) {
        if (mealList == null) {
            this.mealList = new ArrayList<Meal>();
        }
        this.mealList.add(meal);
    }

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public float getDistanceTo (Restaurant restaurant) {
        return getLocation().distanceTo(restaurant.getLocation());
    }

    public Location getLocation () {
        Location loc = new Location("");
        loc.setLatitude(lattitude);
        loc.setLongitude(longtitude);
        return loc;
    }
}
