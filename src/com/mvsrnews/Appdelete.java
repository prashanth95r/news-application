package com.mvsrnews;

import java.io.File;

import android.app.Application;
import android.content.Context;

public class Appdelete extends Application {
    private static Appdelete instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Appdelete getInstance() {
        return instance;
    }

    public void clearApplicationData(Context context) {
        File cache = context.getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                    }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }
}
