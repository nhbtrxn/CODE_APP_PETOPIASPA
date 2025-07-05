package com.nhom9.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nhom9.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RightsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RightsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RightsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RightsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RightsFragment newInstance(String param1, String param2) {
        RightsFragment fragment = new RightsFragment();
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
        View view = inflater.inflate(R.layout.fragment_rights, container, false);

        TextView tv = view.findViewById(R.id.AccountInfo);
        String fullText = "+ Xem, cập nhật hoặc xóa thông tin cá nhân trong mục Cài đặt tài khoản.";
        SpannableString spannable = new SpannableString(fullText);

        int start = fullText.indexOf("Cài đặt tài khoản");
        int end = start + "Cài đặt tài khoản".length();

        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#FF5722")), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(spannable);

        return view;
    }

}