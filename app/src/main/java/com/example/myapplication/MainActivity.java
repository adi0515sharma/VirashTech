package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public List<model> modelList = new ArrayList<>();
    public model[] mymodel;
    public RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String json = loadJSONFile();
        recyclerView = findViewById(R.id.recyclerView);


        if(!json.equals("")){
            new Putting_Value().execute(json);

        }
    }

    public String loadJSONFile() {
        String json = null;
        try {
            File file = new File("myJson.json");
            InputStream is = getAssets().open("myJson.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
        Log.e("json",json);
        return json;
    }

    public class Putting_Value extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();



        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONArray jsonArray = new JSONArray(strings[0]);
                for(int i = 0;i<jsonArray.length();i++){
                    JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                    model m = new model();
                    m.setName(jsonObject.get("name").toString());
                    m.setSlug(jsonObject.get("slug").toString());
                    if(jsonObject.has("img")){
                        m.setImg(jsonObject.get("img").toString());
                    }
                    else{
                        m.setImg("/");
                    }
                    modelList.add(m);

                }
                mymodel = new model[modelList.size()];
                for(model m_2:modelList){
                    model m_3 = new model();
                    m_3.setName(m_2.getName());
                    m_3.setStatus(m_2.getStatus());
                    m_3.setSlug(m_2.getSlug());
                    m_3.setImg(m_2.getImg());
                    mymodel[modelList.indexOf(m_2)] = m_3;
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            recyclerView.setHasFixedSize(true);
            MyAdapter adapter;

            if(getResources().getConfiguration().orientation==1){
                Log.e("hii",mymodel.length+"");
                adapter = new MyAdapter(mymodel,R.layout.for_portrait);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }
            else{
                adapter = new MyAdapter(mymodel,R.layout.for_landscape);
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
            }

            recyclerView.setAdapter(adapter);
        }
    }


}