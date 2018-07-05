package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

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
        words.add(new Word("one","lutti", R.raw.number_one, R.drawable.number_one));
        words.add(new Word("two","otiiko", R.raw.number_two, R.drawable.number_two));
        words.add(new Word("three","tolookosu", R.raw.number_three, R.drawable.number_three));
        words.add(new Word("four","oyyisa", R.raw.number_four, R.drawable.number_four));
        words.add(new Word("five","massokka", R.raw.number_five, R.drawable.number_five));
        words.add(new Word("six","temmokka", R.raw.number_six, R.drawable.number_six));
        words.add(new Word("seven","kenekaku", R.raw.number_seven, R.drawable.number_seven));
        words.add(new Word("eight","kawinta", R.raw.number_eight, R.drawable.number_eight));
        words.add(new Word("nine","wo'e", R.raw.number_nine, R.drawable.number_nine));
        words.add(new Word("ten","na'aacha", R.raw.number_ten, R.drawable.number_ten));

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_numbers);
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

