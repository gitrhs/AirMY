package Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.airmy.R;
import com.google.android.material.card.MaterialCardView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.example.airmy.CustomPopupDialog;
public class NewsPage1Fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public NewsPage1Fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NewsPage1Fragment newInstance(String param1, String param2) {
        NewsPage1Fragment fragment = new NewsPage1Fragment();
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
        View view = inflater.inflate(R.layout.fragment_news_page1, container, false);

        // Find the MaterialCardViews by ID
        MaterialCardView cardView1 = view.findViewById(R.id.view_news1);
        MaterialCardView cardView2 = view.findViewById(R.id.view_news2);

        // Set click listeners for the MaterialCardViews
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the modal popup with content for news 1
                showPopup("A Suistainable Alternative to Air Conditioning");
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the modal popup with content for news 2
                showPopup("This is news 2");
            }
        });

        return view;
    }

    private void showPopup(String title) {
        CustomPopupDialog customPopupDialog = new CustomPopupDialog(requireContext());
        customPopupDialog.setTitle(title); // Set the dynamic content
        customPopupDialog.show();
    }
}
