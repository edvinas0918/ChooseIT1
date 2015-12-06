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
import info.androidhive.materialdesign.model.Meal;
import info.androidhive.materialdesign.model.Restaurant;

/**
 * Created by Edvinas.Barickis on 12/4/2015.
 */
public class MealListAdapter extends RecyclerView.Adapter<MealListAdapter.MealViewHolder> {

    List<Meal> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    private Fragment fragment;


    public MealListAdapter(Context context, List<Meal> data, Fragment f) {
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
    public MealViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.meal_row, parent, false);
        MealViewHolder holder = new MealViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MealViewHolder holder, int position) {

        Meal tempValues = data.get(position);

        holder.tv_name.setText(tempValues.getMealName());
        if(tempValues.getIngredients().length()>25)
            holder.tv_ingredients.setText(tempValues.getIngredients().substring(0,24)+"..");
        else
        holder.tv_ingredients.setText(tempValues.getIngredients());
        String priceString = String.format("%.2f €", tempValues.getPrice());
        holder.tv_price.setText(priceString);
            /*holder.image.setImageResource(
                    res.getIdentifier(
                            "com.example.kasparas.choseit:drawable/"+tempValues.getImage()
                            ,null,null));*/
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MealViewHolder extends RecyclerView.ViewHolder {

        public View thisView;
        public TextView tv_name;
        public TextView tv_ingredients;
        public TextView tv_price;

        public MealViewHolder(View itemView) {
            super(itemView);
            thisView = itemView;
            tv_name = (TextView) itemView.findViewById(R.id.tv_mealName);
            tv_ingredients = (TextView) itemView.findViewById(R.id.tv_mealIngredients);
            tv_price = (TextView) itemView.findViewById(R.id.tv_mealPrice);
        }
    }
}
