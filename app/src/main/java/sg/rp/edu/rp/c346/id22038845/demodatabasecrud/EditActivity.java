package sg.rp.edu.rp.c346.id22038845.demodatabasecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    TextView tvID, tvContent;
    EditText etContent;
    Button btnUpdate, btnDelete;
    Note data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //initialize the variables with ui here
        tvID = findViewById(R.id.tvID);
        tvContent = findViewById(R.id.tvEditContent);
        etContent = findViewById(R.id.etContent2);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        Intent intentRecieved = getIntent();
        DBHelper dbh = new DBHelper(EditActivity.this);
        int pos = intentRecieved.getIntExtra("position",0);
        tvID.setText("ID: " + pos);
        //tvContent.setText(data.getNoteContent(pos));


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                data.setNoteContent(etContent.getText().toString());
                dbh.updateNote(data);
                dbh.close();
                Intent i = new Intent(EditActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                dbh.deleteNote(data.getId());
                Intent i = new Intent(EditActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

    }
}