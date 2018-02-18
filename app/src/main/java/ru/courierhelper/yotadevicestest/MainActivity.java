package ru.courierhelper.yotadevicestest;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String API_URL = "https://api.spacexdata.com/v2/launches?launch_year=2017";

    private ArrayList<Launch> launches;
    private RecyclerView launchesRecyclerView;
    private LaunchesRecyclerAdapter launchesRecyclerAdapter;

    //TODO: DO NOT LOAD BIG IMAGES IN FULL RESOLUTION
    //TODO: DO NOT DISPLAY TINY IMAGES

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        launchesRecyclerView = findViewById(R.id.launchesRecyclerView);
        launchesRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        launches = new ArrayList<>();

        loadLaunchesToRecyclerView();
    }

    private void loadLaunchesToRecyclerView(){
        if (Tools.isOnline(this)) {
            try {
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage(getResources().getString(R.string.loading));
                progressDialog.setCancelable(false);
                progressDialog.show();
                try {
                    new GetAllTheLaunches(new GetAllTheLaunches.LaunchesAreReadyListener() {
                        @Override
                        public void launchesAreReady(ArrayList<Launch> result) {

                            progressDialog.dismiss();

                            if (result.size() > 0 ) {
                                launches = result;
                                launchesRecyclerAdapter = new LaunchesRecyclerAdapter(launches, MainActivity.this);
                                launchesRecyclerView.setAdapter(launchesRecyclerAdapter);
                                loadFirstImageInTheQueueAndContinue(0);
                            } else {
                                Tools.showErrorSnackbar(findViewById(R.id.launchesRecyclerView));
                            }

                        }
                    }).execute(API_URL);
                } catch (Exception e){
                    e.printStackTrace();
                    Tools.showErrorSnackbar(findViewById(R.id.launchesRecyclerView));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Tools.showInternetRequiredSnackbar(findViewById(R.id.launchesRecyclerView));
        }

    }

    private void loadFirstImageInTheQueueAndContinue(final int i){
        if (launches.size() > 0 && i <= launches.size() - 1) {
            new DownloadImage(
                    new DownloadImage.OnImageLoadedListener() {
                        @Override
                        public void onImageLoadedListener(Bitmap bitmap) {
                            launches.get(i).setBitmap(bitmap);
                            int j = i + 1;
                            loadFirstImageInTheQueueAndContinue(j);
                            launchesRecyclerAdapter.notifyDataSetChanged();
                        }
                    })
                    .execute(launches.get(i).getMissionPatchUrl());
        }
    }
}
