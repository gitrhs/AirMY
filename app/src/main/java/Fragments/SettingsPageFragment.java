package Fragments;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.example.airmy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

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


        String[] switchName = {"soundAlertClicked?","sendNotifClicked?", "weatherClicked?", "newsClicked?","healthClicked?","fundClicked??" };

        SharedPreferences[] prefs = {getActivity().getSharedPreferences(switchName[0], Context.MODE_PRIVATE),
                getActivity().getSharedPreferences(switchName[1], Context.MODE_PRIVATE),
                getActivity().getSharedPreferences(switchName[2], Context.MODE_PRIVATE),
                getActivity().getSharedPreferences(switchName[3], Context.MODE_PRIVATE),
                getActivity().getSharedPreferences(switchName[4], Context.MODE_PRIVATE),
                getActivity().getSharedPreferences(switchName[5], Context.MODE_PRIVATE)
        };


        String[] keys = {"alertKey","sendKey", "weatherSwitch","newsSwitch","healthRecSwitch","fundraiserSwitch"};

        boolean  value1 = prefs[0].getBoolean(keys[0] , false);
        boolean  value2 = prefs[1].getBoolean(keys[1] , false);
        boolean  value3 = prefs[2].getBoolean(keys[2] , false);
        boolean  value4 = prefs[3].getBoolean(keys[3] , false);
        boolean  value5 = prefs[4].getBoolean(keys[4] , false);
        boolean  value6 = prefs[5].getBoolean(keys[5] , false);

        tb1.setChecked(value1);
        tb2.setChecked(value2);
        tb3.setChecked(value3);
        tb4.setChecked(value4);
        tb5.setChecked(value5);
        tb6.setChecked(value6);


        // still figuring it out if someone knows please do lmao :3
        tb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    SharedPreferences prefs = getActivity().getSharedPreferences(switchName[0], Context.MODE_PRIVATE);
                    prefs.edit().putBoolean(keys[0], true).commit();
                    onResume();
                }
                else
                {
                    SharedPreferences prefs = getActivity().getSharedPreferences(switchName[0], Context.MODE_PRIVATE);
                    prefs.edit().putBoolean(keys[0], false).commit();
                    onPause();
                }
            }
        });

        // the flag checks if the send notification is on or off - if on the rest of the functions work, if off they don't
        boolean[] flag = {false};
        tb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    SharedPreferences prefs = getActivity().getSharedPreferences(switchName[1], Context.MODE_PRIVATE);
                    prefs.edit().putBoolean(keys[1], true).commit();
                    onResume();
                    flag[0] = true;
                }
                else
                {
                    SharedPreferences prefs = getActivity().getSharedPreferences(switchName[1], Context.MODE_PRIVATE);
                    prefs.edit().putBoolean(keys[1], false).commit();
                    onPause();
                    unsubscribe("weather",keys[2],switchName[2],"Subscribed to Weather Notifications");
                    unsubscribe("news",keys[3],switchName[3],"Subscribed to News Notifications");
                    unsubscribe("healthRec",keys[4],switchName[4],"Subscribed to HealthRec Notifications");
                    unsubscribe("fundraiser",keys[5],switchName[5],"Subscribed to Fundraiser Notifications");
                    flag[0] = false;
                }
            }
        });


        // Subscribtion to Topics
        tb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && flag[0] == true)
                {
                    subscribe("weather",keys[2],switchName[2],"Subscribed to Weather Notifications");
                }
                else
                {
                    unsubscribe("weather",keys[2],switchName[2],"Subscribed to Weather Notifications");
                }
            }
        });
        tb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && flag[0] == true)
                {
                    subscribe("news",keys[3],switchName[3],"Subscribed to News Notifications");
                }
                else
                {
                    unsubscribe("news",keys[3],switchName[3],"Subscribed to News Notifications");
                }
            }
        });
        tb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && flag[0] == true)
                {
                    subscribe("healthRec",keys[4],switchName[4],"Subscribed to HealthRec Notifications");
                }
                else
                {
                    unsubscribe("healthRec",keys[4],switchName[4],"Subscribed to HealthRec Notifications");

                }
            }
        });
        tb6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && flag[0] == true)
                {
                    subscribe("fundraiser",keys[5],switchName[5],"Subscribed to Fundraiser Notifications");
                }
                else
                {
                    unsubscribe("fundraiser",keys[5],switchName[5],"Subscribed to Fundraiser Notifications");

                }
            }
        });

        return view;

    }

    public void subscribe(String topic, String switchKey,String switchName, String message)
    {
        SharedPreferences prefs = getActivity().getSharedPreferences(switchName, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(switchKey, true).commit();
        onResume();
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = message;
                        if (!task.isSuccessful()) {
                            msg = "failed to "+ message ;
                        }
                        Log.d(TAG, msg);
                        Toast.makeText(getActivity(),msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void unsubscribe(String topic, String switchKey,String switchName, String message)
    {
        SharedPreferences prefs = getActivity().getSharedPreferences(switchName, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(switchKey, false).commit();
        onPause();

        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg =  "Un"+message;
                        if (!task.isSuccessful()) {
                            msg = message + " failed";
                        }
                        Log.d(TAG, msg);
                        Toast.makeText(getActivity(),msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }


}