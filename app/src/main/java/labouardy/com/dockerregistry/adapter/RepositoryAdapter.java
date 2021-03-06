package labouardy.com.dockerregistry.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import labouardy.com.dockerregistry.R;
import labouardy.com.dockerregistry.model.Image;
import labouardy.com.dockerregistry.model.Repository;

/**
 * Created by mlabouardy on 21/02/16.
 */
public class RepositoryAdapter extends BaseAdapter {
    private Activity activity;
    private List<Repository> repositories;


    public RepositoryAdapter(Activity activity, List<Repository> repositories) {
        this.activity = activity;
        this.repositories = repositories;
    }

    @Override
    public int getCount() {
        return repositories.size();
    }

    @Override
    public Object getItem(int i) {
        return repositories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater mInflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = mInflater.inflate(R.layout.repository, null);

        Repository repository=(Repository)getItem(i);

        TextView nameTV=(TextView)view.findViewById(R.id.repositoryTV);

        nameTV.setText(repository.getName());

        return view;
    }
}
