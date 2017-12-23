package uk.ac.qub.dblades01.mage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.preference.PreferenceManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SimpleFileIO implements FileIO {
    Context context;
    AssetManager assetManager;
    String externalStoragePath;

    public SimpleFileIO(Context context) {
        this.context = context;
        assetManager = context.getAssets();
        externalStoragePath = context.getExternalFilesDir(null).getAbsolutePath() + File.separator;
    }

    @Override
    public InputStream readAsset(String assetName) throws IOException {
        return assetManager.open(assetName);
    }

    @Override
    public InputStream readFile(String fileName) throws IOException {
        return new FileInputStream(externalStoragePath + fileName);
    }

    @Override
    public OutputStream writeFile(String fileName) throws IOException {
        return new FileOutputStream(externalStoragePath + fileName);
    }

    @Override
    public SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
