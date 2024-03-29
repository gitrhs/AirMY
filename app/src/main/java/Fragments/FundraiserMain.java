package Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.airmy.FundraiserPage;
import com.example.airmy.MainActivity;
import com.example.airmy.R;
import Fragments.FundraiserDonate;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FundraiserMain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FundraiserMain extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FundraiserMain() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FundraiserMain.
     */
    // TODO: Rename and change types and number of parameters
    public static FundraiserMain newInstance(String param1, String param2) {
        FundraiserMain fragment = new FundraiserMain();
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
        View view = inflater.inflate(R.layout.fragment_fundraiser_main, container, false);

        Button donateNowButton = view.findViewById(R.id.DonateNowButton);
        donateNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //switch to FundraiserDonate fragment
                ((FundraiserPage) requireActivity()).replaceFragment(new FundraiserDonate());
            }
        });

        return view;
    }
}