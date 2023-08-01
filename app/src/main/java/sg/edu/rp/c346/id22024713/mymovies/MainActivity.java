package sg.edu.rp.c346.id22024713.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    EditText etTitle, etGenre, etYear;
    Spinner spnRating;
    Button btnInsert, btnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = findViewById(R.id.editTextTitle);
        etGenre = findViewById(R.id.editTextGenre);
        etYear = findViewById(R.id.editTextYear);
        spnRating = findViewById(R.id.spinnerRating);
        btnInsert = findViewById(R.id.buttonInsert);
        btnShow = findViewById(R.id.buttonShow);

        btnInsert.setOnClickListener(v -> {
            String titleInput = etTitle.getText().toString();
            String genreInput = etGenre.getText().toString();
            String yearString = etYear.getText().toString();
            int yearInput = Integer.parseInt(yearString);
            String ratingInput = spnRating.getSelectedItem().toString();

            DBHelper db = new DBHelper(MainActivity.this);
            db.insertMovie(titleInput, genreInput, yearInput, ratingInput);
            etTitle.getText().clear();
            etGenre.getText().clear();
            etYear.getText().clear();
        });

        btnShow.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ShowMovies.class);
            startActivity(intent);
        });
    }
}