package labouardy.com.dockerregistry;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.IOException;
import java.util.Date;

import labouardy.com.dockerregistry.dialog.Dialog;
import labouardy.com.dockerregistry.dialog.ErrorDialog;
import labouardy.com.dockerregistry.dialog.SuccessDialog;
import labouardy.com.dockerregistry.handler.Storage;
import labouardy.com.dockerregistry.model.Account;
import labouardy.com.dockerregistry.model.Registry;


public class Register extends ActionBarActivity {
    private EditText hostnameET, portET, usernameET, passwordET;
    private Spinner versionSpinner;
    private Button createBtn;
    private Account account=Account.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        changeColor();

        init();
    }

    public void init(){
        hostnameET=(EditText)findViewById(R.id.hostnameET);
        portET=(EditText)findViewById(R.id.portET);
        usernameET=(EditText)findViewById(R.id.usernameET);
        passwordET=(EditText)findViewById(R.id.passwordET);
        versionSpinner=(Spinner)findViewById(R.id.versionSpinner);
        createBtn=(Button)findViewById(R.id.createBtn);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hostname=hostnameET.getText().toString().trim();
                Integer port=new Integer(portET.getText().toString());
                String version=versionSpinner.getSelectedItem().toString();

                Registry registry=new Registry();
                registry.setHostname(hostname);
                registry.setPort(port);
                registry.setVersion(version);
                registry.setCreationDate(new Date());

                if(account.alreadyExist(registry)){
                    Dialog dialog=new ErrorDialog(Register.this);
                    dialog.show("Registry already exists !");
                }else{
                    account.add(registry);
                    Storage storage= Storage.getInstance();
                    try {
                        storage.writeObject(Register.this, "DOCKER_REGISTRY", account);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    finish();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
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
