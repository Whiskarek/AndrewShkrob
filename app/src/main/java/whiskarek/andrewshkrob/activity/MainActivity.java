package whiskarek.andrewshkrob.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.views.CircleImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final CircleImageView civPhoto = (CircleImageView) findViewById(R.id.civPhoto);
        civPhoto.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                //startActivity(new Intent(MainActivity.this, LauncherActivity.class));

                return false;
            }
        });
    }

    /**
     * OnClickListener to open VK.
     */
    public void vkClick(View view) {
        Log.d("Click", "VK");
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(getResources().getString(R.string.vkURL)));

        startActivity(intent);
    }

    /**
     * OnClickListener to open GitHub.
     */
    public void gitClick(View view) {
        Log.d("Click", "Git");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/" + getResources().getString(R.string.gitHubURL)));

        startActivity(intent);
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
