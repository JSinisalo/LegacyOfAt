package com.hert.legacyofat.game;

import android.text.SpannableStringBuilder;

import com.hert.legacyofat.backend.Chara;

import java.util.List;

/**
 * Interface for listening the gamemasters demands.
 */
public interface GameListener {

    /**
     * Push text.
     *
     * @param string the string
     */
    void pushText(String string);

    /**
     * Push text.
     *
     * @param builder the builder
     */
    void pushText(SpannableStringBuilder builder);

    /**
     * Update health bars.
     *
     * @param list the list
     */
    void updateHealthBars(List<Chara> list);

    /**
     * Kill.
     *
     * @param i the
     */
    void kill(int i);

    /**
     * Win.
     */
    void win();

    /**
     * Lose.
     */
    void lose();
}
