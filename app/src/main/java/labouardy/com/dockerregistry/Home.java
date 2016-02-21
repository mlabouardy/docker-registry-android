package labouardy.com.dockerregistry;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import labouardy.com.dockerregistry.adapter.RegistryAdapter;
import labouardy.com.dockerregistry.handler.Storage;
import labouardy.com.dockerregistry.model.Account;
import labouardy.com.dockerregistry.model.Registry;
import labouardy.com.dockerregistry.model.RegistrySingleton;


public class Home extends ActionBarActivity {
    private ListView lv;
    private RegistryAdapter adapter;
    private Account account=Account.getInstance();
    private Timer timer=new Timer();
    private Storage storage= Storage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        changeColor();
        init();
    }


    public void init(){
        Account l= null;
        try {
            l = (Account)storage.readObject(this, "DOCKER_REGISTRY");
            if(l!=null)
                account=l;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        lv=(ListView)findViewById(R.id.registries);
        adapter=new RegistryAdapter(this, account.getRegistries());
        lv.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.create);
        fab.attachToListView(lv);
        fab.setColorNormal(getResources().getColor(R.color.first));
        fab.setColorPressed(getResources().getColor(R.color.primary));
        fab.setColorRipple(getResources().getColor(R.color.secondary));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Register.class);
                startActivity(intent);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Home.this, Repositories.class);
                Registry r = account.getRegistries().get(i);
                Registry rs = RegistrySingleton.getInstance();
                rs.setHostname(r.getHostname());
                rs.setPort(r.getPort());
                rs.setVersion(r.getVersion());
                rs.setAuth(r.getAuth());
                startActivity(intent);
            }
        });

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        },1000,10000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
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


        return super.onOptionsItemSelected(item);
    }
}
