package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import info.androidhive.materialdesign.R;

/**
 * Created by Edvinas.Barickis on 12/3/2015.
 */
public class SettingsFragment extends Fragment{
    private static SeekBar seek_bar;
    private static TextView text_view;
    private static Spinner spinnerFrom;
    private static Spinner spinnerTo;
    private MainActivity activity;


    public SettingsFragment() {
        // Required empty public constructor
    }

    public void initPriceSpinners (View view){
        double previousPriceFrom = activity.GetSpinnerPriceFrom();
        double previoustPriceTo = activity.GetSpinnerPriceTo();
        Double[] spinnerArr = new Double[20];
        int iter = 0;
        for (double x = 0;x<=10;x+=0.5) {
            if (iter>=20) break;
            spinnerArr[iter] = x;
            iter++;

        }

        ArrayAdapter<Double> spinnerAdapter = new ArrayAdapter<>(this.getActivity(),android.R.layout.simple_spinner_item,spinnerArr);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom = (Spinner) view.findViewById(R.id.sp_pr_from);
        spinnerTo = (Spinner) view.findViewById(R.id.sp_pr_to);
        spinnerFrom.setAdapter(spinnerAdapter);
        spinnerTo.setAdapter(spinnerAdapter);
        spinnerFrom.setSelection(spinnerAdapter.getPosition(previousPriceFrom));
        spinnerTo.setSelection(spinnerAdapter.getPosition(previoustPriceTo));

    }

    public void initSeekbar(View view) {
        int previous_progress = activity.GetSeekBarBrogress();

        seek_bar = (SeekBar) view.findViewById(R.id.sb_dist);
        seek_bar.setMax(30);
        seek_bar.setDrawingCacheBackgroundColor(Color.CYAN);
        seek_bar.setProgress(previous_progress);
        text_view = (TextView) view.findViewById(R.id.tv_prog);
        text_view.setText(seek_bar.getProgress() + " km");
        final int progress_value;
        seek_bar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progress_value;

                    @Override

                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress_value = progress;
                        text_view.setText(progress + " km");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                        text_view.setText(progress_value + " km");
                    }
                }
        );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        initSeekbar(view);
        initPriceSpinners(view);

        //add save button
        Button selectOptionsSave = (Button) view.findViewById(R.id.btn_select_options_next);
        selectOptionsSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Double valueFrom = Double.valueOf(spinnerFrom.getSelectedItem().toString());
                    Double valueTo = Double.valueOf(spinnerTo.getSelectedItem().toString());
                    int distance = seek_bar.getProgress();
                    if (valueFrom > valueTo)
                        throw new Exception("Incorrect choice");

                    activity.saveOptionsValues(seek_bar.getProgress(), Double.valueOf
                            (spinnerFrom.getSelectedItem().toString()), Double.valueOf(spinnerTo.getSelectedItem().toString()));
                    RestaurantListFragment mf = new RestaurantListFragment();
                    mf.restaurants = activity.getRestaurantList(distance);
                    activity.changeFragment(R.id.container_body, mf);

                    activity.getSupportActionBar().setTitle("Restaurants");
                } catch (NullPointerException ex) {
                    Context context = getActivity();
                    CharSequence text = "Please enable GPS";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } catch (Exception ex) {
                    Context context = getActivity();
                    CharSequence text = ex.getMessage();
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }
        });

        activity.getSupportActionBar().setTitle("Settings");
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
