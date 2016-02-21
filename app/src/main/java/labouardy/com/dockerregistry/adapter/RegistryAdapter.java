package labouardy.com.dockerregistry.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import labouardy.com.dockerregistry.R;
import labouardy.com.dockerregistry.model.Registry;

/**
 * Created by mlabouardy on 20/02/16.
 */
public class RegistryAdapter extends BaseAdapter {
    private Activity activity;
    private List<Registry> registries;

    public RegistryAdapter(Activity activity, List<Registry> registries){
        this.activity=activity;
        this.registries=registries;
    }

    @Override
    public int getCount() {
        return registries.size();
    }

    @Override
    public Object getItem(int i) {
        return registries.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater mInflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = mInflater.inflate(R.layout.registry, null);

        Registry registry=registries.get(i);

        TextView nameTV=(TextView)view.findViewById(R.id.registryTV);
        TextView versionTV=(TextView)view.findViewById(R.id.versionTV);

        nameTV.setText(registry.getHostname()+":"+registry.getPort());
        versionTV.setText(registry.getVersion());

        return view;
    }
}
