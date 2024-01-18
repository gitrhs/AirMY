package Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.airmy.MainActivity;
import com.example.airmy.R;
import com.example.airmy.UserProfileAcitivity;
import com.example.airmy.WelcomePage;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
        EditText passwordText = view.findViewById(R.id.passwordText_login);
        final boolean[] flag = {true};
        ImageView passwordImage = view.findViewById(R.id.passwordImage_login);
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


        ImageView back = view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        Button login = view.findViewById(R.id.login_loginBtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText) view.findViewById(R.id.emailText_login)).getText().toString();
                String password = ((EditText) view.findViewById(R.id.passwordText_login)).getText().toString();
                WelcomePage welcomepage = (WelcomePage) getActivity();
                String token = welcomepage.getData("FCM_Token", "null");
                String apiUrl = "https://airmy.mbed.cc/api/account/login.php" +
                        "?email=" + email + "&password=" + password + "&fcm_token=" + token;
                makeAPICall(apiUrl);
            }
        });
        return view;

    }
    private void makeAPICall(String apiUrl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(apiUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder response = new StringBuilder();

                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }

                        reader.close();

                        // Handle the success response
                        handleSuccessResponse(response.toString());
                    } else {
                        // Handle the error case
                        handleError(responseCode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // Handle the exception
                    handleException(e.getMessage());
                }
            }
        }).start();
    }
    private void handleSuccessResponse(String response) {
        try {
            // Parse the    JSON response
            JSONObject jsonResponse = new JSONObject(response);
            boolean success = jsonResponse.getBoolean("return");

            if (success) {
                // Registration successful
                String name = jsonResponse.getString("name");
                String username = jsonResponse.getString("username");
                String email = jsonResponse.getString("email");
                String auth = jsonResponse.getString("auth");

                // save the string
                WelcomePage welcomepage = (WelcomePage) getActivity();
                welcomepage.saveData("loginName", name);
                welcomepage.saveData("loginUsername", username);
                welcomepage.saveData("loginEmail", email);
                welcomepage.saveData("sessionID", auth);
                Log.d("sessionidnya", auth);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();

                        // Delay the navigation to another page for 2 seconds
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Go back to welcomePage so the user can login
                                Intent intent = new Intent(getActivity(), UserProfileAcitivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // remove animation
                                startActivity(intent);
                            }
                        }, 2000);
                    }
                });
            } else {
                // Registration failed
                final String description = jsonResponse.getString("Description");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Login failed: " + description, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
            // Handle JSON parsing error
            handleException("Error parsing JSON responses");
        }
    }

    private void handleError(int responseCode) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "Error: " + responseCode, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleException(String errorMessage) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
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