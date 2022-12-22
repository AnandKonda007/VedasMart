package com.example.vedasmart;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.vedasmart.Adapters.sub_catogery2Adapter;
import com.example.vedasmart.Adapters.sub_catogery1Adapter;
import com.example.vedasmart.InterFace.Sub_category2_Interface;
import com.example.vedasmart.ServerResponseModels.DashBoardResponse;
import com.example.vedasmart.ServerResponseModels.Info;
import com.example.vedasmart.ServerResponseModels.SubCategorysModel;
import com.example.vedasmart.ServerResponseModels.Sub_Category_Inner_Products;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubCatogeries extends AppCompatActivity implements Sub_category2_Interface {

    ArrayList<SubCategorysModel> subCategorys = new ArrayList<>();
    ArrayList<Info> subProducts = new ArrayList<>();
    sub_catogery1Adapter adapter1;
    sub_catogery2Adapter adapter2;
    RecyclerView recyclerView1, recyclerView2;
    RecyclerView.LayoutManager layoutManager, layoutManager1;
    DrawerLayout drawerLayout2;
    ImageView backimage, subcategory_menu;
    String token = "";
    SwipeRefreshLayout swipeRefreshLayout;
    String sucaregoryID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_catogeries);
        recyclerView1 = findViewById(R.id.sub_catogery);
        recyclerView2 = findViewById(R.id.sub_catogery_inner_products);
        setAdapter();
        setAdapter1();
        Controller.getInstance().fillcontext(getApplicationContext());

        getTokenFromSharedPreference();
        String CategoryID = getIntent().getExtras().getString("categoryID");
        if (CategoryID != null) {
            getSubCategotyProducts(CategoryID);
        }
        backimage = findViewById(R.id.back);
        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        subcategory_menu = findViewById(R.id.sub_category_menu);
        drawerLayout2 = findViewById(R.id.drawerLayout2);
        subcategory_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout2.openDrawer(GravityCompat.END);

            }
        });
        swipeRefreshLayout = findViewById(R.id.swipe_down_to_refresh2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(SubCatogeries.this, "Successfully refreshed", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void getSubCategotyProducts(String categoryID) {
        JsonObject CheckUserObj = new JsonObject();
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("type", categoryID);
            JsonParser jsonParser = new JsonParser();
            CheckUserObj = (JsonObject) jsonParser.parse(jsonObject.toString());
            Log.e("checkBuyProject:", " " + CheckUserObj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Controller.getInstance().ApiCallBackForPostMethods(this, "categories/subcategoryfetch", token, CheckUserObj, "Subcategories");

    }

    private void getSubCategotyInnerProducts(String SubCategoryID) {
        JsonObject CheckUserObj = new JsonObject();
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("type", SubCategoryID);
            JsonParser jsonParser = new JsonParser();

            CheckUserObj = (JsonObject) jsonParser.parse(jsonObject.toString());
            Log.e("getSubCategotyInner:", " " + CheckUserObj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Controller.getInstance().ApiCallBackForPostMethods(this, "sellerProducts/productfetch", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjkzODEyMDgxMTIiLCJpYXQiOjE2NjkzNjYyMjh9.RUZj9BaSV9Hg8UPKr65nA4vYz84AJQJNj5L_ysaLJDA", CheckUserObj, "Subcategories_fetch");

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(SubCatogeries.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(SubCatogeries.this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(Controller.MessageEvent messageEvent) {
        if (messageEvent.body != null && messageEvent.msg.equals("Subcategories")) {
            Log.e("response", "call" + messageEvent.body);
            try {
                JSONObject jObj = new JSONObject(messageEvent.body);
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
            Gson gson = new Gson();
            DashBoardResponse dashBoardResponse = gson.fromJson(messageEvent.body, DashBoardResponse.class);
            Log.e("DashBoardResponse", "call" + new Object().toString());
            if (dashBoardResponse.getResponse() == 3) {
                subCategorys.clear();
                subCategorys = dashBoardResponse.getCategoryInfo().get(0).getSubCategorys();
                setAdapter();
                loadFirstRecord();
                Toast.makeText(getApplicationContext(), dashBoardResponse.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), dashBoardResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if (messageEvent.msg.equals("Subcategories_fetch")) {
            Log.e("Subcategories_fetch", "call" + messageEvent.body);
            try {
                JSONObject jObj = new JSONObject(messageEvent.body);
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
            Gson gson = new Gson();
            Sub_Category_Inner_Products subCategoryInnerProducts = gson.fromJson(messageEvent.body, Sub_Category_Inner_Products.class);
            if (subCategoryInnerProducts.getResponse() == 3) {
                subProducts.clear();
                subProducts = subCategoryInnerProducts.getInfo();
                setAdapter1();
                Toast.makeText(getApplicationContext(), subCategoryInnerProducts.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), subCategoryInnerProducts.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }


    private void loadFirstRecord() {
        if (subCategorys.size() > 0) {
            getSubCategotyInnerProducts(subCategorys.get(0).getSubCategoryID());
        }
    }


    private void setAdapter() {
        layoutManager = new LinearLayoutManager(SubCatogeries.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(layoutManager);
        adapter1 = new sub_catogery1Adapter(this, SubCatogeries.this, subCategorys);
        recyclerView1.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();
    }

    private void setAdapter1() {
        layoutManager1 = new LinearLayoutManager(SubCatogeries.this, LinearLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(layoutManager1);
        adapter2 = new sub_catogery2Adapter(SubCatogeries.this, subProducts);
        recyclerView2.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
    }

    private void getTokenFromSharedPreference() {
        SharedPreferences sharedPref = getSharedPreferences("tokenPrefs", MODE_PRIVATE);
        token = sharedPref.getString("token", null);
    }

    @Override
    public void OnItemClickSub(int position, String SubCategoryID) {
        sucaregoryID = SubCategoryID;
        if (sucaregoryID != null) {
            getSubCategotyInnerProducts(sucaregoryID);
        }
    }
}