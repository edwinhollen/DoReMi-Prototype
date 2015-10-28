package edu.edwinhollen.doremi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Edwin on 10/26/15
 */
public class Notes{
    private static Map<String, Sound> loadedSounds = new HashMap<>();
    public static void play(final Note n){
        final String path = String.format("instruments/piano/%s.mp3", n.toString());
        if(loadedSounds.containsKey(path)){
            loadedSounds.get(path).play();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadedSounds.put(path, Gdx.audio.newSound(Gdx.files.internal(path)));
                loadedSounds.get(path).play();
            }
        }).start();
    }

    public static void dispose() {
        for(Sound s : loadedSounds.values()){
            s.dispose();
        }
        loadedSounds.clear();
    }
}
