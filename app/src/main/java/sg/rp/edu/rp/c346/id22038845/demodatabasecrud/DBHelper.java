package sg.rp.edu.rp.c346.id22038845.demodatabasecrud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper { //dbHelper === sqliteHelper class
    private static final int DATABASE_VER = 2; //always integer
    private static final String DATABASE_NAME ="simplenotes.db";
    private static final String TABLE_NOTE = "note";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NOTE_CONTENT = "note_content";

    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VER);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE " + TABLE_NOTE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NOTE_CONTENT + " TEXT )";
        db.execSQL(createTableSql);
        Log.i("info","created tables"); //not needed but to indicate in logcat

        // dummy records to be inserted when db is created
        for(int i = 0; i<4; i++){
                ContentValues values = new ContentValues();
                values.put(COLUMN_NOTE_CONTENT, "Data Number "+ i);
            db.insert(TABLE_NOTE, null, values);
        }
        Log.i("info", "dummy records inserted");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE );
        //onCreate(db); //not a good  practice

        db.execSQL("ALTER TABLE " + TABLE_NOTE + " ADD COLUMN module_name TEXT ");
        //^works in tandem with database version
    }

    public long insertNote(String noteContent){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_CONTENT, noteContent);
        long result = db.insert(TABLE_NOTE, null, values);
        db.close();
        if(result == -1){
            Log.d("DBHelper", "Insert failed");
        }else {
            Log.d("Sql insert", "Id: " + result); //id returned, shouldnt be -1
        }
        return  result;
    }

    public int updateNote(Note data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_CONTENT, data.getNoteContent());
        String condition = COLUMN_ID + "= ?"; //? one variable/parameter to check
        String[] args = {String.valueOf(data.getId())}; //if more then one will be ? and name = ?, arg is the value to be place in the args, if using condition with null arguments it is bad practice as it will make the code vulnerable to sql injection
        int result = db.update(TABLE_NOTE, values, condition, args);
        db.close();
        return result;
    }

    public int deleteNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_NOTE, condition, args);
        db.close();
        return result;
    }

    public ArrayList<Note> getAllNotes(){
        ArrayList<Note> notes = new ArrayList<Note>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COLUMN_ID, COLUMN_NOTE_CONTENT};

        Cursor cursor = db.query(TABLE_NOTE, columns, null,null,null,null,null,null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String noteContent = cursor.getString(1);
                Note note = new Note(id,noteContent);
                notes.add(note);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }

    public ArrayList<Note> getAllNotes(String keyword){
        ArrayList<Note> notes = new ArrayList<Note>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_NOTE_CONTENT};
        String condition = COLUMN_NOTE_CONTENT + " Like ?";
        String[] args = {"%" + keyword + "%"};
        Cursor cursor = db.query(TABLE_NOTE, columns, condition, args, null,null,null,null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String noteContent = cursor.getString(1);
                Note note = new Note(id, noteContent);
                notes.add(note);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }




}
