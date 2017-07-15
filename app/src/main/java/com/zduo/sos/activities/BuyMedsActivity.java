/*
 * SOS
 * Copyright (C) 2016  zDuo (Adithya J, Vazbloke)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.zduo.sos.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zduo.sos.R;
import com.zduo.sos.adapters.BuyMedsAdapter;
import com.zduo.sos.database.DatabaseHelper;
import com.zduo.sos.models.Reminder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BuyMedsActivity extends AppCompatActivity {

    @BindView(R.id.buy_meds_recycler_view)
    RecyclerView buyMedsRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.header)
    LinearLayout header;
    @BindView(R.id.toolbar_shadow)
    View toolbarShadow;
    @BindView(R.id.fire_circle)
    ImageView fireCircle;
    @BindView(R.id.directions_card)
    CardView directionsCard;
    @BindView(R.id.buy_button)
    Button buyButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_meds);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        buyMedsRecyclerView.setLayoutManager(layoutManager);

        DatabaseHelper database = DatabaseHelper.getInstance(this);
        List<Reminder> reminderList = database.getNotificationList(Reminder.INACTIVE);
        database.close();
        BuyMedsAdapter buyMedsAdapter = new BuyMedsAdapter(this, reminderList);
        buyMedsRecyclerView.setAdapter(buyMedsAdapter);

        directionsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=pharmacies");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void buyMedsOnClick(View view) {
        Toast.makeText(this, "Your request has been sent successfully", Toast.LENGTH_SHORT).show();
    }
}
