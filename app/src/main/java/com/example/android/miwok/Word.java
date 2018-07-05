package com.example.android.miwok;

import android.content.Context;

/**
 * {@link Word} represents a vocabulary word that the user wishes to learn.
 * The object includes a default language word, its Miwok translation, possibly an image, and an audio file.
 */
public class Word {

    // String value - default
    private String mValueDefault;

    // String value - miwok
    private String mValueMiwok;

    // Int value - mp3 resource ID
    private int mAudio;

    // Int value - image
    private int mImage;

    /**
     * Constructs a new TextView with initial values for default language word, Miwok word, and audio
     */
    public Word(String defaultWord, String miwokWord, int audio) {
        mValueDefault = defaultWord;
        mValueMiwok = miwokWord;
        mAudio = audio;
    }

    /**
     * Constructs a new TextView with initial values for default language word, Miwok word, audio, and image
     */
    public Word(String defaultWord, String miwokWord, int audio, int image) {
        mValueDefault = defaultWord;
        mValueMiwok = miwokWord;
        mAudio = audio;
        mImage = image;
    }
    /**
     * Sets the values in the TextView, if the user wishes to change them for some reason.
     *
     * @param defaultWord is the word in the default language.
     * @param miwokWord is the word in Miwok.
     * @param audio is the Miwok word spoken in an audio file.
     * @param image is the logo that goes with each word.
     */
    public void setText(String defaultWord, String miwokWord, int audio, int image) {
        mValueDefault = defaultWord;
        mValueMiwok = miwokWord;
        mAudio = audio;
        mImage = image;
    }

    /**
     * Gets the default language string value in the TextView.
     *
     * @return the default language word.
     */
    public String getDefault() {
        return mValueDefault;
    }

    /**
     * Gets the Miwok language string value in the TextView.
     *
     * @return the Miwok word.
     */
    public String getMiwok() {
        return mValueMiwok;
    }

    /**
     * Gets the audio associated with the word.
     *
     * @return the audio.
     */
    public int getAudio() { return mAudio; }

    /**
     * Gets the image associated with the word.
     *
     * @return the image.
     */
    public int getImage() { return mImage; }
}
