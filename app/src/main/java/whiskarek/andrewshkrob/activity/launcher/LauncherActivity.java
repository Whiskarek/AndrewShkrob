package whiskarek.andrewshkrob.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.Settings;
import whiskarek.andrewshkrob.views.recyclerView.LauncherAdapter;
import whiskarek.andrewshkrob.views.recyclerView.OffsetItemDecoration;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        createGridLayout();
    }

    private void createGridLayout() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.launcher_content);
        final int offset = getResources().getDimensionPixelOffset(R.dimen.offset);
        recyclerView.addItemDecoration(new OffsetItemDecoration(offset));

        final int spanCount = (Settings.getModelType() == Settings.Model.DEFAULT ? getResources().getInteger(R.integer.modelDefault) : getResources().getInteger(R.integer.modelSolid));
        final GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        recyclerView.setLayoutManager(layoutManager);

        final List<Integer> data = generateData();
        final LauncherAdapter launcherAdapter = new LauncherAdapter(data);
        recyclerView.setAdapter(launcherAdapter);
    }

    private List<Integer> generateData() {
        final List<Integer> colors = new ArrayList<>();
        final Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            int color = Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            colors.add(color);
        }

        return colors;
    }
}
