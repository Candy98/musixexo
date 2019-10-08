package com.example.musixexo.homepages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.musixexo.PlayerActivity;
import com.example.musixexo.R;
import com.example.musixexo.custom.DottedProgress;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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
                        url = obj.getList("url");
                        titleVid.setText(obj.get("name") + "");
                        IsCompleteChecker(obj);


                        LoadTitleGlideImg(obj.get("titleimg") + "");
                    }
                    dottedProgress.dismiss();

                    arrayAdapter = new ArrayAdapter<>(HomepageActivity.this, android.R.layout.simple_list_item_1, episodes);
                    listView.setAdapter(arrayAdapter);

                }
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(HomepageActivity.this, PlayerActivity.class);
                        intent.putExtra("Url", url.get(i).toString());
                        startActivity(intent);
                    }
                });

            }

        });

    }

    private void LoadTitleGlideImg(String url) {
        Glide.with(HomepageActivity.this).load(url).centerCrop().into(imgTitle);
    }

    private void BindViews() {
        listView = findViewById(R.id.lvEpList);
        imgTitle = findViewById(R.id.imgTitle);
        titleVid = findViewById(R.id.titleVid);
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