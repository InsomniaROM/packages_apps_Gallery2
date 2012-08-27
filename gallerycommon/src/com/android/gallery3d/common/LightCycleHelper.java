/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.gallery3d.common;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

public class LightCycleHelper {
    public static final String LIGHTCYCLE_PACKAGE =
            "com.google.android.apps.lightcycle";
    public static final String LIGHTCYCLE_CAPTURE_CLASS =
            "com.google.android.apps.lightcycle.PanoramaCaptureActivity";
    public static final String LIGHTCYCLE_VIEW_CLASS =
            "com.google.android.apps.lightcycle.PanoramaViewActivity";
    public static final String EXTRA_OUTPUT_DIR = "output_dir";

    private static boolean sUpdated;
    private static boolean sHasViewActivity;
    private static boolean sHasCaptureActivity;

    private static boolean hasLightCycleActivity(PackageManager pm, String activityClass) {
        Intent it = new Intent();
        it.setClassName(LIGHTCYCLE_PACKAGE, activityClass);
        return (pm.resolveActivity(it, 0) != null);
    }

    private static void update(PackageManager pm) {
        sUpdated = true;
        sHasViewActivity = hasLightCycleActivity(pm, LIGHTCYCLE_VIEW_CLASS);
        sHasCaptureActivity = hasLightCycleActivity(pm, LIGHTCYCLE_CAPTURE_CLASS);
    }

    public synchronized static boolean hasLightCycleView(PackageManager pm) {
        if (!sUpdated) {
            update(pm);
        }
        return sHasViewActivity;
    }

    public synchronized static boolean hasLightCycleCapture(PackageManager pm) {
        if (!sUpdated) {
            update(pm);
        }
        return sHasCaptureActivity;
    }

    public synchronized static void onPackageAdded(Context context, String packageName) {
        if (LIGHTCYCLE_PACKAGE.equals(packageName)) {
            update(context.getPackageManager());
        }
    }

    public synchronized static void onPackageRemoved(Context context, String packageName) {
        if (LIGHTCYCLE_PACKAGE.equals(packageName)) {
            update(context.getPackageManager());
        }
    }

    public synchronized static void onPackageChanged(Context context, String packageName) {
        if (LIGHTCYCLE_PACKAGE.equals(packageName)) {
            update(context.getPackageManager());
        }
    }
}
