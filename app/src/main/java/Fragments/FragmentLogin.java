package Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.airmy.MainActivity;
import com.example.airmy.R;
import com.example.airmy.WelcomePage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLogin extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentLogin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentLogin.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentLogin newInstance(String param1, String param2) {
        FragmentLogin fragment = new FragmentLogin();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ImageView passwordImage = view.findViewById(R.id.passwordImage_login);
        EditText passwordText = view.findViewById(R.id.passwordText_login);
        final boolean[] flag = {true};
        passwordImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(flag[0] == false)
                    {
                        passwordImage.setImageResource(R.drawable.password_off);
                        passwordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        flag[0] = true;
                    }
                    else if (flag[0] == true)
                    {
                        passwordImage.setImageResource(R.drawable.password_on);
                        passwordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        flag[0] = false;
                    }

            }
        });


        RelativeLayout frame = view.findViewById(R.id.loginFragmentRelativeLayout);
        Button login = view.findViewById(R.id.login_loginBtn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // needs configuration with the database

                // go to Homepage if the login is successful
                Intent intent = new Intent(getActivity(), new MainActivity().getClass());
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // remove animation
                startActivity(intent);
            }
        });



        TextView registerNow = view.findViewById(R.id.registerNow);
        registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (savedInstanceState == null) {
                    getChildFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.loginFragContainer, FragmentRegisteration.class, null).addToBackStack(null)
                            .commit();
                }
            }
        });

        TextView forgotPass = view.findViewById(R.id.forgotPass);
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (savedInstanceState == null) {
                    getChildFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.loginFragContainer, ForgotPassword.class, null).addToBackStack(null)
                            .commit();
                }
            }
        });

        ImageView back = view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        return view;

    }
}

// Testing sharedPref

//    SharedPreferences sharePref  = getSharedPreferences(getString(R.string.AirMyPREF), MODE_PRIVATE);
//    SharedPreferences.Editor editor = sharePref.edit();
//        editor.putString("User", "5678");
//                editor.apply();
//                String user = sharePref.getString(getString(R.string.KEY_USER), null);
//                TextView text = findViewById(R.id.textView6);
//                text.setText(user);