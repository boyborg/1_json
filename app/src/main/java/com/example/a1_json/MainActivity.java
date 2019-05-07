package com.example.a1_json;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
String id,name,username,email,street,suite,city,zipcode,address;

private RecyclerView recyclerView;
private Adapter_Json adapt;
private ArrayList<Char_> charlist;
private Button btnGetdata;
private BufferedReader reader=null;
private HttpURLConnection connection = null;
ProgressDialog progressDialog;
SwipeRefreshLayout mswipeRefresh;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGetdata=(Button)findViewById(R.id.button_data);

        mswipeRefresh=(SwipeRefreshLayout)findViewById(R.id.swipefresh);
        mswipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Async task=new Async();
                //Object[] arg = new String[]{null,null,null};
                task.execute();
            }
        });

        btnGetdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Async task=new Async();
                task.execute();
            }
        });
       /*

         jason hashmap
        name=(TextView)findViewById(R.id.idname);
        exp=(TextView)findViewById(R.id.idexp);
        classid=(TextView)findViewById(R.id.idclass);



        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        adapt=new Adapter_Json();

    }
    public String loadJSONFromAsset(){
        String jason = null;
        try {
            InputStream is = getAssets().open("program_1.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jason = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return jason;
        */
    }


    public class Async extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("mohon tunggu");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params){
            charlist=new ArrayList<>();
            try {
               // JSONObject obj=new JSONObject(loadJSONFromAsset());
               // JSONArray userArray=obj.getJSONArray("program_1");
                JSONArray userArray=new JSONArray(getData());
                for (int i=0; i<userArray.length();i++) {
                    JSONObject userDetail = userArray.getJSONObject(0);
                    id = userDetail.getString("id");
                    name = userDetail.getString("name");
                    username = userDetail.getString("username");
                    email = userDetail.getString("email");

                    JSONObject addres = userDetail.getJSONObject("address");
                    street = addres.getString("street");
                    suite = addres.getString("suite");
                    city = addres.getString("city");
                    zipcode = addres.getString("zipcode");

                    address = street+", "+suite+", "+city+", "+zipcode;

                    charlist.add(new Char_(id,name,username,email,address));
                }}catch (JSONException x) {
                x.printStackTrace();

            }
            return null;
        }

            @Override
        protected void onPostExecute(String Result){
            mswipeRefresh.setRefreshing(false);
            progressDialog.dismiss();
            recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
            adapt=new Adapter_Json(charlist);
            RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(MainActivity.this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapt);
        }

    }

    public String getData(){
        String line=" ";
        try{
            URL url = new URL("https://jsonplaceholder.typicode.com/users");
            connection =(HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) !=null){
                buffer.append(line);
            }
            return buffer.toString();
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(connection !=null)connection.disconnect();
            try{
                if(reader !=null)reader.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }

}

