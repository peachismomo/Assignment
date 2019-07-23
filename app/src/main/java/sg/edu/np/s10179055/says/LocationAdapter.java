package sg.edu.np.s10179055.says;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LocationAdapter extends ArrayAdapter<Location> {
    Context c;
    int layout;
    ArrayList<Location> data;

    public LocationAdapter(Context c, int layout, ArrayList<Location> data) {
        super(c, layout, data);
        this.c = c;
        this.layout = layout;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(c).inflate(layout, parent, false);
        }
        TextView location = v.findViewById(R.id.textView);
        TextView currentNum = v.findViewById(R.id.textView2);

        Location l = data.get(position);
        location.setText(l.getLocation());
        currentNum.setText(Integer.toString(l.getCurrent()));
        return v;
    }
}
