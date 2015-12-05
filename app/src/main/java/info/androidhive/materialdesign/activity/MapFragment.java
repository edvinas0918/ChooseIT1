package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.model.Restaurant;

/**
 * Created by Edvinas.Barickis on 12/2/2015.
 */
public class MapFragment extends SupportMapFragment implements LocationListener, OnMapReadyCallback {

    private MainActivity activity;
    public List<Restaurant> restaurantList;

    public MapFragment() {
        super();
        restaurantList = new ArrayList<>();
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity)getActivity();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        initMap();
        activity.getSupportActionBar().setTitle("Map");
        return v;
    }

    private void initMap() {
        UiSettings settings = getMap().getUiSettings();
        settings.setAllGesturesEnabled(false);
        settings.setMyLocationButtonEnabled(true);
        settings.setAllGesturesEnabled(true);
        settings.setMyLocationButtonEnabled(true);

        LatLng location = activity.getLocationLatLng();
        getMap().addMarker(new MarkerOptions().position(location));
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        addRestaurantMarkersToMap();
    }

    public void addRestaurantMarkersToMap() {

        for (Restaurant restaurant : restaurantList){
            if (restaurant.getLongtitude() != 0) {
                getMap().addMarker(new MarkerOptions().position(activity.getRestaurantLocationLatLng(restaurant))).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(activity.getRestaurantLocationLatLng(restaurant), 15));
            }
        }

      /*  List<Restaurant> restaurants = activity.getRestaurantList();
        for (Restaurant restaurant:restaurants) {
            getMap().addMarker(new MarkerOptions().position(activity.getRestaurantLocation(restaurant)));
        }*/
    }



    /************methods to implement****************/

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
