package com.example.vedasmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Terms_and_conditions extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView sidemenu;
    NavigationView navigationView;
    WebView webView;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);
        progressDialog = new ProgressDialog(Terms_and_conditions.this);
        checkNetwork();
        sidemenu = findViewById(R.id.menu);
        drawerLayout = findViewById(R.id.drawerLayout);
        sidemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }

        });
        navigationView = findViewById(R.id.sidebar);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.home1) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    startActivity(new Intent(Terms_and_conditions.this, DashBoard.class));

                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }


    private void checkNetwork() {
        Controller.getInstance().fillcontext(getApplicationContext());
        if (Controller.getInstance().checkNetwork()) {
            webActions();
        } else {
            Log.e("Internet connection", "Please Check Your Internet Connection");
            //Toast.makeText(DashBoard.this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();
        }
    }


    private void webActions() {
        webView = findViewById(R.id.web_terms_and_conditions);
        Log.e("Webview", "calling webview page");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/PrivacyPolicy.htm");
        progressDialog.dismiss();

        //directly we load url below
        //webView.loadUrl("http://liveapi-vmart.softexer.com/TermsAndConditions.htm");

    }
}