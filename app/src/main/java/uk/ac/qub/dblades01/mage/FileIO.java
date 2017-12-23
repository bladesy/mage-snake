package uk.ac.qub.dblades01.mage;

import android.content.SharedPreferences;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface FileIO {
    InputStream readAsset(String assetName) throws IOException;
    InputStream readFile(String fileName) throws IOException;
    OutputStream writeFile(String fileName) throws IOException;
    SharedPreferences getPreferences();
}
