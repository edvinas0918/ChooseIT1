package info.androidhive.materialdesign.activity;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.model.Meal;
import info.androidhive.materialdesign.model.Restaurant;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{

    private int seekBarProgress;
    private String spinnerPriceFrom;
    private String spinnerPriceTo;

    public void SetSeekBarProgress(int seekBarProgress) {
        this.seekBarProgress = seekBarProgress;
    }

    public int GetSeekBarBrogress() {
        return this.seekBarProgress;
    }

    public void SetSpinnerPriceFrom(String spinnerPriceFrom) {
        this.spinnerPriceFrom = spinnerPriceFrom;
    }

    public String GetSpinnerPriceFrom() {
        return this.spinnerPriceFrom;
    }

    public void SetSpinnerPriceTo(String spinnerPriceTo) {
        this.spinnerPriceTo = spinnerPriceTo;
    }

    public String GetSpinnerPriceTo() {
        return this.spinnerPriceTo;
    }

    public void saveOptionsValues(int seekBarValue, String spinnerFromValue, String spinnerToValue) {

        SetSeekBarProgress(seekBarValue);
        SetSpinnerPriceFrom(spinnerFromValue);
        SetSpinnerPriceTo(spinnerToValue);
    }

    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveOptionsValues(10, "0", "10");

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        RestaurantListFragment rlf = new RestaurantListFragment();
        rlf.restaurants = getRestaurantList();

        changeFragment(R.id.container_body, rlf);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_search){
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new RestaurantListFragment();
                ((RestaurantListFragment)fragment).restaurants = getRestaurantList(GetSeekBarBrogress());
                title = getString(R.string.title_restaurantList);
                break;
            case 1:
                fragment = new MapFragment();
                ((MapFragment)fragment).restaurantList = getRestaurantList(GetSeekBarBrogress());
                title = getString(R.string.title_restaurants_in_map);
                break;
            case 2:
                fragment = new SettingsFragment();
                title = getString(R.string.title_settings);
                break;
            default:
                break;
        }

        if (fragment != null) {
            changeFragment(R.id.container_body, fragment);
            getSupportActionBar().setTitle(title);
        }
    }

    public List<Restaurant> getRestaurantList(int distance) {
        List<Restaurant> allRestaurants = getRestaurantList();
        List<Restaurant> resultRestaurants = new ArrayList<>();
        LatLng currentLoc = getLocationLatLng();
        Location currentLocation = new Location("Current");
        currentLocation.setLatitude(currentLoc.latitude);
        currentLocation.setLongitude(currentLoc.longitude);

        for (int i = 0; i < allRestaurants.size(); i++) {
            Restaurant restaurant = allRestaurants.get(i);
           /* Location restaurantLocation = new Location("RestaurantLocation");
            restaurantLocation.setLatitude(restaurant.getLattitude());
            restaurantLocation.setLongitude(restaurant.getLongtitude());*/


            if (currentLocation.distanceTo(restaurant.getLocation()) <= distance * 1000) {
                resultRestaurants.add(restaurant);
            }
        }
        return resultRestaurants;
    }

    public List<Meal> getRestaurantMeals(Restaurant restaurant, double minPrice, double maxPrice) {
        List<Meal> allMeals = restaurant.getMealList();
        List<Meal> resultMeals = new ArrayList<>();

        for (int i = 0; i < allMeals.size(); i++) {
            double price = allMeals.get(i).getPrice();
            if (price >= minPrice && price <= maxPrice) {
                resultMeals.add(allMeals.get(i));
            }
        }

        return resultMeals;
    }

    /************************ Reading restaurant data from file**************************/
    private List<Restaurant> restaurantList;

    public List<Restaurant> getRestaurantList() {
        if (restaurantList == null) {
            restaurantList = readRestaurantListData();
        }
        return restaurantList;
    }

    public List<Restaurant> readRestaurantListData() {
        List<Restaurant> restaurantList = new ArrayList<Restaurant>();

        JSONArray restaurants = readRestaurantDataFromFile();
        try {
            for (int i = 0; i < restaurants.length(); i++) {
                JSONObject obj = (JSONObject) restaurants.get(i);
                Restaurant restaurant = new Restaurant();
                restaurant.setAddress(obj.getString("address"));
                restaurant.setRestName(obj.getString("name"));
                restaurant.setLattitude(obj.getDouble("Latitude"));
                restaurant.setLongtitude(obj.getDouble("Longtitude"));
                restaurant.setPhoneNumber(obj.getString("phoneNumber"));
                restaurant.setMealList(getMealListFromRestaurantJSONObject(obj));
                restaurantList.add(restaurant);
            }
        } catch (Exception ex) {

        }
        return restaurantList;
    }

    public List<Meal> getMealListFromRestaurantJSONObject(JSONObject restaurant) {
        List<Meal> mealList = new ArrayList<Meal>();

        try {
            JSONArray mealJSONArray = restaurant.getJSONArray("Meals");
            for (int i = 0; i < mealJSONArray.length(); i++) {
                JSONObject obj = (JSONObject) mealJSONArray.get(i);

                Meal meal = new Meal();
                meal.setIngredients(obj.getString("Ingredients"));
                meal.setMealName(obj.getString("Name"));
                meal.setPrice(obj.getDouble("Price"));
                mealList.add(meal);
            }
        } catch (Exception ex) {
        }

        return mealList;
    }

    public JSONArray readRestaurantDataFromFile() {
        JSONArray result = null;
        try {
            JSONObject obj = new JSONObject(readJSONStringFromFile());
            result = obj.getJSONArray("Restaurants");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String readJSONStringFromFile() {
        String json = null;
        try {
            InputStream is = getResources().openRawResource(R.raw.restaurant_data);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    /************************ Reading restaurant data from file end**************************/

    public void changeFragment(int containerId, Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(containerId, fragment);
        ft.addToBackStack(null);
        ft.commit();
        Log.w(TAG, "changeFragment");
    }

    /************************ Location **************************/
    public Location getLocation(){
        Location loc = new Location("Current");
        LatLng currentLatLng = getLocationLatLng();

        loc.setLatitude(currentLatLng.latitude);
        loc.setLongitude(currentLatLng.longitude);

        return loc;
    }

    public LatLng getLocationLatLng() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        Location bestLocation = null;
        for (String provider : locationManager.getProviders(true)) {
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }

        return new LatLng(bestLocation.getLatitude(), bestLocation.getLongitude());
    }

    public LatLng getRestaurantLocationLatLng(Restaurant restaurant) {
        return new LatLng(restaurant.getLattitude(), restaurant.getLongtitude());
    }
}