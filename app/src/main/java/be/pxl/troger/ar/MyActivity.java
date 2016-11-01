package be.pxl.troger.ar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import be.pxl.troger.ar.tools.CameraManager;
import be.pxl.troger.ar.views.CameraPreviewView;

/**
 * main activity, responsible for loading the layout and its views
 * @author Michael Troger
 */
public class MyActivity extends AppCompatActivity {
    /**
     * class name for debugging with logcat
     */
    private static final String TAG = MyActivity.class.getName();
    /**
     * the SurfaceView holding the current picture of the camera
     */
    private CameraPreviewView mPreview;
    /**
     * the camera manager is responsible for getting an instance of the camera
     * and releasing it correctly
     */
    private CameraManager mCameraManager;
    /**
     * for detecting finishing other activity (settings)
     */
    private static final int RESULT_SETTINGS = 1;

    private boolean takePicture;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        // get a camera manager instance
        mCameraManager = new CameraManager(this);

        // Create the preview view
        mPreview = new CameraPreviewView(this, mCameraManager.getCamera());
        // init the camera preview with the necessary parameters so that
        // it knows which area of the camera picture is to analyze

        loadSettings();
        mPreview.loadSettings(takePicture);

        // add the live picture of the camera to the prepared FrameLayout
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        Log.d(TAG, "started :)");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.preferences: {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivityForResult(intent, RESULT_SETTINGS);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
    /**
     * loads user settings which are customizable via gui
     */
    private void loadSettings() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        takePicture = prefs.getBoolean("pref_key_takepicture", true);
        Log.d(TAG, "take picture setting changed: " + takePicture);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SETTINGS:
                loadSettings();
                mPreview.loadSettings(takePicture);
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        mPreview.onPause();
        mCameraManager.onPause();

        mPreview.setVisibility(View.GONE); // this to fix freeze.
    }


    @Override
    protected void onResume() {
        super.onResume();
        mCameraManager.onResume();

        mPreview.setCamera(mCameraManager.getCamera());

        mPreview.setVisibility(View.VISIBLE); // this can fix the freeze.
    }

}