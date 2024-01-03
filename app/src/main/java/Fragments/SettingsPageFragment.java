package Fragments;

import android.os.Bundle;
import com.example.airmy.R;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsPageFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Switch tb1, tb2, tb3, tb4, tb5, tb6;


    public SettingsPageFragment() {
        // Required empty public constructor
    }

    public static SettingsPageFragment newInstance(String param1, String param2) {
        SettingsPageFragment fragment = new SettingsPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings_page, container, false);

        tb1 = view.findViewById(R.id.switch1);
        tb2 = view.findViewById(R.id.switch2);
        tb3 = view.findViewById(R.id.switch3);
        tb4 = view.findViewById(R.id.switch4);
        tb5 = view.findViewById(R.id.switch5);
        tb6 = view.findViewById(R.id.switch6);

        //set listener for the ToggleButton state changes
        tb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //handle the state change
                if(isChecked){
                    //toggle button is ON
                } else {
                    //toggle button is OFF
                }
            }
        });

        return view;
    }


}