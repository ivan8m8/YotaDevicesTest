package ru.courierhelper.yotadevicestest;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ivan on 15.02.2018.
 */

public class LaunchesRecyclerAdapter extends RecyclerView.Adapter<LaunchesRecyclerAdapter.ViewHolder> {

    private ArrayList<Launch> launches;
    private Context context;

    public LaunchesRecyclerAdapter(ArrayList<Launch> launches, Context context) {
        this.launches = launches;
        this.context = context;
    }

    @Override
    public LaunchesRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LaunchesRecyclerAdapter.ViewHolder holder, final int position) {

        holder.rocketNameTextView.setText(launches.get(position).getRocketName());
        holder.detailsTextView.setText(launches.get(position).getDetails());

        Date formatDate = new Date(TimeUnit.SECONDS.toMillis(launches
                .get(position)
                .getLaunchDate()
                + 10800));

        holder.launchDateTextView
                .setText(new SimpleDateFormat("dd.MM.yyyy",
                        new Locale("ru")).format(formatDate));


        holder.launchTimeTextView
                .setText(new SimpleDateFormat("'в' HH:mm:ss '(МСК)'",
                        new Locale("ru")).format(formatDate));

        //new DownloadImage(holder.missionPatchImageView).execute(launches.get(position).getMissionPatchUrl());
        //holder.missionPatchImageView.setImageResource(R.drawable.ic_launcher_background);

        holder.missionPatchImageView.setImageBitmap(launches.get(position).getBitmap());

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(view.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.launch_onclick);
                ImageView articleImageView = dialog.findViewById(R.id.articleImageView);
                articleImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Uri uri = Uri.parse(launches.get(position).getArticleLink());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
                        if (Build.VERSION.SDK_INT >= 21) {
                            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
                        } else {
                            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
                        }
                        try {
                            intent.addFlags(flags);
                        } catch (Exception e) {
                            e.printStackTrace();
                            context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
                        }
                        context.startActivity(intent);
                    }
                });
                ImageView videoImageView = dialog.findViewById(R.id.videoImageView);
                videoImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String videoID = launches
                                .get(position)
                                .getVideoLink().replace("https://www.youtube.com/watch?v=", "");
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("vnd.youtube:"
                                        + videoID));
                        intent.putExtra("VIDEO_ID", videoID);
                        try {
                            context.startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(launches.get(position).getVideoLink())));
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (launches != null ? launches.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView rocketNameTextView;
        private TextView launchDateTextView;
        private TextView launchTimeTextView;
        private ImageView missionPatchImageView;
        private TextView detailsTextView;

        private View container;

        public ViewHolder(View itemView) {
            super(itemView);
            rocketNameTextView = itemView.findViewById(R.id.rocketNameTextView);
            launchDateTextView = itemView.findViewById(R.id.launchDateTextView);
            launchTimeTextView = itemView.findViewById(R.id.launchTimeTextView);
            missionPatchImageView = itemView.findViewById(R.id.missionPatchImageView);
            detailsTextView = itemView.findViewById(R.id.detailsTextView);

            container = itemView.findViewById(R.id.card_view);
        }
    }
}
