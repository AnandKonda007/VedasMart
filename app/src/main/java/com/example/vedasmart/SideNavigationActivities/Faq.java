package com.example.vedasmart.SideNavigationActivities;

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

import com.example.vedasmart.Controller;
import com.example.vedasmart.DashBoard;
import com.example.vedasmart.R;
import com.google.android.material.navigation.NavigationView;

public class Faq extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView sidemenu;
    NavigationView navigationView;
    WebView webView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        progressDialog = new ProgressDialog(Faq.this);
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
                    progressDialog.show();
                    startActivity(new Intent(Faq.this, DashBoard.class));
                    progressDialog.dismiss();


                }else if (id == R.id.terms_and_conditions) {
                    progressDialog.show();
                    startActivity(new Intent(Faq.this, Terms_and_conditions.class));
                    progressDialog.dismiss();

                }
                else if (id == R.id.privacy_policy) {
                    progressDialog.show();
                    startActivity(new Intent(Faq.this, Privacy_policy.class));
                    progressDialog.dismiss();

                }
                else if (id == R.id.refund_returns) {
                    progressDialog.show();
                    startActivity(new Intent(Faq.this, Return_refund.class));
                    progressDialog.dismiss();


                }
                else if (id == R.id.faq) {
                    drawerLayout.openDrawer(GravityCompat.START);
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
        webView = findViewById(R.id.web_faq);
        Log.e("Webview", "calling webview page");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://liveapi-vmart.softexer.com/frequentquestions.html");
        progressDialog.dismiss();

        //directly we load url below
        //webView.loadUrl("http://liveapi-vmart.softexer.com/frequentquestions.html");

    }
}