package info.androidhive.materialdesign.model;

/**
 * Created by Edvinas.Barickis on 12/2/2015.
 */
public class Meal {
    private String mealName;
    private double Price;
    private String Ingredients;

    public String getIngredients() {
        return Ingredients;
    }

    public void setIngredients(String ingredients) {
        Ingredients = ingredients;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }
}
