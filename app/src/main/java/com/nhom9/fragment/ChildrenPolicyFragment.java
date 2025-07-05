package com.nhom9.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nhom9.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChildrenPolicyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChildrenPolicyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChildrenPolicyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChildrenPolicyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChildrenPolicyFragment newInstance(String param1, String param2) {
        ChildrenPolicyFragment fragment = new ChildrenPolicyFragment();
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
        // Inflate layout cho fragment
        View view = inflater.inflate(R.layout.fragment_children_policy, container, false);

        // Gán TextView và xử lý in đậm
        TextView tvAgeLimit = view.findViewById(R.id.AgeLimit);
        String fullText = "Ứng dụng không dành cho người dùng dưới 13 tuổi. Nếu phát hiện thông tin từ trẻ em, chúng tôi sẽ xóa ngay khỏi hệ thống.";
        String boldText = "không dành cho người dùng dưới 13 tuổi";

        SpannableString spannable = new SpannableString(fullText);

        int start = fullText.indexOf(boldText);
        int end = start + boldText.length();

        spannable.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvAgeLimit.setText(spannable);

        return view;
    }}
