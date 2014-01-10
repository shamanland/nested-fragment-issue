package com.shamanland.test.nestedfragmentsissue;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TextViewFragment extends Fragment {
    private TextView mTextView;
    private int mBackgroundColor;
    private int mTextColor;
    private int mRequestCode;

    private String getText() {
        return "TextViewFragment: mRequestCode: " + mRequestCode + "\n";
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        mBackgroundColor = getArguments().getInt("background.color", Color.GRAY);
        mTextColor = getArguments().getInt("text.color", Color.BLACK);
        mRequestCode = getArguments().getInt("request.code", 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mTextView = new TextView(inflater.getContext());
        mTextView.setBackgroundColor(mBackgroundColor);
        mTextView.setTextColor(mTextColor);
        mTextView.setText(getText());
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClicked();
            }
        });
        return mTextView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mTextView.setText(getText() + "onActivityResult: " + requestCode);
    }

    protected void onClicked() {
        Intent intent = new Intent(getActivity(), AuxActivity.class);
        intent.putExtra("request.code", mRequestCode);
        startActivityForResult(intent, mRequestCode);
    }
}
