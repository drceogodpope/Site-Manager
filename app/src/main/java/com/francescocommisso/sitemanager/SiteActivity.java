package com.francescocommisso.sitemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import java.util.ArrayList;

public class SiteActivity extends AppCompatActivity {

    ViewPager vp;
    Site site;
    ArrayList<Lot> lots;
    DBHandler dbh;
    GridView gridView;
    LotGridAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site);
        vp = (ViewPager) findViewById(R.id.view_pager);
        dbh = DBHandler.getInstance(this);
        site = dbh.getSites().get(getIntent().getExtras().getInt("Position"));
        lots = dbh.getLots(dbh.getWritableDatabase(),site.getFormattedName());
        gridView = (GridView)findViewById(R.id.gridView);
        adapter = new LotGridAdapter(lots,this);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SiteActivity.this,LotActivity.class);
                System.out.println(position);
                intent.putExtra("LotPosition",position);
                intent.putExtra("LotId",position + 1);
                intent.putExtra("SitePosition",getIntent().getExtras().getInt("Position"));
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.addSubMenu("Delete Site");
        setTitle(site.getName());
        System.out.println("Options?");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(SiteActivity.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        if(dbh.checkExists(site.getName())) {
            dbh.deleteSite(site);
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SiteActivity.this,MainActivity.class);
        startActivity(intent);
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }
}
