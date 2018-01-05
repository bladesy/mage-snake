package uk.ac.qub.dblades01.mage.io;

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

/* A simple implementation of the FileIO interface. */
public class SimpleFileIO implements FileIO {
    Context context;
    AssetManager assetManager;
    String externalStoragePath;

    /* context provides access to assets, file locations, and preferences. */
    public SimpleFileIO(Context context) {
        this.context = context;
        assetManager = context.getAssets();
        externalStoragePath = context.getExternalFilesDir(null).getAbsolutePath() + File.separator;
    }

    /* Return an InputStream from the asset given by assetName, throwing an IOException if the asset
    cannot be opened. */
    @Override
    public InputStream readAsset(String assetName) throws IOException {
        return assetManager.open(assetName);
    }

    /* Return an InputStream from the externally stored file given by fileName, throwing an
    IOException if the file cannot be opened. */
    @Override
    public InputStream readFile(String fileName) throws IOException {
        return new FileInputStream(externalStoragePath + fileName);
    }

    /* Return an OutputStream to the externally stored file given by fileName, throwing an
    IOException if the file cannot be opened. */
    @Override
    public OutputStream writeFile(String fileName) throws IOException {
        return new FileOutputStream(externalStoragePath + fileName);
    }

    /* Return the SharedPreferences instance used by the application to store and retrieve simple
    data values. */
    @Override
    public SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
