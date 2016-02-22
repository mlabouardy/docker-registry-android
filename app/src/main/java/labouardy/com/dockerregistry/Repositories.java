package labouardy.com.dockerregistry;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import labouardy.com.dockerregistry.adapter.RepositoryAdapter;
import labouardy.com.dockerregistry.dialog.Dialog;
import labouardy.com.dockerregistry.dialog.ErrorDialog;
import labouardy.com.dockerregistry.handler.APIHandler;
import labouardy.com.dockerregistry.handler.APIv1;
import labouardy.com.dockerregistry.handler.APIv2;
import labouardy.com.dockerregistry.model.Registry;
import labouardy.com.dockerregistry.model.RegistrySingleton;
import labouardy.com.dockerregistry.model.Repository;

public class Repositories extends ActionBarActivity {
    private ListView lv;
    private List<Repository> repositories;
    private RepositoryAdapter adapter;
    private APIHandler api;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        changeColor();
        init();

    }

    public void init(){
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Contacting the server ...");
        progressDialog.show();

        repositories=new ArrayList<>();
        lv=(ListView)findViewById(R.id.repositories);
        adapter=new RepositoryAdapter(this, repositories);
        lv.setAdapter(adapter);

        final Registry r= RegistrySingleton.getInstance();

        if(r.getVersion().compareTo("V1")==0){
            api=new APIv1();
            api.getRepositories(r, new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            Dialog dialog=new ErrorDialog(Repositories.this);
                            dialog.show("Server is not responding !");
                        }
                    });
                }

                @Override
                public void onResponse(final Response response) throws IOException {
                    String data = response.body().string();
                    try {
                        JSONObject o = new JSONObject(data);
                        JSONArray tmp = o.getJSONArray("results");
                        for (int i = 0; i < tmp.length(); i++) {
                            Repository repository = new Repository();
                            repository.setName(tmp.getJSONObject(i).getString("name"));
                            repositories.add(repository);
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
            api.getRepositories(r,new Callback(){

                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            Dialog dialog=new ErrorDialog(Repositories.this);
                            dialog.show("Server is not responding !");
                        }
                    });
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    String data = response.body().string();
                    try {
                        JSONObject o = new JSONObject(data);
                        JSONArray tmp = o.getJSONArray("repositories");
                        for (int i = 0; i < tmp.length(); i++) {
                            Repository repository = new Repository();
                            repository.setName(tmp.getString(i));
                            repositories.add(repository);
                        }
                    }catch (JSONException e) {
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


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(Repositories.this, Tags.class);
                intent.putExtra("repository", repositories.get(i).getName());
                startActivity(intent);
            }
        });

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
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
