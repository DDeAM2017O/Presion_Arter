package com.example.jebus_vladimir.notimportant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jebus_vladimir.presion_arterial.R;

import java.util.ArrayList;

/**
 * Created by MSI 0ND on 11/30/2017.
 */
public class PersonListAdapter extends ArrayAdapter<Lectura> {

    private static final String TAG = "PersonListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView date, time, sys, dis;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public PersonListAdapter(Context context, int resource, ArrayList<Lectura> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //create the view result for showing the animation
        final View result;
        //ViewHolder object
        PersonListAdapter.ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new PersonListAdapter.ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.dateTV);
            holder.time = (TextView) convertView.findViewById(R.id.timeTV);
            holder.sys = (TextView) convertView.findViewById(R.id.sysTV);
            holder.dis = (TextView) convertView.findViewById(R.id.disTV);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (PersonListAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.date.setText( getItem(position).getDate() );
        holder.time.setText( getItem(position).getTime() );
        holder.sys.setText( getItem(position).getSys() );
        holder.dis.setText( getItem(position).getDis() );

        return convertView;
    }
}
