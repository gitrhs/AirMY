package Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.airmy.MainActivity;
import com.example.airmy.NewsPage;
import com.example.airmy.R;
import com.google.android.material.card.MaterialCardView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.TextView;

import com.example.airmy.CustomPopupDialog;
import org.json.JSONException;
import org.json.JSONObject;

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
        NewsPage newsPage = (NewsPage) getActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_page1, container, false);
        //call save data to get the data first:
        String[] c1 = newsPage.parseTheJson(newsPage.getData("LN0", ""));
        String[] c2 = newsPage.parseTheJson(newsPage.getData("LN1", ""));
        String[] c3 = newsPage.parseTheJson(newsPage.getData("LN2", ""));
        String[] c4 = newsPage.parseTheJson(newsPage.getData("LN3", ""));
        String[] c5 = newsPage.parseTheJson(newsPage.getData("LN4", ""));

        //change the title
        TextView tv_news1_title = view.findViewById(R.id.tv_news1_title);
        tv_news1_title.setText(c1[1]);
        TextView tv_news2_title = view.findViewById(R.id.tv_news2_title);
        tv_news2_title.setText(c2[1]);
        TextView tv_news3_title = view.findViewById(R.id.tv_news3_title);
        tv_news3_title.setText(c3[1]);
        TextView tv_news4_title = view.findViewById(R.id.tv_news4_title);
        tv_news4_title.setText(c4[1]);
        TextView tv_news5_title = view.findViewById(R.id.tv_news5_title);
        tv_news5_title.setText(c5[1]);
        //change the publish tv_news1_publish
        TextView tv_news1_publish = view.findViewById(R.id.tv_news1_publish);
        tv_news1_publish.setText("Published at "+ c1[4]+ ", " + c1[3]+"\nSource: "+c1[2]);
        TextView tv_news2_publish = view.findViewById(R.id.tv_news2_publish);
        tv_news2_publish.setText("Published at "+ c2[4]+ ", " + c2[3]+"\nSource: "+c2[2]);
        TextView tv_news3_publish = view.findViewById(R.id.tv_news3_publish);
        tv_news3_publish.setText("Published at "+ c3[4]+ ", " + c3[3]+"\nSource: "+c3[2]);
        TextView tv_news4_publish = view.findViewById(R.id.tv_news4_publish);
        tv_news4_publish.setText("Published at "+ c4[4]+ ", " + c4[3]+"\nSource: "+c4[2]);
        TextView tv_news5_publish = view.findViewById(R.id.tv_news5_publish);
        tv_news5_publish.setText("Published at "+ c5[4]+ ", " + c5[3]+"\nSource: "+c5[2]);

        // Find the MaterialCardViews by ID
        MaterialCardView cardView1 = view.findViewById(R.id.view_news1);
        MaterialCardView cardView2 = view.findViewById(R.id.view_news2);
        MaterialCardView cardView3 = view.findViewById(R.id.view_news3);
        MaterialCardView cardView4 = view.findViewById(R.id.view_news4);
        MaterialCardView cardView5 = view.findViewById(R.id.view_news5);

        //change the d
        // Set click listeners for the MaterialCardViews
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the modal popup with content for news 1
                showPopup("LNC0", c1);
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the modal popup with content for news 2
                showPopup("LNC1", c2);
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the modal popup with content for news 3
                showPopup("LNC2", c3);
            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the modal popup with content for news 4
                showPopup("LNC3", c4);
            }
        });
        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the modal popup with content for news 5
                showPopup("LNC4", c5);
            }
        });

        return view;
    }

    private void showPopup(String contentName, String[] data) {
        CustomPopupDialog customPopupDialog = new CustomPopupDialog(requireContext());
        NewsPage newsPage = (NewsPage) getActivity();
        customPopupDialog.setTitle(data[1]); // Set the dynamic content
        customPopupDialog.setDesc("Published in "+ data[4]+ ", " + data[3]+"\nSource: "+data[2]);
        customPopupDialog.setContent(newsPage.getData(contentName, ""));
        customPopupDialog.show();
    }
}
