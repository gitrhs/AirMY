package Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.airmy.MainActivity;
import com.example.airmy.R;
import com.example.airmy.UserProfileAcitivity;
import com.example.airmy.WelcomePage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;

public class FragmentRegisteration extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentRegisteration() {
        // Required empty public constructor
    }
    public static FragmentRegisteration newInstance(String param1, String param2) {
        FragmentRegisteration fragment = new FragmentRegisteration();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registeration, container, false);
        ImageView passwordImage = view.findViewById(R.id.passwordImage);
        EditText passwordText = view.findViewById(R.id.passwordText_reg);
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

        ImageView passwordImage2 = view.findViewById(R.id.passwordImage2);
        EditText passwordText2 = view.findViewById(R.id.passwordTextAgain_reg);
        final boolean[] flag2 = {true};
        passwordImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flag2[0] == false)
                {
                    passwordImage2.setImageResource(R.drawable.password_off);
                    passwordText2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    flag2[0] = true;
                }
                else if (flag[0] == true)
                {
                    passwordImage2.setImageResource(R.drawable.password_on);
                    passwordText2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    flag2[0] = false;
                }

            }
        });
        Button register = view.findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the form data
                String username = ((EditText) view.findViewById(R.id.username_reg)).getText().toString();
                String email = ((EditText) view.findViewById(R.id.email_reg)).getText().toString();
                String password = ((EditText) view.findViewById(R.id.passwordText_reg)).getText().toString();
                WelcomePage welcomepage = (WelcomePage) getActivity();
                String token = welcomepage.getData("FCM_Token", "null");
                //call the api
                String apiUrl = "https://airmy.mbed.cc/api/account/create.php" +
                        "?name=" + username + "&username=" + username + "&email=" + email + "&password=" + password + "&fcm_token=" + token;;

                makeAPICall(apiUrl);
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
            // Parse the JSON response
            JSONObject jsonResponse = new JSONObject(response);
            boolean success = jsonResponse.getBoolean("return");

            if (success) {
                // Registration successful
                String name = jsonResponse.getString("name");
                String username = jsonResponse.getString("username");
                String email = jsonResponse.getString("email");
                String auth = jsonResponse.getString("auth");
                // save the loginAuth on localstorage
                WelcomePage welcomepage = (WelcomePage) getActivity();
                welcomepage.saveData("loginName", name);
                welcomepage.saveData("loginUsername", username);
                welcomepage.saveData("loginEmail", email);
                welcomepage.saveData("sessionID", auth);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Registration successful", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(getActivity(), "Registration failed: " + description, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
            // Handle JSON parsing error
            handleException("Error parsing JSON response");
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