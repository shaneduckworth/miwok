package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

    public MediaPlayer mp;
    private AudioManager am;
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        mp = new MediaPlayer();
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Initialize numbers array
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("father","әpә", R.raw.family_father, R.drawable.family_father));
        words.add(new Word("mother","әṭa", R.raw.family_mother, R.drawable.family_mother));
        words.add(new Word("son","angsi", R.raw.family_son, R.drawable.family_son));
        words.add(new Word("daughter","tune", R.raw.family_daughter, R.drawable.family_daughter));
        words.add(new Word("older brother","taachi", R.raw.family_older_brother, R.drawable.family_older_brother));
        words.add(new Word("younger brother","chalitti", R.raw.family_younger_brother, R.drawable.family_younger_brother));
        words.add(new Word("older sister","teṭe", R.raw.family_older_sister, R.drawable.family_older_sister));
        words.add(new Word("younger sister","kolliti", R.raw.family_younger_sister, R.drawable.family_younger_sister));
        words.add(new Word("grandmother","ama", R.raw.family_grandmother, R.drawable.family_grandmother));
        words.add(new Word("grandfather","paapa", R.raw.family_grandfather, R.drawable.family_grandfather));

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_family);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int audio = words.get(position).getAudio();
                releaseMediaPlayer();

                int res = am.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mp = MediaPlayer.create(getApplicationContext(), audio); // creates new mediaplayer with song.
                    mp.start();
                    mp.setOnCompletionListener(mCompletionListener);
                }
            }
        });

    }

    private void releaseMediaPlayer() {
        if(mp != null) {
            mp.release();
            mp = null;
            am.abandonAudioFocus(afChangeListener);
        }
    }

    private AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // Permanent loss of audio focus
                releaseMediaPlayer();
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                // Pause playback
                mp.pause();
                mp.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // Lower the volume, keep playing
                //mp.setVolume(0.2, 0.2);
                mp.pause();
                mp.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // Your app has been granted audio focus again
                // Raise volume to normal, restart playback if necessary
                mp.start();
            }
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}

