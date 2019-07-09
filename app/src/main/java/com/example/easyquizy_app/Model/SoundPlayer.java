package com.example.easyquizy_app.Model;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.example.easyquizy_app.R;

public class SoundPlayer {

    private static SoundPool soundPool;
    private static int heartBreakSound;
    private static int failSound;
    private static int applauseSound;

    public SoundPlayer(Context context) {
        //SoundPool(int maxStreams, int streamType, int srcQuality)
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);

        heartBreakSound = soundPool.load(context, R.raw.heart_break_sound, 1);
        failSound = soundPool.load(context, R.raw.fail_sound, 1);
        applauseSound = soundPool.load(context, R.raw.applause_sound, 1);

    }

    public void playHeartBreakSound()
    {
        //play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        soundPool.play(heartBreakSound, 0.7f, 0.7f, 1, 0, 1.0f);
    }
    public void playFailSound()
    {
        //play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        soundPool.play(failSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    public void playApplauseSound()
    {
        //play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        soundPool.play(applauseSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
}
