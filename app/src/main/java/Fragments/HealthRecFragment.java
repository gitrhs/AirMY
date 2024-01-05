package Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.airmy.HealthRecPage;
import com.example.airmy.MainActivity;
import com.example.airmy.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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

        String jsonString = healthPage.getData("current_data", "");
        if (!jsonString.isEmpty()) {
            try {

                // Convert the JSON string to a JSONObject
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONObject currentObject = jsonObject.getJSONObject("current");
                int uvNum = currentObject.getInt("uv");
                String numUV = Integer.toString(uvNum);
                String uvText, uvcolor, uvDescription, uvSafetyDescription;
                if (uvNum <=2){
                    uvText = "Low";
                    uvcolor = "#34ff34";
                    uvDescription = "Minimal danger. No protection needed. Enjoy outdoor activities with little or no risk of harm.";
                    uvSafetyDescription = "The UV level is low, indicating minimal danger. It's generally safe to enjoy outdoor activities without significant risk of harm.";
                } else if (uvNum <=5){
                    uvText = "Moderate";
                    uvcolor = "#f3d756";
                    uvSafetyDescription = "The UV level is moderate, suggesting a low risk of harm. Take basic precautions like wearing sunglasses and using sunscreen for added safety.";
                    uvDescription = "Low risk of harm from unprotected sun exposure. Take precautions such as wearing sunglasses and using sunscreen.";
                } else if (uvNum <= 7){
                    uvText = "High";
                    uvcolor = "#f81b00";
                    uvSafetyDescription = "The UV level is high, posing a considerable risk of harm. Protect yourself by limiting sun exposure during peak hours and using adequate protective measures.";
                    uvDescription = "High risk of harm. Protection against sun damage is needed. Limit sun exposure during peak hours and use protective measures.";
                } else if (uvNum <= 10){
                    uvText = "Very High";
                    uvcolor = "#cc51f2";
                    uvSafetyDescription = "The UV level is very high, indicating a significant risk of harm. Extra precautions are necessary, including avoiding outdoor activities during midday hours and using sunscreen, hats, and sunglasses.";
                    uvDescription = "Very high risk of harm. Extra precautions are necessary. Avoid being outside during midday hours, and use sunscreen, hats, and sunglasses.";
                } else {
                    uvText = "Extreme";
                    uvcolor = "#6434fb";
                    uvSafetyDescription = "The UV level is extreme, representing an extreme risk of harm. Outdoor activities during peak sunlight hours should be avoided, and stringent protective measures must be taken.";
                    uvDescription = "Extreme risk of harm. Unprotected skin and eyes can burn quickly. Avoid outdoor activities during peak sunlight hours, and take all possible precautions.";
                }
                Log.d("hasilUV", " result: " + uvNum);
                TextView uvCard = view.findViewById(R.id.uvIndexText);
                uvCard.setText(numUV);
                //change the color
                uvCard.setTextColor(Color.parseColor(uvcolor)); // Replace R.color.your_color with your color resource
                //change textUV
                TextView textUV = view.findViewById(R.id.textUV);
                textUV.setText(uvText);
                //change HowSafeText
                TextView HowSafeText = view.findViewById(R.id.HowSafeText);
                HowSafeText.setText(uvSafetyDescription);
                //hours and minutes rn
                Calendar calendar = Calendar.getInstance();
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY); // 24-hour format
                int currentMinute = calendar.get(Calendar.MINUTE);

                // Format the time as a string
                String currentTime = String.format("Now, %02d.%02d\n\n"+uvDescription, currentHour, currentMinute);

                // Find the TextView by its ID and set the text
                TextView textView4 = view.findViewById(R.id.textView7);
                textView4.setText(currentTime);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // Return the modified view
        return view;
    }
}