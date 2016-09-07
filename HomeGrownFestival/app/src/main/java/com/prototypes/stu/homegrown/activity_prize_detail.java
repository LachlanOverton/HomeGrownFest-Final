package com.prototypes.stu.homegrown;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class activity_prize_detail extends Activity {

    private String rowID;
    private String nameString;
    private String shortDescriptionString;
    private String longDescriptionString;
    private String vendorString;

    Context context;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prize_selected_prize);

        TextView name = (TextView) findViewById(R.id.selectedPrizeName);
        TextView shortDescription = (TextView) findViewById(R.id.selectedPrizeShortDescription);
        TextView longDescription = (TextView) findViewById(R.id.selectedPrizeLongDescription);
        TextView vendor = (TextView) findViewById(R.id.selectedPrizeVendor);
        Button claim = (Button) findViewById(R.id.selectedPrizeClaim);

        Intent intent = getIntent();
        rowID = intent.getStringExtra("ROWID");
        nameString = intent.getStringExtra("Name");
        shortDescriptionString = intent.getStringExtra("ShortDescription");
        longDescriptionString = intent.getStringExtra("LongDescription");
        vendorString = intent.getStringExtra("Vendor");

        name.setText(nameString);
        shortDescription.setText(shortDescriptionString);
        longDescription.setText(longDescriptionString);
        vendor.setText(vendorString);

        dbHelper = new DBHelper(this);


        claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Claim Prize");
                builder.setMessage("Are you sure you want to Claim this prize?\nThis prize will be deleted.").setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dbHelper.deletePrize(Integer.parseInt(rowID));
                                finish();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent myIntent = new Intent(this, activity_prize_list.class);
        startActivity(myIntent);
    }
}
