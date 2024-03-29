package com.example.musixexo.homepages;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musixexo.CustomAdapter;
import com.example.musixexo.R;
import com.example.musixexo.custom.DottedProgress;
import com.example.musixexo.models.ModelClass;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ViewsList extends AppCompatActivity {
    ImageView imgvListPh;
    ArrayList<ModelClass> activityList = new ArrayList<>();
    RecyclerView rcvlist;
    CustomAdapter rcvAdaptor, rcvAdapter2;
    ModelClass modelClass;
    DottedProgress dottedProgress;
    MenuItem item;
    String[] spinner = {"Anime", "Movies"};
    String searchKey;
    ProgressBar progressBar;
    ArrayList<ModelClass> allitems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_views);
        BindViews();
        dottedProgress = new DottedProgress(ViewsList.this);
        dottedProgress.show();

        rcvAdaptor.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ArrayList<ModelClass> menulist) {
                Intent intent = new Intent(ViewsList.this, HomepageActivity.class);
                intent.putExtra("AnimeName", menulist.get(position).getActivityName());
                startActivity(intent);
                ViewsList.this.finish();
            }
        });


    }


    private void BindViews() {
        rcvlist = findViewById(R.id.rcvList);
        rcvAdaptor = new CustomAdapter(ViewsList.this, activityList);
        progressBar = findViewById(R.id.progressLoad);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        item = menu.findItem(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, spinner);
        final Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        spinner.setAdapter(adapter);
       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               switch (position) {
                   case 0:
                        activityList.clear();
                       ParseFetchData("anime");
                       break;
                   case 1:
                    activityList.clear();
                       ParseFetchData("movie");
                       break;
                       default:
                           break;

               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null && TextUtils.getTrimmedLength(query) > 0) {
                    activityList.clear();
                    rcvlist.setAdapter(rcvAdaptor);
                    QueryInParse(query);
                } else {
                    rcvAdapter2 = new CustomAdapter(ViewsList.this, allitems);
                    rcvlist.setAdapter(rcvAdapter2);
                    Log.i("Check", "null query");
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                activityList.clear();
                rcvlist.setAdapter(rcvAdaptor);
                QueryInParse(newText);
                return true;
            }
        });
        return true;
    }

    private void ParseFetchData(String key) {
        dottedProgress.show();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Episodes");
        query.whereEqualTo("cat", key);
        query.orderByAscending("name");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject obj : objects) {
                        modelClass = new ModelClass();
                        modelClass.setImgUri(Uri.parse(obj.get("thumb") + ""));
                        modelClass.setActivityName(obj.get("name") + "");
                        activityList.add(modelClass);

                    }
                    allitems = activityList;
                    rcvlist.setAdapter(rcvAdaptor);
                    dottedProgress.dismiss();
                }

            }
        });
    }

    private void QueryInParse(String query) {
        progressBar.setVisibility(View.VISIBLE);
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Episodes");
        query1.whereContains("name", query);
        query1.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject obj : objects) {
                        modelClass = new ModelClass();
                        modelClass.setImgUri(Uri.parse(obj.get("thumb") + ""));
                        modelClass.setActivityName(obj.get("name") + "");
                        activityList.add(modelClass);
                    }
                    progressBar.setVisibility(View.GONE);
                    rcvlist.setAdapter(rcvAdaptor);
                }

            }
        });

    }


}

