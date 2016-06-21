package com.francescocommisso.sitemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;


public class LotActivity extends AppCompatActivity {

    Site site;
    String siteName;
    Lot lot;
    int lotId;
    int lotPosition;
    DBHandler dbh = DBHandler.getInstance(this);
    Button completeButton;
    Button incompleteButton;
    Button issueButton;
    Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lot);
        completeButton = (Button) findViewById(R.id.button_set_complete);
        incompleteButton = (Button) findViewById(R.id.button_set_incomplete);
        issueButton = (Button) findViewById(R.id.button_set_issue);
        site = dbh.getSites().get(getIntent().getExtras().getInt("SitePosition"));
        siteName = site.getName();
        lotPosition = getIntent().getExtras().getInt("LotPosition");
        lotId = getIntent().getExtras().getInt("LotId");
        lot = site.getLot(lotPosition);
        intent = new Intent(LotActivity.this,SiteActivity.class);
        intent.putExtra("Position", getIntent().getExtras().getInt("SitePosition"));

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbh.updateLotStatus(site.getFormattedName(), lotId,Lot.COMPLETE);
                site.getLot(lotPosition).setStatus(Lot.COMPLETE);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });
        incompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbh.updateLotStatus(site.getFormattedName(), lotId,Lot.INCOMPLETE);
                site.getLot(lotPosition).setStatus(Lot.INCOMPLETE);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });

        issueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbh.updateLotStatus(site.getFormattedName(), lotId,Lot.ERROR);
                site.getLot(lotPosition).setStatus(Lot.ERROR);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lot_activity, menu);
        setTitle(siteName + " - " + String.valueOf(lotId));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }
}
