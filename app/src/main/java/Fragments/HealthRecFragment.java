package Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.airmy.HealthRecPage;
import com.example.airmy.MainActivity;
import com.example.airmy.R;

import java.util.Calendar;

public class HealthRecFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HealthRecFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HealthRecFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragments.HealthRecFragment newInstance(String param1, String param2) {
        Fragments.HealthRecFragment fragment = new Fragments.HealthRecFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health_rec, container, false);
        HealthRecPage healthPage = (HealthRecPage) getActivity();
        //count Air Quality Index "airqualityNumberToday"
        String AQI = healthPage.getData("AQI", "-");
        Log.d("aqidataonhealthrec", "is : " + AQI);
        TextView airqualityNumberToday = view.findViewById(R.id.airqualityNumberTodays);
        airqualityNumberToday.setText(AQI);
        String AQIDesc;
        String AQIText;
        String maskStatus = "Mask recommended";
        int AQINum;

        // Check if AQI is a valid integer
        if (!AQI.isEmpty() && AQI.matches("\\d+")) {
            AQINum = Integer.parseInt(AQI);
            if (AQINum >= 0 && AQINum <= 50) {
                AQIText = "Good";
                AQIDesc = "Air quality is good, and air pollution poses little or no risk.";
            } else if (AQINum <= 100) {
                AQIText = "Moderate";
                AQIDesc = "Okay, but sensitive individuals may experience minor health effects.";
            } else if (AQINum <= 150) {
                AQIText = "Unhealthy for Sensitive Groups";
                AQIDesc = "Members of sensitive groups may experience health effects. The general public is less likely to be affected.";
            } else if (AQINum <= 200) {
                AQIText = "Unhealthy";
                maskStatus = "Mask required";
                AQIDesc = "Everyone may begin to experience health effects, and members of sensitive groups may experience more serious health effects.";
            } else if (AQINum <= 300) {
                AQIText = "Very Unhealthy";
                maskStatus = "Mask required";
                AQIDesc = "Health alert: everyone may experience more serious health effects.";
            } else if (AQINum <= 500) {
                AQIText = "Hazardous";
                maskStatus = "Mask required";
                AQIDesc = "Health warnings of emergency conditions; the entire population is more likely to be affected.";
            } else {
                AQIText = "Invalid AQI Value";
                maskStatus = "-";
                AQIDesc = "Unknown AQI category";
            }
        } else {
            AQIText = "Invalid AQI Value";
            maskStatus = "-";
            AQIDesc = "Unknown AQI category";
        }

        //show the text on "airqualityTextRateToday"
        TextView airqualityTextRateToday = view.findViewById(R.id.airqualityTextRateToday);
        airqualityTextRateToday.setText(AQIText);
        //show the desc on "healthEffictsToday"
        TextView healthEffictsToday = view.findViewById(R.id.healthEffictsToday);
        healthEffictsToday.setText(AQIDesc);
        //show the maskStatus on maskRequiry
        TextView maskRequiry = view.findViewById(R.id.maskRequiry);
        maskRequiry.setText(maskStatus);

        //hours and minutes rn
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY); // 24-hour format
        int currentMinute = calendar.get(Calendar.MINUTE);

        // Format the time as a string
        String currentTime = String.format("Now, %02d.%02d", currentHour, currentMinute);

        // Find the TextView by its ID and set the text
        TextView textView4 = view.findViewById(R.id.textView7);
        textView4.setText(currentTime);
        // Return the modified view
        return view;
    }
}