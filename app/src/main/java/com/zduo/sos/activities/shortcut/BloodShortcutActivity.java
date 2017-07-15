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

package com.zduo.sos.activities.shortcut;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.zduo.sos.R;

public class BloodShortcutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent();
        Intent shortcutActivity = new Intent(this, BloodShortcut.class);
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutActivity);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.fab_blood));
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(this, R.drawable.shortcut_blood));
        setResult(RESULT_OK, intent);

        finish();
    }
}