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

public class ColorsActivity extends AppCompatActivity {

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
        words.add(new Word("red","weṭeṭṭi", R.raw.color_red, R.drawable.color_red));
        words.add(new Word("green","chokokki", R.raw.color_green, R.drawable.color_green));
        words.add(new Word("brown","ṭakaakki", R.raw.color_brown, R.drawable.color_brown));
        words.add(new Word("grey","ṭopoppi", R.raw.color_gray, R.drawable.color_gray));
        words.add(new Word("black","kululli", R.raw.color_black, R.drawable.color_black));
        words.add(new Word("white","kelelli", R.raw.color_white, R.drawable.color_white));
        words.add(new Word("dusty yellow","ṭopiisә", R.raw.color_dusty_yellow, R.drawable.color_dusty_yellow));
        words.add(new Word("mustard yellow","chiwiiṭә", R.raw.color_mustard_yellow, R.drawable.color_mustard_yellow));

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_colors);
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

