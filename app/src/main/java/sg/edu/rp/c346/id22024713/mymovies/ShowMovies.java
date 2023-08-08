package sg.edu.rp.c346.id22024713.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class ShowMovies extends AppCompatActivity {

    ListView lvMovies;
    Button tb1, tb2, btnBack;
    CustomAdapter adapter;
    ArrayList<Movie> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movies);

        lvMovies = findViewById(R.id.listView);
        tb1 = findViewById(R.id.toggle1);
        tb2 = findViewById(R.id.toggle2);
        btnBack = findViewById(R.id.buttonBack);
        data = new ArrayList<>();

        DBHelper db = new DBHelper(ShowMovies.this);
        data = db.getMovies();
        db.close();
        adapter = new CustomAdapter(ShowMovies.this, R.layout.row, data);
        lvMovies.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        lvMovies.setOnItemClickListener((parent, view, position, id) -> {
            Movie list = data.get(position);
            Intent intent = new Intent(ShowMovies.this, ModifyMovie.class);
            intent.putExtra("list", list);
            startActivity(intent);
        });

        tb1.setOnClickListener(v -> {
            adapter = new CustomAdapter(ShowMovies.this, R.layout.row, db.getMovies());
            lvMovies.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });

        tb2.setOnClickListener(v -> {
            adapter = new CustomAdapter(ShowMovies.this, R.layout.row, db.getFilteredMovies());
            lvMovies.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });

        btnBack.setOnClickListener(v -> {
            finish();
        });
    }
}