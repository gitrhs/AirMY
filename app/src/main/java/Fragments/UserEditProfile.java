package Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.airmy.MainActivity;
import com.example.airmy.R;
import com.example.airmy.UserProfileAcitivity;
import com.example.airmy.UserProfilePage;
import com.example.airmy.WelcomePage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.util.Log;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserEditProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserEditProfile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserEditProfile() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserEditProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static UserEditProfile newInstance(String param1, String param2) {
        UserEditProfile fragment = new UserEditProfile();
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
        View view = inflater.inflate(R.layout.fragment_user_edit_profile, container, false);
        UserProfileAcitivity welcomepage = (UserProfileAcitivity) getActivity();
        EditText editUserEmail = view.findViewById(R.id.editUserEmail);
        editUserEmail.setText(welcomepage.getData("loginEmail", ""));
        EditText editUserName = view.findViewById(R.id.editUserName);
        editUserName.setText(welcomepage.getData("loginUsername", ""));
        EditText editName = view.findViewById(R.id.editName);
        editName.setText(welcomepage.getData("loginName", ""));
        Button savechanges = view.findViewById(R.id.savechanges);
        savechanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText) view.findViewById(R.id.editName)).getText().toString();
                String username = ((EditText) view.findViewById(R.id.editUserName)).getText().toString();
                String email = ((EditText) view.findViewById(R.id.editUserEmail)).getText().toString();
                String password = ((EditText) view.findViewById(R.id.editUserPass)).getText().toString();
                UserProfileAcitivity userprofile = (UserProfileAcitivity) getActivity();
                String sessionID = userprofile.getData("sessionID", "");
                String apiUrl = "https://airmy.mbed.cc/api/account/update.php" + "?name=" + name + "&username=" + username + "&email=" + email + "&password=" + password + "&auth=" + sessionID;
                Log.d("apiurlnya", apiUrl);
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
                        Log.d("hasilrespondapinya", response.toString());
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
                UserProfileAcitivity welcomepage = (UserProfileAcitivity) getActivity();
                welcomepage.saveData("loginName", name);
                welcomepage.saveData("loginUsername", username);
                welcomepage.saveData("loginEmail", email);
                welcomepage.saveData("sessionID", auth);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "updated successful", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(getActivity(), "update failed: " + description, Toast.LENGTH_SHORT).show();
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