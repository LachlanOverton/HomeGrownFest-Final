package com.prototypes.stu.homegrown;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PrizeAdapter extends ArrayAdapter<Prize> {

    /*
        The PrizeAdapter controls the activity_prize_prize_list.xml layout
        All items in this PrizeAdapter should relate to the activity stated above
     */

    Context context;
    int layoutResourceId;
    Prize[] data;

    public PrizeAdapter(Context context, int layoutResourceId, Prize[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PrizeHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new PrizeHolder();
            holder.name = (TextView) row.findViewById(R.id.prizeList_prizeTitle);
            holder.description = (TextView) row.findViewById(R.id.prizeList_prizeDescription);
            holder.icon = (ImageView) row.findViewById(R.id.prizeList_prizeIcon);

            row.setTag(holder);
        } else {
            holder = (PrizeHolder) row.getTag();
        }

        Prize prize = data[position];
        holder.rowID = Integer.toString(prize.getId()); // Prize ID
        holder.name.setText(prize.getName());
        holder.description.setText(prize.getShortDescription());
        holder.icon.setImageResource(R.drawable.ic_launcher);

        holder.titleString = prize.getName();
        holder.shortDescriptionString = prize.getShortDescription();
        holder.longDescriptionString = prize.getLongDescription();
        holder.vendorString = prize.getVendor();

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrizeHolder p = (PrizeHolder) view.getTag();

                Intent myIntent = new Intent(context, activity_prize_detail.class);
                myIntent.putExtra("ROWID", p.rowID); // Adds the RowID to the Intent
                myIntent.putExtra("Name", p.titleString);
                myIntent.putExtra("ShortDescription", p.shortDescriptionString);
                myIntent.putExtra("LongDescription", p.longDescriptionString);
                myIntent.putExtra("Vendor", p.vendorString);
                context.startActivity(myIntent); // Starts the activity
                ((Activity) context).finish(); // Deletes the old activity so the list will be refreshed
            }
        });

        return row;
    }

    static class PrizeHolder {
        TextView name;
        TextView description;
        ImageView icon;

        String rowID;
        String titleString;
        String shortDescriptionString;
        String longDescriptionString;
        String vendorString;
    }
}