package sg.edu.rp.c346.id22024713.mymovies;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

        String selected = data.getRating();
        int pos = 100;
        if (selected.equals("G")){
            pos = 0;
        } else if (selected.equals("PG")){
            pos = 1;
        } else if (selected.equals("PG13")){
            pos = 2;
        } else if (selected.equals("NC16")){
            pos = 3;
        } else if (selected.equals("M18")){
            pos = 4;
        } else {
            pos = 5;
        }
        spnRating.setSelection(pos);

        btnUpdate.setOnClickListener(v -> {
            String titleInput = etTitle.getText().toString();
            String genreInput = etGenre.getText().toString();
            String yearString = etYear.getText().toString();
            int yearInput = Integer.parseInt(yearString);
            String ratingInput = spnRating.getSelectedItem().toString();

            DBHelper dbh = new DBHelper(ModifyMovie.this);
            data.setMovie(titleInput, genreInput, yearInput, ratingInput);
            dbh.updateMovie(data);
            Toast.makeText(ModifyMovie.this, "'" + titleInput + "'" + " successfully updated!", Toast.LENGTH_SHORT).show();
            dbh.close();
            finish();
            Intent intent1 = new Intent(ModifyMovie.this, ShowMovies.class);
            startActivity(intent1);
        });

        btnDelete.setOnClickListener(v -> {
            String titleInput = etTitle.getText().toString();

            AlertDialog.Builder myBuilder = new AlertDialog.Builder(ModifyMovie.this);
            myBuilder.setTitle("Danger");
            myBuilder.setMessage("Are you sure you want to delete the movie " + "'" + titleInput + "'?");
            myBuilder.setCancelable(false);
            myBuilder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DBHelper dbh = new DBHelper(ModifyMovie.this);
                    dbh.deleteMovie(data.getId());
                    finish();
                    Intent intent2 = new Intent(ModifyMovie.this, ShowMovies.class);
                    startActivity(intent2);

                    Toast.makeText(ModifyMovie.this, "'" + titleInput + "'" + " successfully removed.", Toast.LENGTH_SHORT).show();
                }
            });

            myBuilder.setNegativeButton("CANCEL", null);
            AlertDialog myDialog = myBuilder.create();
            myDialog.show();
        });

        btnCancel.setOnClickListener(v -> {
            String titleInput = etTitle.getText().toString();

            AlertDialog.Builder myBuilder = new AlertDialog.Builder(ModifyMovie.this);
            myBuilder.setTitle("Danger");
            myBuilder.setMessage("Are you sure you want to discard the changes for " + "'" + titleInput + "'?");
            myBuilder.setCancelable(false);
            myBuilder.setPositiveButton("DISCARD", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            myBuilder.setNegativeButton("DO NOT DISCARD", null);
            AlertDialog myDialog = myBuilder.create();
            myDialog.show();

        });
    }
}