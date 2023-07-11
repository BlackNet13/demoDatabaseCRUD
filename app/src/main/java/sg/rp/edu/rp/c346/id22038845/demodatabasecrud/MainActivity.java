package sg.rp.edu.rp.c346.id22038845.demodatabasecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnEdit, btnRetrieve;
    TextView tvDBContent;
    EditText etContent;
    ArrayList<Note> al;
    ListView lv;
    ArrayAdapter<Note> aa;

    @Override
    protected void onResume(){
        super.onResume();

        btnRetrieve.performClick();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the variables with UI here
        btnAdd = findViewById(R.id.btnAdd);
        btnEdit = findViewById(R.id.btnEdit);
        btnRetrieve = findViewById(R.id.btnRetrieve);

        tvDBContent = findViewById(R.id.tvContent);
        etContent = findViewById(R.id.etContent);
        lv = findViewById(R.id.lv);


        al = new ArrayList<Note>();
        aa = new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1,al);
        lv.setAdapter(aa);



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etContent.getText().toString();
                DBHelper  dbh = new DBHelper(MainActivity.this);
                long inserted_id = dbh.insertNote(data);

                if(inserted_id != -1){
                    al.clear();
                    al.addAll(dbh.getAllNotes());
                    aa.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Insert successful", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                al.clear();
                //al.addAll(dbh.getAllNotes());

                String filterText = etContent.getText().toString().trim();
                if(filterText.length()==0){
                    al.addAll(dbh.getAllNotes());
                }else{
                    al.addAll(dbh.getAllNotes(filterText));
                }
                aa.notifyDataSetChanged();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note target =al.get(0);

                Intent i = new Intent(MainActivity.this,EditActivity.class);
                i.putExtra("data", target);
                i.putExtra("position", String.valueOf(btnEdit.getTag()));
                startActivity(i);

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*btnEdit.setTag(position);
                btnEdit.performClick();*/

                Note target =al.get(position);

                Intent i = new Intent(MainActivity.this,EditActivity.class);
                i.putExtra("data", target);
                i.putExtra("position", position);
                startActivity(i);


            }
        });
    }
}