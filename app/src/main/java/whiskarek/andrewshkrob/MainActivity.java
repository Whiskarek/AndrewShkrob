package whiskarek.andrewshkrob;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final Button btChangePos = (Button) findViewById(R.id.btChangePos);
        final TextView tvToReplace = (TextView) findViewById(R.id.tvToReplace);
        final RelativeLayout rlWithText = (RelativeLayout) findViewById(R.id.rlWithText);


        btChangePos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rlWithTextWidth = rlWithText.getWidth();
                int rlWithTextHeight = rlWithText.getHeight();

                Random rand = new Random();

                int tvToReplaceNewPosX = rand.nextInt(rlWithTextWidth - tvToReplace.getWidth());
                int tvToReplaceNewPosY = rand.nextInt(rlWithTextHeight - tvToReplace.getHeight());

                tvToReplace.setX(tvToReplaceNewPosX);
                tvToReplace.setY(tvToReplaceNewPosY);

                Log.d("Replace", "Text replaced to " + tvToReplaceNewPosX + "x" + tvToReplaceNewPosY);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
