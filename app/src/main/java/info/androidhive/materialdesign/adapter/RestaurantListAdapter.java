package info.androidhive.materialdesign.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.activity.MainActivity;
import info.androidhive.materialdesign.activity.RestaurantListFragment;
import info.androidhive.materialdesign.model.Restaurant;

/**
 * Created by Edvinas.Barickis on 12/1/2015.
 */
public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.MyViewHolder> {
    List<Restaurant> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    private Fragment fragment;

    public RestaurantListAdapter(Context context, List<Restaurant> data, Fragment f) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        fragment = f;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.restaurant_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (data.size() <= 0) {
            holder.tv_name.setText("No Data");
        } else {
            Restaurant tempValues = data.get(position);

            holder.tv_name.setText(tempValues.getRestName());
            holder.tv_description.setText(tempValues.getAddress());
            Float distance = ((MainActivity) context).getLocation().distanceTo(tempValues.getLocation()) / 1000;
            String distanceString = String.format("%.1f km", distance);
            holder.tv_distance.setText(distanceString);
            /*holder.image.setImageResource(
                    res.getIdentifier(
                            "com.example.kasparas.choseit:drawable/"+tempValues.getImage()
                            ,null,null));*/
            holder.thisView.setOnClickListener(new OnItemClickListener(position));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public View thisView;
        public TextView tv_name;
        public TextView tv_description;
        public TextView tv_distance;

        public MyViewHolder(View itemView) {
            super(itemView);
            thisView = itemView;
            tv_name = (TextView) itemView.findViewById(R.id.tv_restaurantName);
            tv_description = (TextView) itemView.findViewById(R.id.tv_restaurantDescription);
            tv_distance = (TextView) itemView.findViewById(R.id.tv_restaurantDistance);
        }
    }

    private class OnItemClickListener implements RecyclerView.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {
            ((RestaurantListFragment) fragment).onItemClick(mPosition);
        }
    }
}
