package io.supercharge.shimmeringlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.supercharge.shimmerlayout.ShimmerLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShimmerLayout shimmerLayout = (ShimmerLayout) findViewById(R.id.shimmer_layout);
        shimmerLayout.startShimmerAnimation();
    }
}
