package de.awacademy.training.android.gametemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set fullscreen mode
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // hide the screen title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // display the game canvas on the screen
        this.setContentView(new GameCanvas(this));
    }
}
