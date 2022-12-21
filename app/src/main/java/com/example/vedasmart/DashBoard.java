package com.example.vedasmart;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.RECORD_AUDIO;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.vedasmart.Adapters.categories1Adapter;
import com.example.vedasmart.Adapters.categories2Adapter;
import com.example.vedasmart.Adapters.categories3Adapter;
import com.example.vedasmart.InterFace.Sub_category1_Interface;
import com.example.vedasmart.ServerResponseModels.CategoryInfo;
import com.example.vedasmart.ServerResponseModels.DailyDeals;
import com.example.vedasmart.ServerResponseModels.DashBoardResponse;
import com.example.vedasmart.ServerResponseModels.advertisements;
import com.example.vedasmart.ServerResponseModels.banners;
import com.example.vedasmart.ServerResponseModels.bestSellings;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class DashBoard extends AppCompatActivity implements Sub_category1_Interface {
    //Request code for Location
    private static final int Loc_REQ_CODE = 1025;

    ProgressDialog progressDialog;
    DrawerLayout drawerLayout;
    // Voice to text
    ImageView mic;
    EditText text;
    //Array Lists
    ArrayList<CategoryInfo> Products = new ArrayList<>();
    ArrayList<advertisements> Advertisements = new ArrayList<>();
    ArrayList<banners> Banners = new ArrayList<>();
    ArrayList<bestSellings> BestSellings = new ArrayList<>();
    ArrayList<DailyDeals> dailyDeals = new ArrayList<>();
    //Recyclerviews
    RecyclerView recyclerView, recyclerView2, recyclerView3;
    RecyclerView.LayoutManager layoutManager, layoutManager2, layoutManager3;

    //Adapters
    categories1Adapter adapter;
    categories2Adapter adapter2;
    categories3Adapter adapter3;

    //Banners
    ImageView imageView, imageView2, imageView3;

    //Image sliders
    ImageSlider imageSlider;
    //Left sidemenu
    ImageView sidemenu;
    //Swipe down to Refresh
    SwipeRefreshLayout swipeRefreshLayout;
    //Button
    Button search;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        locationPermission();
        progressDialog = new ProgressDialog(DashBoard.this);
        findViewByIds();
        layoutManagers();
        searchButtonActions();
        checkNetwork();
        sidemenuActions();
        voiceActions();
        swipeToRefresh();
        setAdapter();
        //setAdapter2();
        //setAdapter3();
    }


    private void searchButtonActions() {
        search = findViewById(R.id.search_btn);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ButtonText = search.getText().toString();
                if (ButtonText.equals("Search")) {
                    search.setText("Clear");
                } else {
                    if (ButtonText.equals("Clear")) {
                        search.setText("Search");

                    }
                }
            }

        });
    }

    private void swipeToRefresh() {
        swipeRefreshLayout = findViewById(R.id.swipe_down_to_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkNetwork();
                Toast.makeText(DashBoard.this, "Successfully refreshed", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void locationPermission() {
        if (checkPermission()) {

        } else {
            ActivityCompat.requestPermissions(DashBoard.this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION, RECORD_AUDIO}, Loc_REQ_CODE);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Loc_REQ_CODE) {
            statusCheck();
            if (grantResults.length > 0) {
                int loc1 = grantResults[0];
                int loc2 = grantResults[1];
                int recordAudio = grantResults[2];


                boolean checkloc1 = loc1 == PackageManager.PERMISSION_GRANTED;
                boolean checkloc2 = loc2 == PackageManager.PERMISSION_GRANTED;
                boolean checkAudio = recordAudio == PackageManager.PERMISSION_GRANTED;

                if (checkloc1 && checkloc2 && checkAudio) {

                    // Toast.makeText(this, "permission granted ", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(this, "permission Denied ", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    public boolean checkPermission() {
        int resultLoc1 = ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION);
        int resultLoc2 = ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION);
        int resultmicrophone = ActivityCompat.checkSelfPermission(this, RECORD_AUDIO);

        return resultLoc1 == PackageManager.PERMISSION_GRANTED && resultLoc2 == PackageManager.PERMISSION_GRANTED && resultmicrophone == PackageManager.PERMISSION_GRANTED;

    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Allow Location Access")
                .setMessage("Vedas_Mart uses your location to find products that can be delivered to you")
                .setIcon(R.drawable.location)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void voiceActions() {
        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent
                        = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

                try {
                    startActivityForResult(intent, 200);
                } catch (Exception e) {
                   /* Toast
                            .makeText(DashBoard.this, " " + e.getMessage(),
                                    Toast.LENGTH_SHORT)
                            .show();*/
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                text.setText(Objects.requireNonNull(result).get(0));
            }
        }
    }

    private void sidemenuActions() {
        sidemenu = findViewById(R.id.sidemenu);
        drawerLayout = findViewById(R.id.drawerLayout);

        sidemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void checkNetwork() {
        progressDialog.show();
        Controller.getInstance().fillcontext(getApplicationContext());
        if (Controller.getInstance().checkNetwork()) {
            getProducts();
        } else {
            Log.e("Internet connection", "Please Check Your Internet Connection");
            Toast.makeText(DashBoard.this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();
        }
    }


    private void findViewByIds() {
        recyclerView = findViewById(R.id.categories1);
        recyclerView2 = findViewById(R.id.categories2);
        recyclerView3 = findViewById(R.id.categories3);

        imageSlider = findViewById(R.id.imageslider);
        imageView = findViewById(R.id.banner1);
        imageView2 = findViewById(R.id.banner2);
        imageView3 = findViewById(R.id.banner3);

        text = findViewById(R.id.search);
        mic = findViewById(R.id.mic_icon);


    }

    private void layoutManagers() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new categories1Adapter(this, DashBoard.this, Products);
        recyclerView.setAdapter(adapter);

        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter2 = new categories2Adapter(this, dailyDeals);
        recyclerView2.setAdapter(adapter2);


        recyclerView3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter3 = new categories3Adapter(this, BestSellings);
        recyclerView3.setAdapter(adapter3);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProducts();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(DashBoard.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(DashBoard.this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(Controller.MessageEvent messageEvent) {
        progressDialog.dismiss();
        // Log.e("response", "call" + messageEvent.body);
        if (messageEvent.body != null && messageEvent.msg.equals("DashBoardApi")) {
            try {
                JSONObject jObj = new JSONObject(messageEvent.body);
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
            Gson gson = new Gson();
            DashBoardResponse dashBoardResponse = gson.fromJson(messageEvent.body, DashBoardResponse.class);
            Log.e("DashBoardResponse", "call" + dashBoardResponse.getResponse());
            if (dashBoardResponse.getResponse() == 3) {
                Products.clear();
                Products = dashBoardResponse.getCategoryInfo();
                adapter = new categories1Adapter(DashBoard.this, this, Products);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                if (dashBoardResponse.getDashBoardData().size() > 0) {
                    Advertisements = dashBoardResponse.getDashBoardData().get(0).getAdvertisements();
                    ArrayList<SlideModel> slideModels = new ArrayList<>();
                    if (Advertisements.size() > 0) {
                        for (advertisements obj : Advertisements) {
                            slideModels.add(new SlideModel(Api.img_url + obj.getAdvertiesmentImage(), ScaleTypes.FIT));
                        }
                    }
                    imageSlider.setImageList(slideModels);
                }
                if (dashBoardResponse.getDashBoardData().size() > 0) {
                    Banners = dashBoardResponse.getDashBoardData().get(0).getBaners();
                    Glide.with(this)
                            .load(Api.img_url + Banners.get(0).getBanerImage())
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_foreground)
                            .centerCrop()
                            .into(imageView);
                    Glide.with(this)
                            .load(Api.img_url + Banners.get(1).getBanerImage())
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_foreground)
                            .centerCrop()
                            .into(imageView2);
                    Glide.with(this)
                            .load(Api.img_url + Banners.get(2).getBanerImage())
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_foreground)
                            .centerCrop()
                            .into(imageView3);

                }

                if (dashBoardResponse.getResponse() == 3) {
                    dailyDeals.clear();
                    dailyDeals = dashBoardResponse.getDailyDeals();
                    adapter2 = new categories2Adapter(this, dailyDeals);
                    recyclerView2.setAdapter(adapter2);
                    adapter2.notifyDataSetChanged();

                }

                if (dashBoardResponse.getResponse() == 3) {
                    BestSellings.clear();
                    BestSellings = dashBoardResponse.getBestSellings();
                    adapter3 = new categories3Adapter(this, BestSellings);
                    recyclerView3.setAdapter(adapter3);
                    adapter3.notifyDataSetChanged();

                }


            }
        }
    }

    private void
    getProducts() {
        JsonObject CheckUserObj = new JsonObject();
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("type", "All");
            JsonParser jsonParser = new JsonParser();

            CheckUserObj = (JsonObject) jsonParser.parse(jsonObject.toString());
            Log.e("checkBuyProject:", " " + CheckUserObj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Controller.getInstance().ApiCallBackForPostMethods(this, "categories/subcategoryfetch", token, CheckUserObj, "DashBoardApi");


    }

    private void setAdapter() {
        layoutManager = new LinearLayoutManager(DashBoard.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new categories1Adapter(DashBoard.this, this, Products);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void OnItemClick(int position, String CategoryID) {
        Intent intent = new Intent(DashBoard.this, SubCatogeries.class);
        intent.putExtra("categoryID", CategoryID);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }
}
