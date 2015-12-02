package info.androidhive.materialdesign.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.model.Meal;
import info.androidhive.materialdesign.model.Restaurant;

/**
 * Created by Edvinas.Barickis on 12/2/2015.
 */
public class MealListFragment extends Fragment {

    public Restaurant restaurant;

    public MealListFragment() {
        // Required empty public constructor
    }

    private MainActivity activity;
    public ArrayList<Meal> MealsListItems = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_list, container, false);

        //setListData();
        ((TextView)view.findViewById(R.id.tv_restaurant_name)).setText(restaurant.getRestName());
        ((TextView)view.findViewById(R.id.tv_adress)).setText(restaurant.getAddress());
        ((TextView)view.findViewById(R.id.tv_phone_nr)).setText(restaurant.getPhoneNumber());

        Button mapButton = (Button) view.findViewById(R.id.btn_map);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapFragment mf = new MapFragment();
                mf.restaurant = restaurant;
                activity.changeFragment(R.id.container_body, mf);
            }
        });
        /*Resources res = getResources();
        list = (ListView) view.findViewById(R.id.lv_mealsList);


        List<Meal> mealList = activity.getRestaurantMeals(restaurant, Double.valueOf(activity.GetSpinnerPriceFrom()),Double.valueOf( activity.GetSpinnerPriceTo()));
        adapter = new MealsListAdaptor(activity, this, (ArrayList)mealList, res);
        list.setAdapter(adapter);

        Button mapButton = (Button) view.findViewById(R.id.btn_map);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapFragment mf = new MapFragment();
                mf.restaurant = restaurant;
                ((MainActivity) getActivity()).changeFragment(R.id.main, mf);
            }
        });*/
        return view;
    }
}