package com.shamanland.test.nestedfragmentsissue;

import android.os.Bundle;
import android.widget.TextView;

import com.shamanland.common.app.CommonActivity;

public class AuxActivity extends CommonActivity {
    private int mRequestCode;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        mRequestCode = getIntent().getIntExtra("request.code", -1);

        TextView content = new TextView(this);
        content.setText("AuxActivity received " + mRequestCode);

        setContentView(content);
    }

    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }
}
