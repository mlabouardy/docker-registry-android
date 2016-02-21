package labouardy.com.dockerregistry.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import labouardy.com.dockerregistry.R;
import labouardy.com.dockerregistry.model.Repository;
import labouardy.com.dockerregistry.model.Tag;

/**
 * Created by mlabouardy on 21/02/16.
 */
public class TagAdapter extends BaseAdapter {
    private Activity activity;
    private List<Tag> tags;


    public TagAdapter(Activity activity, List<Tag> tags) {
        this.activity = activity;
        this.tags = tags;
    }

    @Override
    public int getCount() {
        return tags.size();
    }

    @Override
    public Object getItem(int i) {
        return tags.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater mInflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = mInflater.inflate(R.layout.tag, null);

        Tag tag=(Tag)getItem(i);

        TextView nameTV=(TextView)view.findViewById(R.id.tagTV);

        nameTV.setText(tag.getTag());

        return view;
    }
}
