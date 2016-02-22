package labouardy.com.dockerregistry.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.List;

import labouardy.com.dockerregistry.Home;
import labouardy.com.dockerregistry.R;
import labouardy.com.dockerregistry.model.Registry;
import labouardy.com.dockerregistry.util.Timesince;

/**
 * Created by mlabouardy on 20/02/16.
 */
public class RegistryAdapter extends BaseSwipeAdapter {
    private Activity activity;
    private List<Registry> registries;
    private Timesince timesince=Timesince.getInstance();

    public RegistryAdapter(Activity activity, List<Registry> registries){
        this.activity=activity;
        this.registries=registries;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        View v;
        v = LayoutInflater.from(activity).inflate(R.layout.registry, null);
        v.findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registries.remove(position);
                ((Home)activity).removeNotify();
            }
        });
        return v;
    }

    @Override
    public void fillValues(int position, View view) {
        Registry registry=registries.get(position);

        TextView nameTV=(TextView)view.findViewById(R.id.registryTV);
        TextView versionTV=(TextView)view.findViewById(R.id.versionTV);
        TextView timeTV=(TextView)view.findViewById(R.id.timeTV);

        nameTV.setText(registry.getHostname()+":"+registry.getPort());
        versionTV.setText(registry.getVersion());
        timeTV.setText(timesince.calculateSince(registry.getCreationDate()));
    }

    @Override
    public int getCount() {
        return registries.size();
    }

    @Override
    public Object getItem(int position) {
        return registries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
