package com.example.vedasmart;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.RECORD_AUDIO;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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
import com.example.vedasmart.Adapters.side_NavigationAdapter;
import com.example.vedasmart.DashBordServerResponseModels.Sign_out_model;
import com.example.vedasmart.InterFace.Side_navigation_interface;
import com.example.vedasmart.InterFace.Sub_category1_Interface;
import com.example.vedasmart.DashBordServerResponseModels.CategoryInfo;
import com.example.vedasmart.DashBordServerResponseModels.DailyDeals;
import com.example.vedasmart.DashBordServerResponseModels.DashBoardResponse;
import com.example.vedasmart.DashBordServerResponseModels.advertisements;
import com.example.vedasmart.DashBordServerResponseModels.banners;
import com.example.vedasmart.SideNavigationActivities.Faq;
import com.example.vedasmart.SideNavigationActivities.Privacy_policy;
import com.example.vedasmart.SideNavigationActivities.Ratings;
import com.example.vedasmart.SideNavigationActivities.Return_refund;
import com.example.vedasmart.SideNavigationActivities.Terms_and_conditions;
import com.google.android.material.navigation.NavigationView;
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

public class DashBoard extends AppCompatActivity implements Sub_category1_Interface, Side_navigation_interface {
    Context context;
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
    ArrayList<com.example.vedasmart.DashBordServerResponseModels.BestSellings> BestSellings = new ArrayList<>();
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

    NavigationView navigationView;
    //Storing phone number in shared preferences
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    SharedPreferences sharedpreferences;
    String phone;

    side_NavigationAdapter navigationAdapter;
    ArrayList<String> sidemenu_items = new ArrayList<>();
    RecyclerView side_menu_recyclerview;


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
        navigationSidemenu();
    }

    private void navigationSidemenu() {
        if (phone == null) {
            sidemenu_items.add("Home");
            sidemenu_items.add("Terms and conditions");
            sidemenu_items.add("Privacy Policy");
            sidemenu_items.add("Refunds and Returns");
            sidemenu_items.add("FAQ's");
            sidemenu_items.add("Ratings");
            sidemenu_items.add("Login");
        } else {
            sidemenu_items.add("Home");
            sidemenu_items.add("My orders");
            sidemenu_items.add("My WishList");
            sidemenu_items.add("Addresses");
            sidemenu_items.add("Terms and conditions");
            sidemenu_items.add("Privacy Policy");
            sidemenu_items.add("Refunds and Returns");
            sidemenu_items.add("FAQ's");
            sidemenu_items.add("Ratings");
            sidemenu_items.add("Sign out");

        }
        side_menu_recyclerview = findViewById(R.id.side_navigation_recyclerview);
        side_menu_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        navigationAdapter = new side_NavigationAdapter(DashBoard.this, this, sidemenu_items);
        side_menu_recyclerview.setAdapter(navigationAdapter);

    }

    private void searchButtonActions() {
        search = findViewById(R.id.search_btn);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ButtonText = search.getText().toString();
                if (ButtonText.equals("Search")) {
                    if (text.getText().toString().isEmpty()) {
                        Toast.makeText(DashBoard.this, "Please Enter Product Name", Toast.LENGTH_SHORT).show();


                    } else {
                        search.setText("Clear");
                    }

                } else {
                    if (ButtonText.equals("Clear")) {
                        search.setText("Search");
                        text.setText("");

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

        navigationView = findViewById(R.id.sidebar);
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        phone = sharedpreferences.getString(PHONE_NUMBER, null);
        View header = navigationView.getHeaderView(0);
        TextView textView = header.findViewById(R.id.side_menu_logout);
        if (phone == null) {
            textView.setText("Login/Sign up");

        } else {
            textView.setText(phone);
        }
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
        } else if (messageEvent.body != null && messageEvent.msg.equals("LogoutApi")) {
            try {
                JSONObject jObj = new JSONObject(messageEvent.body);
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
            Gson gson = new Gson();
            Sign_out_model sign_out_model = gson.fromJson(messageEvent.body, Sign_out_model.class);
            if (sign_out_model.getResponse() == 3) {

                Log.e("signout", "sign out");
                startActivity(new Intent(getApplicationContext(), Sign_in.class));
                Toast.makeText(getApplicationContext(), sign_out_model.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }


    private void getProducts() {

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

    private void logoutApiPramas() {
        JsonObject CheckUserObj = new JsonObject();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("PhoneNumber", phone);
            jsonObject.put("deviceType", "Mobile");

            JsonParser jsonParser = new JsonParser();
            CheckUserObj = (JsonObject) jsonParser.parse(jsonObject.toString());
            Log.e("checkBuyProject:", " " + CheckUserObj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Controller.getInstance().ApiCallBackForPostMethods(DashBoard.this, "customer/signout", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjkzODEyMDgxMTIiLCJpYXQiOjE2NjkzNjYyMjh9.RUZj9BaSV9Hg8UPKr65nA4vYz84AJQJNj5L_ysaLJDA", CheckUserObj, "LogoutApi");

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
            finishAffinity();
            super.onBackPressed();
        }
    }

    @Override
    public void side_Navigation_On_ItemClick(int position) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if (phone == null) {
            if (position == 0) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else if (position == 1) {
                startActivity(new Intent(DashBoard.this, Terms_and_conditions.class));
            } else if (position == 2) {
                startActivity(new Intent(DashBoard.this, Privacy_policy.class));
            } else if (position == 3) {
                startActivity(new Intent(DashBoard.this, Return_refund.class));
            } else if (position == 4) {
                startActivity(new Intent(DashBoard.this, Faq.class));
            } else if (position == 5) {
                startActivity(new Intent(DashBoard.this, Ratings.class));
            } else if (position == 6) {
                startActivity(new Intent(DashBoard.this, Sign_in.class));
                finishAffinity();
            }
        } else {
            if (position == 0) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else if (position == 4) {
                startActivity(new Intent(DashBoard.this, Terms_and_conditions.class));
            } else if (position == 5) {
                startActivity(new Intent(DashBoard.this, Privacy_policy.class));
            } else if (position == 6) {
                startActivity(new Intent(DashBoard.this, Return_refund.class));
            } else if (position == 7) {
                startActivity(new Intent(DashBoard.this, Faq.class));
            } else if (position == 8) {
                startActivity(new Intent(DashBoard.this, Ratings.class));
            } else if (position == 9) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashBoard.this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure,do you want to logout from this device?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {

                    logoutApiPramas();
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.clear();
                    editor.apply();
                });

                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }


        }
    }
}
