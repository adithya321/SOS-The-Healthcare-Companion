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

package com.zduo.sos.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zduo.sos.R;
import com.zduo.sos.activities.ViewActivity;
import com.zduo.sos.models.Reminder;
import com.zduo.sos.utils.DateAndTimeUtil;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {

    private Context context;
    private List<Reminder> reminderList;

    public ReminderAdapter(Context context, List<Reminder> reminderList) {
        this.context = context;
        this.reminderList = reminderList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notification_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        Reminder reminder = reminderList.get(position);

        Calendar calendar = DateAndTimeUtil.parseDateAndTime(reminder.getDateAndTime());

        viewHolder.title.setText(reminder.getTitle());
        viewHolder.content.setText(reminder.getContent());
        viewHolder.time.setText(DateAndTimeUtil.toStringReadableTime(calendar, context));
        viewHolder.quantity.setText(context.getResources().getString(R.string.quantity_show, reminder.getQuantity()));
        int iconResId = context.getResources().getIdentifier(reminder.getIcon(), "drawable", context.getPackageName());
        viewHolder.icon.setImageResource(iconResId);
        GradientDrawable bgShape = (GradientDrawable) viewHolder.circle.getDrawable();
        bgShape.setColor(Color.parseColor(reminder.getColour()));

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewActivity.class);
                intent.putExtra("NOTIFICATION_ID", reminderList.get(viewHolder.getAdapterPosition()).getId());

                // Add shared element transition animation if on Lollipop or later
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    CardView cardView = (CardView) view.findViewById(R.id.notification_card);

                    TransitionSet setExit = new TransitionSet();
                    Transition transition = new Fade();
                    transition.excludeTarget(android.R.id.statusBarBackground, true);
                    transition.excludeTarget(android.R.id.navigationBarBackground, true);
                    transition.excludeTarget(R.id.fab_button, true);
                    transition.excludeTarget(R.id.recycler_view, true);
                    transition.setDuration(400);
                    setExit.addTransition(transition);

                    ((Activity) context).getWindow().setSharedElementsUseOverlay(false);
                    ((Activity) context).getWindow().setReenterTransition(null);

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(((Activity) context), cardView, "cardTransition");
                    ActivityCompat.startActivity(((Activity) context), intent, options.toBundle());

                    ((RecyclerListener) context).hideFab();
                } else {
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    public interface RecyclerListener {
        void hideFab();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.notification_title)
        TextView title;
        @BindView(R.id.notification_time)
        TextView time;
        @BindView(R.id.notification_content)
        TextView content;
        @BindView(R.id.notification_quantity)
        TextView quantity;
        @BindView(R.id.header_separator)
        TextView textSeparator;
        @BindView(R.id.notification_icon)
        ImageView icon;
        @BindView(R.id.notification_circle)
        ImageView circle;
        private View view;

        public ViewHolder(final View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }
}