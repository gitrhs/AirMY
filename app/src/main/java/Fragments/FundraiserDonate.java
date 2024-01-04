package Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.airmy.R;

public class FundraiserDonate extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FundraiserDonate() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FundraiserDonate.
     */
    // TODO: Rename and change types and number of parameters
    public static FundraiserDonate newInstance(String param1, String param2) {
        FundraiserDonate fragment = new FundraiserDonate();
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
        View view = inflater.inflate(R.layout.fragment_fundraiser_donate, container, false);

        Button d1 = view.findViewById(R.id.donateButton1);
        Button d2 = view.findViewById(R.id.donateButton2);
        Button d3 = view.findViewById(R.id.donateButton3);

        d1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //direct to web url

                String url1 = "https://www.mercy.org.my/why-donate/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url1));

                //check if device has an app to open the url
                if(intent.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivity(intent);
                } else {
                    // Log.d("FundraiserDonate", "No app to handle the URL");
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url1));
                    startActivity(browserIntent);
                }
            }
        });

        d2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //direct to web url

                String url2 = "https://www.greenpeace.org/malaysia/act/donate/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url2));

                //check if device has an app to open the url
                if(intent.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivity(intent);
                } else {
                    // Log.d("FundraiserDonate", "No app to handle the URL");
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url2));
                    startActivity(browserIntent);
                }
            }
        });

        d3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //direct to web url

                String url3 = "https://hospismalaysia.org/donations/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url3));

                //check if device has an app to open the url
                if(intent.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivity(intent);
                } else {
                    // Log.d("FundraiserDonate", "No app to handle the URL");
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url3));
                    startActivity(browserIntent);
                }
            }
        });

        return view;
    }
}