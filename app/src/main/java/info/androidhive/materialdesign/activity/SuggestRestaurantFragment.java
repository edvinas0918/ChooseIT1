package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.model.Restaurant;

/**
 * Created by Edvinas.Barickis on 12/4/2015.
 */
public class SuggestRestaurantFragment extends Fragment {

    public SuggestRestaurantFragment() {
        // Required empty public constructor
    }

    private MainActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_suggest_restaurant, container, false);

        Button createButton = (Button)view.findViewById(R.id.btn_suggest_restaurant);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                String restaurantName = ((EditText)activity.findViewById(R.id.et_restaurant_name)).getText().toString();
                String restaurantAddress = ((EditText)activity.findViewById(R.id.et_restaurant_address)).getText().toString();
                String phoneNumber = ((EditText)activity.findViewById(R.id.et_phone_number)).getText().toString();

                Restaurant newRestaurant = new Restaurant ();
                newRestaurant.setRestName(restaurantName);
                newRestaurant.setAddress(restaurantAddress);
                newRestaurant.setPhoneNumber(phoneNumber);

                activity.saveNewRestaurant(newRestaurant);
                RestaurantListFragment fragment = new RestaurantListFragment();
                ((RestaurantListFragment)fragment).restaurants = activity.getRestaurantList(activity.GetSeekBarBrogress());
                activity.changeFragment(R.id.container_body, fragment);
            }
        });

        activity.getSupportActionBar().setTitle("Suggest Restaurant");

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
