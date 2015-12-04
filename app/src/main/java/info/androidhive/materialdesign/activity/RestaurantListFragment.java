package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.RestaurantListAdapter;
import info.androidhive.materialdesign.model.Restaurant;

/**
 * Created by Edvinas.Barickis on 12/1/2015.
 */
public class RestaurantListFragment extends Fragment{
    private OnFragmentInteractionListener mListener;

    public RestaurantListFragment() {
        // Required empty public constructor
    }

    MainActivity activity;
    List<Restaurant> restaurants;
    RecyclerView listView;
    RestaurantListAdapter adapter;
    // public ArrayList<RestaurantListModel> RestaurantListItems = new ArrayList<RestaurantListModel>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        LinearLayoutManager llm = new LinearLayoutManager(activity);
        listView = (RecyclerView) view.findViewById(R.id.restaurantList);
        TextView emptyView = (TextView) view.findViewById(R.id.restaurant_list_empty_view);

        if (restaurants.isEmpty()) {
            listView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
        adapter = new RestaurantListAdapter(activity, restaurants, this);
        listView.setLayoutManager(llm);
        listView.setAdapter(adapter);

        return view;
    }

    private RestaurantFragment restaurantFragment;

    public void onItemClick(int mPosition) {
        if (restaurantFragment == null) {
            restaurantFragment = new RestaurantFragment();
        }

        restaurantFragment.restaurant = restaurants.get(mPosition);
        activity.changeFragment(R.id.container_body, restaurantFragment);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}
