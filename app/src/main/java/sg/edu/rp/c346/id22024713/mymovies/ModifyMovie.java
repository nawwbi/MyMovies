package sg.edu.rp.c346.id22024713.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ModifyMovie extends AppCompatActivity {

    EditText etId, etTitle, etGenre, etYear;
    Spinner spnRating;
    Button btnUpdate, btnDelete, btnCancel;
    Movie data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_movie);

        etId = findViewById(R.id.movieIdInput);
        etTitle = findViewById(R.id.titleInput);
        etGenre = findViewById(R.id.genreInput);
        etYear = findViewById(R.id.yearInput);
        spnRating = findViewById(R.id.ratingInput);
        btnUpdate = findViewById(R.id.update);
        btnDelete = findViewById(R.id.delete);
        btnCancel = findViewById(R.id.cancel);

        Intent intent = getIntent();
        data = (Movie) intent.getSerializableExtra("list");

        etId.setText(data.getId() + "");
        etTitle.setText(data.getTitle());
        etGenre.setText(data.getGenre());
        etYear.setText(data.getYear() + "");

        btnUpdate.setOnClickListener(v -> {
            String titleInput = etTitle.getText().toString();
            String genreInput = etGenre.getText().toString();
            String yearString = etYear.getText().toString();
            int yearInput = Integer.parseInt(yearString);
            String ratingInput = spnRating.getSelectedItem().toString();

            DBHelper dbh = new DBHelper(ModifyMovie.this);
            data.setMovie(titleInput, genreInput, yearInput, ratingInput);
            dbh.updateMovie(data);
            dbh.close();
            finish();
            Intent intent1 = new Intent(ModifyMovie.this, ShowMovies.class);
            startActivity(intent1);
        });

        btnDelete.setOnClickListener(v -> {
            DBHelper dbh = new DBHelper(ModifyMovie.this);
            dbh.deleteMovie(data.getId());
            finish();
            Intent intent2 = new Intent(ModifyMovie.this, ShowMovies.class);
            startActivity(intent2);
        });

        btnCancel.setOnClickListener(v -> {
            finish();
        });
    }
}