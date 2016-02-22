package labouardy.com.dockerregistry;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import labouardy.com.dockerregistry.adapter.RepositoryAdapter;
import labouardy.com.dockerregistry.adapter.TagAdapter;
import labouardy.com.dockerregistry.dialog.Dialog;
import labouardy.com.dockerregistry.dialog.ErrorDialog;
import labouardy.com.dockerregistry.handler.APIHandler;
import labouardy.com.dockerregistry.handler.APIv1;
import labouardy.com.dockerregistry.handler.APIv2;
import labouardy.com.dockerregistry.model.Registry;
import labouardy.com.dockerregistry.model.RegistrySingleton;
import labouardy.com.dockerregistry.model.Repository;
import labouardy.com.dockerregistry.model.Tag;


public class Tags extends ActionBarActivity {
    private ListView lv;
    private List<Tag> tags;
    private TagAdapter adapter;
    private APIHandler api;
    private ProgressDialog progressDialog;
    private String repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        changeColor();
        init();
    }

    public void init(){
        repository=getIntent().getStringExtra("repository");

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Contacting the server ...");
        progressDialog.show();

        tags=new ArrayList<>();
        lv=(ListView)findViewById(R.id.tags);
        adapter=new TagAdapter(this, tags);
        lv.setAdapter(adapter);

        final Registry r= RegistrySingleton.getInstance();

        if(r.getVersion().compareTo("V1")==0){
            api=new APIv1();

            api.getImages(r, repository, new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            Dialog dialog=new ErrorDialog(Tags.this);
                            dialog.show("Server is not responding !");
                        }
                    });
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    String data=response.body().string();
                    try {
                        JSONObject tmp=new JSONObject(data);
                        Iterator<String> keys=tmp.keys();
                        while(keys.hasNext()){
                            String key=keys.next();
                            Tag tag=new Tag();
                            tag.setTag(key);
                            tags.add(tag);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }
                    });
                }
            });
        }else{
            api=new APIv2();
            api.getImages(r, repository, new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            Dialog dialog=new ErrorDialog(Tags.this);
                            dialog.show("Server is not responding !");
                        }
                    });
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    String data=response.body().string();
                    try {
                        JSONObject tmp=new JSONObject(data);
                        JSONArray d=tmp.getJSONArray("tags");
                        for(int i=0;i<d.length();i++){
                            Tag tag=new Tag();
                            tag.setTag(d.getString(i));
                            tags.add(tag);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }
                    });
                }
            });
        }

    }

    public void changeColor(){
        int color = getResources().getColor(R.color.dashboard);
        ColorDrawable colorDrawable = new ColorDrawable(color);
        getSupportActionBar().setBackgroundDrawable(colorDrawable);

        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(color);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tags, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
