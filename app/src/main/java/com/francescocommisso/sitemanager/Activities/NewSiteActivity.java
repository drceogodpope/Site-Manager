package com.francescocommisso.sitemanager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.francescocommisso.sitemanager.DBHandler;
import com.francescocommisso.sitemanager.MainActivity;
import com.francescocommisso.sitemanager.R;
import com.francescocommisso.sitemanager.Site;
import com.podcopic.animationlib.library.AnimationType;
import com.podcopic.animationlib.library.StartSmartAnimation;

public class NewSiteActivity extends AppCompatActivity {

    DBHandler dbh;
    EditText inputName;
    EditText inputNumberOfLots;
    ImageButton siteImage;
    Button createSite;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_site);
        dbh = DBHandler.getInstance(this);
        inputName = (EditText) findViewById(R.id.input_site_name);
        inputNumberOfLots = (EditText) findViewById(R.id.input_number_of_lots);
        siteImage = (ImageButton) findViewById(R.id.imageButton);
        createSite = (Button) findViewById(R.id.button_create_site);
        createSite.setOnClickListener(new View.OnClickListener(){  @Override

        public void onClick(View v) {
            if(!inputName.getText().toString().equals("") && !inputNumberOfLots.getText().toString().equals("") ){
                String name = inputName.getText().toString();
                String totalLots = inputNumberOfLots.getText().toString();
                if(!dbh.checkExists(name)){
                    if(Integer.valueOf(totalLots)<400) {
                        int tl = Integer.parseInt(totalLots);
                        dbh.addSite(new Site(name, tl));
                        Intent intent = new Intent(NewSiteActivity.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Too many Lots",Toast.LENGTH_SHORT).show();
                        shakeTotalLots();
                    }
                }
                else {
                        Toast.makeText(NewSiteActivity.this,"Site name taken",Toast.LENGTH_SHORT).show();
                    shakeInputName();
                }
            }

            else{
                if(inputName.getText().toString().equals("")){
                    shakeInputName();
                }

                if(inputNumberOfLots.getText().toString().equals("")){
                    shakeTotalLots();
                }
            }
        }
        });


        siteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, 1);
            }
        });


    }

    public void shakeTotalLots(){
        StartSmartAnimation.startAnimation(inputNumberOfLots, AnimationType.Shake , 1000 , 0 , true );
    }

    public void shakeInputName(){
        StartSmartAnimation.startAnimation(inputName, AnimationType.Shake , 1000 , 0 , true );
    }


    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }


}
