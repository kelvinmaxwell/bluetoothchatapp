package com.modcom.bluetoothchatapp;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.fragment.app.FragmentTransaction;



/**
 * A simple launcher activity containing a summary sample description, sample log and a custom

 * <p>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
public class MainActivity extends SampleActivityBase
        implements BluetoothChatFragment.UpdateDataMonitor {

    public static final String TAG = "MainActivity";

    // Whether the Log Fragment is currently shown
    private boolean mLogShown;

    BluetoothChatFragment m_fragment1;
    DataMonitorFragment m_fragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            m_fragment1 = new BluetoothChatFragment();
            transaction.replace(R.id.sample_content_fragment, m_fragment1);
            transaction.commit();
            FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
            m_fragment2 = new DataMonitorFragment();
            transaction2.replace(R.id.sample_content_fragment2, m_fragment2);
            transaction2.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
/*        MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);
        logToggle.setVisible(findViewById(R.id.sample_output) instanceof ViewAnimator);
        logToggle.setTitle(mLogShown ? R.string.sample_hide_log : R.string.sample_show_log);
*/
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
/*            case R.id.menu_toggle_log:
                mLogShown = !mLogShown;
                ViewAnimator output = (ViewAnimator) findViewById(R.id.sample_output);
                if (mLogShown) {
                    output.setDisplayedChild(1);
                } else {
                    output.setDisplayedChild(0);
                }
                supportInvalidateOptionsMenu();
                return true;
*/        }
        return super.onOptionsItemSelected(item);
    }

    /** Create a chain of targets that will receive log data */
    @Override
    public void initializeLogging() {
        // Wraps Android's native log framework.
        LogWrapper logWrapper = new LogWrapper();
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        Log.setLogNode(logWrapper);

        // Filter strips out everything except the message text.
        MessageOnlyLogFilter msgFilter = new MessageOnlyLogFilter();
        logWrapper.setNext(msgFilter);

        // On screen logging via a fragment with a TextView.
        LogFragment logFragment = (LogFragment) getSupportFragmentManager()
                .findFragmentById(R.id.log_fragment);
        msgFilter.setNext(logFragment.getLogView());

        Log.i(TAG, "Ready");
    }

    public void UpdateDataMonitorToActivity(BluetoothChatFragment.CvtDataDump cvtDataDump) {
        m_fragment2.setData(cvtDataDump);
    }

    public void ShowDataMonitorToActivity (boolean bShowDataMonitor) {
        if (bShowDataMonitor) {
            findViewById(R.id.relativeLayout1).setVisibility(View.GONE);
            findViewById(R.id.relativeLayout2).setVisibility(View.VISIBLE);
            //findViewById(R.id.relativeLayout1).getLayoutParams().height = 1; // Works but not needed since View.GONE works ok
        }
        else {
            findViewById(R.id.relativeLayout2).setVisibility(View.GONE);
            findViewById(R.id.relativeLayout1).setVisibility(View.VISIBLE);
        }
    }
}
