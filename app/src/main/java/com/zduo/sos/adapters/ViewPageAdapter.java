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

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;
import com.zduo.sos.R;
import com.zduo.sos.fragments.DashboardFragment;
import com.zduo.sos.fragments.InventoryFragment;
import com.zduo.sos.fragments.OverviewFragment;
import com.zduo.sos.fragments.ProfileFragment;
import com.zduo.sos.fragments.ReportsFragment;
import com.zduo.sos.fragments.SOSFragment;

public class ViewPageAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.CustomTabProvider {

    private final int[] ICONS = {
            R.drawable.selector_icon_inactive,
            R.drawable.selector_icon_active,
            R.drawable.selector_icon_report,
            R.drawable.selector_icon_graph,
            R.drawable.selector_icon_sos,
            R.drawable.selector_icon_profile,
    };

    public ViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public void tabUnselected(View view) {
        view.setSelected(false);
    }

    @Override
    public void tabSelected(View view) {
        view.setSelected(true);
    }

    @Override
    public View getCustomTabView(ViewGroup parent, int position) {
        FrameLayout customLayout = (FrameLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_tab, parent, false);
        ((ImageView) customLayout.findViewById(R.id.image)).setImageResource(ICONS[position]);
        return customLayout;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return ICONS.length;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
            default:
                return new DashboardFragment();
            case 1:
                return new InventoryFragment();
            case 2:
                return new ReportsFragment();
            case 3:
                return new OverviewFragment();
            case 4:
                return new SOSFragment();
            case 5:
                return new ProfileFragment();
        }
    }
}