package whiskarek.andrewshkrob.activity.list;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import whiskarek.andrewshkrob.R;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        createGridLayout();
    }

    private void createGridLayout() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_content);
        final int offset = getResources().getDimensionPixelOffset(R.dimen.offset);
        recyclerView.addItemDecoration(new OffsetItemDecoration(offset));

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        final List<Integer> data = generateData();
        final ListAdapter listAdapter = new ListAdapter(data, this);
        recyclerView.setAdapter(listAdapter);
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
