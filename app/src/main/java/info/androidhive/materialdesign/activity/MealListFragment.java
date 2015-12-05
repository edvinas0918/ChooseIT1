package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.MealListAdapter;
import info.androidhive.materialdesign.adapter.RestaurantListAdapter;
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
    public List<Meal> MealsListItems;
    RecyclerView listView;
    MealListAdapter adapter;

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

        LinearLayoutManager llm = new LinearLayoutManager(activity);
        listView = (RecyclerView) view.findViewById(R.id.mealList);
        TextView emptyView = (TextView) view.findViewById(R.id.meal_list_empty_view);


        List<Meal> mealList = activity.getRestaurantMeals(restaurant, Double.valueOf(activity.GetSpinnerPriceFrom()),Double.valueOf( activity.GetSpinnerPriceTo()));
        adapter = new MealListAdapter(activity, (ArrayList)mealList, this);

        if (mealList.isEmpty()) {
            listView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

        listView.setLayoutManager(llm);
        listView.setAdapter(adapter);
        activity.getSupportActionBar().setTitle("Meals");
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
