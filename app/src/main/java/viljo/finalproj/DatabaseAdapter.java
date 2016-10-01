package viljo.finalproj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.HashMap;

public class DatabaseAdapter extends SQLiteOpenHelper{
    private static final String TAG = DatabaseAdapter.class.getSimpleName();
    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USER = "user";
    private static final String KEY_ID = "id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_FIRST = "first";
    private static final String KEY_SUR = "surname";
    private static final String KEY_USER = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_CREATED_AT = "created_at";

    public DatabaseAdapter(Context context) {
        super(context, DATABASE_NAME,  null,    DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "(" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_FIRST + " TEXT, " +
                KEY_SUR + " TEXT, " +
                KEY_USER+ " TEXT UNIQUE, " +
                KEY_EMAIL + " TEXT UNIQUE, " +
                KEY_PASSWORD + " TEXT, " +
                KEY_CREATED_AT + " TEXT)";
        db.execSQL(CREATE_USER_TABLE);
        Log.d(TAG,"Database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public void registeruser(String first, String sur, String username, String email, String password, String created_at){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FIRST,first);
        values.put(KEY_SUR,sur);
        values.put(KEY_USER,username);
        values.put(KEY_EMAIL,email);
        values.put(KEY_PASSWORD,password);
        values.put(KEY_CREATED_AT,created_at);

        long id  = db.insert(TABLE_USER,null, values);
        db.close();
        Log.d(TAG, "User added successfully!");
    }

    public boolean checkExist(String username, String email){
        String qry = "SELECT * FROM " + TABLE_USER + " WHERE " + KEY_EMAIL +" = '"+email+"' or "+ KEY_USER + " = '" + username +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(qry,null);
        cursor.moveToFirst();
        if (cursor.getCount() != 0){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean validateUser(String userc, String password){
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT * FROM "+TABLE_USER+" WHERE " +KEY_EMAIL+ "='" + userc+"' or " + KEY_USER + "= '"+userc+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            if (password.equals(cursor.getString(5))){
                user.put("first",cursor.getString(1));
                user.put("surname",cursor.getString(2));
                user.put("username",cursor.getString(3));
                user.put("email",cursor.getString(4));
                user.put("password",cursor.getString(5));
                user.put("created_at",cursor.getString(6));
                Log.d(TAG,"Fetching user from Sqlite: "+user.toString());
                cursor.close();
                db.close();
            }
            return true;
        }
        else{
            cursor.close();
            db.close();
            return false;
        }
    }

    public  void deleteUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, null, null);
        db.close();
        Log.d(TAG, "Deleted all user from sqlite");
    }
}