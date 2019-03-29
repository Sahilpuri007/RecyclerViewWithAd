package com.android.customrecyclerview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.android.customrecyclerview.R;
import com.android.customrecyclerview.adapter.ListAdapter;
import com.android.customrecyclerview.model.Student;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String ADD_UNIT_ID ="ca-app-pub-3940256099942544/6300978111";
    private RecyclerView recyclerView;
    private ArrayList<Object> studentArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListAdapter listAdapter = new ListAdapter(studentArrayList);

        NativeExpressAdView addView = new NativeExpressAdView(MainActivity.this);

        studentArrayList.add(new Student("abc","3"));
        studentArrayList.add(getResources().getDrawable(R.drawable.ic_launcher_background));
        studentArrayList.add(addView);
        studentArrayList.add(new Student("def","6"));
        studentArrayList.add(getResources().getDrawable(R.drawable.ic_launcher_background));
        studentArrayList.add(addView);
        studentArrayList.add(new Student("ghi","9"));
        studentArrayList.add(getResources().getDrawable(R.drawable.ic_launcher_background));
        studentArrayList.add(addView);
    /*    studentArrayList.add(new Student("jkl","12"));
        studentArrayList.add(getResources().getDrawable(R.drawable.ic_launcher_background));
        studentArrayList.add(addView);
        studentArrayList.add(new Student("mno","15"));
        studentArrayList.add(getResources().getDrawable(R.drawable.ic_launcher_background));
        studentArrayList.add(addView);
        studentArrayList.add(new Student("pqr","18"));
        studentArrayList.add(getResources().getDrawable(R.drawable.ic_launcher_background));
        studentArrayList.add(addView);
        studentArrayList.add(new Student("stu","21"));
        studentArrayList.add(getResources().getDrawable(R.drawable.ic_launcher_background));
        studentArrayList.add(addView);
        studentArrayList.add(new Student("vwx","24"));
        studentArrayList.add(getResources().getDrawable(R.drawable.ic_launcher_background));
        studentArrayList.add(addView);
        studentArrayList.add(new Student("yz@","27"));*/
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);

        setupAdd();
    }

    private void setupAdd() {
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                AdSize adSize=new AdSize(1,1);
                final float scale = MainActivity.this.getResources().getDisplayMetrics().density;
                NativeExpressAdView addView = new NativeExpressAdView(MainActivity.this);
                for (int i = 2; i <= studentArrayList.size(); i += 3) {
                    addView = (NativeExpressAdView) studentArrayList.get(i);

                    View view3 = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_add, addView, false);
                    final CardView cardView = view3.findViewById(R.id.add_view);
                    Log.d("test", "run: " + cardView.toString());

                    int addWidth = cardView.getWidth() - cardView.getPaddingLeft() - cardView.getPaddingRight();
                     adSize = new AdSize((int) (addWidth / scale), 150);}
                    addView.setAdSize(adSize);
                    addView.setAdUnitId(ADD_UNIT_ID);

                loadAdd(2);
            }


        });
    }
    public void loadAdd(final int i) {

        if (i >= studentArrayList.size()) {
            return;
        }
        Object item = studentArrayList.get(i);
        if (!(item instanceof NativeExpressAdView)) {
            throw new ClassCastException("Expected item at index " + i + " to be a Native Ad");
        }

        final NativeExpressAdView adView = (NativeExpressAdView) item;

        adView.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                loadAdd(i + 3);
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                loadAdd(i + 3);
            }
        });
        adView.loadAd(new AdRequest.Builder().addTestDevice("E7D1F49BED0FB07AD75E5966613EB70D").build());
        }
    }

