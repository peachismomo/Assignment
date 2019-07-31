package sg.edu.np.s10179055.says;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LocationAdapter extends ArrayAdapter<Location> {
    Context c;
    int layout;
    int num;
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

        //Set the location and current number of people to be displayed
        Location l = data.get(position);
        location.setText(l.getLocation());
        currentNum.setText(Integer.toString(l.getCurrent()));

        // Color-Based Classification for the number of people at each canteen
        // Red , Yellow & Green representing Hard, Medium and Easy difficulty of finding seats
        if (l.getLocation() == "Makanplace") {
            if (l.getCurrent() >= 500) {
                currentNum.setTextColor(Color.parseColor("#ff4d4d"));
            } else if (l.getCurrent() < 500 && l.getCurrent() >= 300)
                currentNum.setTextColor(Color.parseColor("#FFFF00"));
            else
                currentNum.setTextColor(Color.parseColor("#00FF7F"));
        } else if (l.getLocation() == "Foodclub") {
            if (l.getCurrent() >= 400) {
                currentNum.setTextColor(Color.parseColor("#ff4d4d"));
            } else if (l.getCurrent() < 400 && l.getCurrent() >= 250)
                currentNum.setTextColor(Color.parseColor("#FFFF00"));
            else
                currentNum.setTextColor(Color.parseColor("#00FF7F"));
        } else if (l.getLocation() == "Munch") {
            if (l.getCurrent() >= 300) {
                currentNum.setTextColor(Color.parseColor("#ff4d4d"));
            } else if (l.getCurrent() < 300 && l.getCurrent() >= 200)
                currentNum.setTextColor(Color.parseColor("#FFFF00"));
            else
                currentNum.setTextColor(Color.parseColor("#00FF7F"));
        } else {
            if (l.getCurrent() >= 250) {
                currentNum.setTextColor(Color.parseColor("#ff4d4d"));
            } else if (l.getCurrent() < 250 && l.getCurrent() >= 150)
                currentNum.setTextColor(Color.parseColor("#FFFF00"));
            else
                currentNum.setTextColor(Color.parseColor("#00FF7F"));
        }

        return v;
    }
}
