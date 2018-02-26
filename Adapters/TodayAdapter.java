package com.studyscheduler.kuubs0n.studyscheduler.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.studyscheduler.kuubs0n.studyscheduler.Models.TodayModel;
import com.studyscheduler.kuubs0n.studyscheduler.R;

/**
 * Created by Kuubs0n on 24.02.2018.
 */

public class TodayAdapter extends ArrayAdapter<TodayModel> {

    private Activity context;
    private TodayModel[] array;

    private TextView tvTime;
    private TextView tvName;
    private TextView tvPlace;
    private TextView tvProff;

    public TodayAdapter(Activity context, TodayModel[] array){
        super(context, R.layout.today_item, array);
        this.context = context;
        this.array = array;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View rowView = convertView;
        if(rowView == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            rowView = layoutInflater.inflate(R.layout.today_item, null, true);
        }

        tvTime = (TextView) rowView.findViewById(R.id.tvTime);
        tvName = (TextView) rowView.findViewById(R.id.tvName);
        tvPlace = (TextView) rowView.findViewById(R.id.tvPlace);
        tvProff = (TextView) rowView.findViewById(R.id.tvProf);

        tvTime.setText(array[position].Times);
        tvName.setText(array[position].Name);
        tvPlace.setText(array[position].Place);
        tvProff.setText(array[position].Professor);

        if(!array[position].IsLab)
            tvName.setTextColor(context.getResources().getColor(R.color.colorLab));
        else
            tvName.setTextColor(context.getResources().getColor(R.color.colorPrimary));

        return rowView;
    }

}
