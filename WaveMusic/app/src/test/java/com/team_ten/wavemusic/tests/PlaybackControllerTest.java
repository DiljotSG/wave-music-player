package com.team_ten.wavemusic.tests;

import com.team_ten.wavemusic.logic.PlaybackController;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class PlaybackControllerTest {

    @BeforeClass
    public static void setUpClass() {
        PlaybackController.init_for_testing();
    }

    @AfterClass
    public static void tearDownClass() {
        ;
    }

    @Test
    public void loopmode_toggle_moves_to_correct_state() {
        int inital_mode = PlaybackController.get_playback_state_num();
        PlaybackController.toggleLoopMode();
        int final_mode = PlaybackController.get_playback_state_num();

        // test that
        if (inital_mode < PlaybackController.get_num_playback_states() - 1)
            assertEquals(1, final_mode - inital_mode);
        else
            assertEquals(0, final_mode);
    }
}