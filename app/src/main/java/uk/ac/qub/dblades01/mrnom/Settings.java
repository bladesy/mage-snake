package uk.ac.qub.dblades01.mrnom;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import uk.ac.qub.dblades01.mage.io.FileIO;

/* Stores the user settings and scores, along with being responsible for updating these settings and
scores. */
public class Settings {
    public static boolean soundEnabled = true;
    public static int[] highscores = { 100, 80, 50, 30, 10 };

    /* Load in previously saved settings through fileIO. */
    public static void load(FileIO fileIO) {
        BufferedReader reader;

        reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(fileIO.readFile(".mrnom")));
            soundEnabled = Boolean.parseBoolean(reader.readLine());

            for(int i = 0; i < 5; ++i)
                highscores[i] = Integer.parseInt(reader.readLine());
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        finally {
            if(reader != null) {
                try {
                    reader.close();
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* Save the current settings through fileIO. */
    public static void save(FileIO fileIO) {
        BufferedWriter writer;

        writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(fileIO.writeFile(".mrnom")));
            writer.write(Boolean.toString(soundEnabled));

            for(int i = 0; i < 5; ++i) {
                writer.newLine();
                writer.write(Integer.toString(highscores[i]));
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        finally {
            if(writer != null) {
                try {
                    writer.close();
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* Attempt to add score to the highscores array. */
    public static void addScore(int score) {
        for(int i = 0; i < 5; ++i)
            if(score > highscores[i]) {
                for(int j = 3; j >= i; --j)
                    highscores[j + 1] = highscores[j];

                highscores[i] = score;
                break;
            }
    }
}
