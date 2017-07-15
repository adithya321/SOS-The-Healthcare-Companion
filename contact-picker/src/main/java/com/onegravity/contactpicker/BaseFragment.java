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

package com.onegravity.contactpicker;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Locale;

/**
 * Super class of ContactFragment to take care of common tasks.
 */
public abstract class BaseFragment extends Fragment implements SearchView.OnQueryTextListener {

    private String[] mQueryStrings;

    private View mRootLayout;
    private RecyclerView mRecyclerView;
    private View mFastScroll;
    private View mSectionIndex;
    private View mEmptyView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        if (savedInstanceState != null) {
            mQueryStrings = (String[]) savedInstanceState.getSerializable("mQueryStrings");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("mQueryStrings", mQueryStrings);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);
    }

    protected final View createView(LayoutInflater inflater, int layoutId,
                                    RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter,
                                    List<? extends ContactElement> elements) {
        mRootLayout = inflater.inflate(layoutId, null);
        mRecyclerView = (RecyclerView) mRootLayout.findViewById(android.R.id.list);
        mEmptyView = mRootLayout.findViewById(android.R.id.empty);

        // use a LinearLayout for the RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(adapter);

        updateEmptyViewVisibility(elements);

        return mRootLayout;
    }

    protected void updateEmptyViewVisibility(List<? extends ContactElement> elements) {
        boolean isEmpty = elements == null || elements.isEmpty();
        mRecyclerView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        if (mFastScroll != null) {
            mFastScroll.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        }
        if (mSectionIndex != null) {
            mSectionIndex.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        }
        mEmptyView.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();

        EventBus.getDefault().register(this);

        if (mQueryStrings != null && mQueryStrings.length > 0) {
            performFiltering(mQueryStrings);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    final public boolean onQueryTextSubmit(String query) {
        return onQuery(query);
    }

    @Override
    final public boolean onQueryTextChange(String query) {
        return onQuery(query);
    }

    private boolean onQuery(String query) {
        String queryString = query.toString().toLowerCase(Locale.getDefault());
        mQueryStrings = queryString.split(" ");
        performFiltering(mQueryStrings);
        return true;
    }

    /**
     * Filter the data using the query strings.
     */
    abstract protected void performFiltering(String[] queryStrings);

}