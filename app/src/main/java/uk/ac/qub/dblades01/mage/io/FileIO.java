package uk.ac.qub.dblades01.mage.io;

import android.content.SharedPreferences;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* Describes a class that provides read access to assets, read and write access to files, and access
to the application preferences to store and retrieve simple data values. */
public interface FileIO {
    InputStream readAsset(String assetName) throws IOException;
    InputStream readFile(String fileName) throws IOException;
    OutputStream writeFile(String fileName) throws IOException;
    SharedPreferences getPreferences();
}
