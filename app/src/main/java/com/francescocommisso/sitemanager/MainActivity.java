package com.francescocommisso.sitemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    DBHandler dbh;
    SiteAdapter sAdapt;
    ArrayList<Site> sites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbh = DBHandler.getInstance(this);
        sites = dbh.getSites();
        sAdapt = new SiteAdapter(this, sites);
        ListView siteListView = (ListView) findViewById(R.id.siteList);
        assert siteListView != null;
        siteListView.setAdapter(sAdapt);
        siteListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,SiteActivity.class);
                intent.putExtra("Position", position);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        menu.addSubMenu("UPDATE DATABASE");
        MenuItem addButton = menu.findItem(R.id.action_addSite);

        assert addButton != null;
        addButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(MainActivity.this,NewSiteActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(MainActivity.this,MainActivity.class);
        dbh.onUpgrade(dbh.getWritableDatabase(),0,1);
        sites.clear();
        for (Site s: dbh.getSites()){
            sites.add(s);
        }
        sAdapt.notifyDataSetChanged();
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        return true;
    }

    public void onBackPressed() {
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }


}
