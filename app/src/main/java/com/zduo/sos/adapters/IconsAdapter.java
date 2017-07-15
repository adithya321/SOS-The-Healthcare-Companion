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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zduo.sos.R;
import com.zduo.sos.database.DatabaseHelper;
import com.zduo.sos.dialogs.IconPicker;
import com.zduo.sos.models.Icon;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IconsAdapter extends RecyclerView.Adapter<IconsAdapter.ViewHolder> {

    private IconPicker iconPicker;
    private List<Icon> iconList;

    public IconsAdapter(IconPicker iconPicker, List<Icon> iconList) {
        this.iconPicker = iconPicker;
        this.iconList = iconList;
    }

    @Override
    public int getItemCount() {
        return iconList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_icon_grid, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        final String iconName = iconList.get(position).getName();
        final int iconResId = viewHolder.view.getContext().getResources().getIdentifier(iconName, "drawable", viewHolder.view.getContext().getPackageName());
        viewHolder.imageView.setImageResource(iconResId);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper database = DatabaseHelper.getInstance(viewHolder.view.getContext());
                iconList.get(viewHolder.getAdapterPosition()).setUseFrequency(iconList.get(viewHolder.getAdapterPosition()).getUseFrequency() + 1);
                database.updateIcon(iconList.get(viewHolder.getAdapterPosition()));
                database.close();

                String name;
                if (!iconName.equals(viewHolder.view.getContext().getString(R.string.default_icon_value))) {
                    name = viewHolder.view.getContext().getString(R.string.custom_icon);
                } else {
                    name = viewHolder.view.getContext().getResources().getString(R.string.default_icon);
                }

                ((IconPicker.IconSelectionListener) viewHolder.view.getContext()).onIconSelection(iconPicker, iconName, name, iconResId);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.icon)
        ImageView imageView;
        private View view;

        public ViewHolder(final View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }
}