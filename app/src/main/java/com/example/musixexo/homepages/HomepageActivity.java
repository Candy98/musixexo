package com.example.musixexo.homepages;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musixexo.PlayerActivity;
import com.example.musixexo.R;
import com.example.musixexo.adapters.CustomAdapterEpisodeLists;
import com.example.musixexo.custom.DottedProgress;
import com.example.musixexo.models.ModelClass;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class HomepageActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    List episodes;
    List url;
    ImageView imgTitle;
    String Animename;
    TextView titleVid;
    DottedProgress dottedProgress;
    PrettyDialog prettyDialog;
    JSONArray jsonArray;
    RecyclerView rcvEpList;

    JSONObject jsonObject;
    List<JSONObject> jsonObjects = new ArrayList<>();
    List<String> urls = new ArrayList<>();
    List<String> epList = new ArrayList<>();
    List<String> durationList = new ArrayList<>();
    List<String> imgTitleEpList = new ArrayList<>();
    ArrayList<ModelClass> activityList = new ArrayList<>();
    CustomAdapterEpisodeLists customAdapterEpisodeLists;
    ModelClass modelClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        BindViews();

        dottedProgress = new DottedProgress(HomepageActivity.this);
        Animename = getIntent().getStringExtra("AnimeName");
        // ParseObject parseObject = new ParseObject("Episodes");
        // parseObject.put("test", "Passed");
        // parseObject.put("name", "Slam Dunk");
        // parseObject.put("titleimg","");
        // parseObject.put("thumb","");
        // parseObject.saveInBackground(new SaveCallback() {
        //     @Override
        //     public void done(ParseException e) {
        //         if (e != null) {
        //             Log.i("Parse", e.getMessage());
        //         }
        //     }
        // });

        dottedProgress.show();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Episodes");
        query.whereEqualTo("name", Animename);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {


                    for (ParseObject obj : objects) {
                        episodes = obj.getList("epno");
                        try {
                            String response = obj.get("urljson") + "";
                            JSONArray jsonArray = new JSONArray(response);


                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObjects.add(jsonArray.getJSONObject(i));
                            }
                            for (JSONObject obj1 : jsonObjects) {
                                urls.add(obj1.get("url").toString());
                                modelClass = new ModelClass();
                                modelClass.setActivityName(obj1.get("title") + "");
                                modelClass.setVidDuration(obj1.get("duration") + "");
                                modelClass.setEpImgUrl(Uri.parse(obj1.get("poster") + ""));
                                activityList.add(modelClass);


                            }
                            rcvEpList.setAdapter(customAdapterEpisodeLists);

                        } catch (JSONException ex) {
                        }


                        titleVid.setText(obj.get("name") + "");
                        IsCompleteChecker(obj);


                        LoadTitleGlideImg(obj.get("titleimg") + "");
                    }
                    dottedProgress.dismiss();


                }


            }

        });
        customAdapterEpisodeLists.setOnItemClickListener(new CustomAdapterEpisodeLists.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ArrayList<ModelClass> menulist) {
                Intent intent;
                intent = new Intent(HomepageActivity.this, PlayerActivity.class);
                intent.putExtra("Url", urls.get(position));
                startActivity(intent);
            }
        });

    }


    private void LoadTitleGlideImg(String url) {
        Glide.with(HomepageActivity.this).load(url).centerCrop().into(imgTitle);
    }

    private void BindViews() {
        imgTitle = findViewById(R.id.imgTitle);
        titleVid = findViewById(R.id.titleVid);
        customAdapterEpisodeLists = new CustomAdapterEpisodeLists(this, activityList);
        rcvEpList = findViewById(R.id.rcvEpList);

    }

    private void IsCompleteChecker(final ParseObject obj) {
        if (Boolean.valueOf(obj.get("iscomplete").toString()) == false) {
            prettyDialog = new PrettyDialog(this);
            prettyDialog
                    .setMessage("Sorry,episodes are not ready yet please try again later")
                    .setIcon(R.drawable.ic_warning)
                    .addButton(
                            "OK",                    // button text
                            R.color.pdlg_color_white,        // button text color
                            R.color.pdlg_color_green,        // button background color
                            new PrettyDialogCallback() {        // button OnClick listener
                                @Override
                                public void onClick() {
                                    // Do what you gotta do
                                    startActivity(new Intent(HomepageActivity.this, ViewsList.class));
                                    HomepageActivity.this.finish();
                                }
                            }
                    ).show();
            prettyDialog.setCancelable(false);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(HomepageActivity.this, ViewsList.class));
    }
}