package Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.airmy.R;

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
        Button savechanges = view.findViewById(R.id.savechanges);
        savechanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText) view.findViewById(R.id.editName)).getText().toString();
                String username = ((EditText) view.findViewById(R.id.editUserName)).getText().toString();
                String email = ((EditText) view.findViewById(R.id.editUserEmail)).getText().toString();
                String password = ((EditText) view.findViewById(R.id.editUserPass)).getText().toString();

                //String auth =
                //String apiUrl = "https://airmy.mbed.cc/api/account/update.php" + "?name=" + username + "&username=" + username + "&email=" + email + "&password=" + password + "&auth=" + auth;

                //makeAPICall(apiUrl);
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