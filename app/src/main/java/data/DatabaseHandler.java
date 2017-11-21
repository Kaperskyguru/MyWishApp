package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.MyWish;

public class DatabaseHandler extends SQLiteOpenHelper {
    ArrayList<MyWish> wishList = new ArrayList<>();

    public DatabaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create = "CREATE TABLE wishes (`_id`	INTEGER , `title`	TEXT, `content` TEXT, `recorddate` LONG,PRIMARY KEY(_id));";


        String CREATE_WISHES_TABLE = "CREATE TABLE " + Constants.TABLE_NAME +
                "(" + Constants.KEY_ID + "INTEGER PRIMARY KEY," + Constants.TITLE_NAME +
                " TEXT, " + Constants.CONTENT_NAME + " TEXT," + Constants.DATE_NAME +
                "LONG);";

        Log.v("TESting", CREATE_WISHES_TABLE);
        db.execSQL(CREATE_WISHES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + Constants.TABLE_NAME);

        //Create a new one
        onCreate(db);
    }

    // Add content to database
    public void add_wishes_to_database(MyWish wish) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.TITLE_NAME, wish.getTitle());
        values.put(Constants.CONTENT_NAME, wish.getContent());
        values.put(Constants.DATE_NAME, java.lang.System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<MyWish> get_all_wishes() {
        String selectQuery = "SELECT * FROM" + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        //  String[] cols = ;

        Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor2 = db.query(Constants.TABLE_NAME, new String[]{Constants.KEY_ID,
                        Constants.TITLE_NAME, Constants.CONTENT_NAME,
                        Constants.DATE_NAME}, null, null, null, null,
                Constants.DATE_NAME, "DESC");

        if (cursor.moveToFirst()) {
            do {
                MyWish wish = new MyWish();
                wish.setTitle(cursor.getString(cursor.getColumnIndex(Constants.TITLE_NAME)));
                wish.setContent(cursor.getString(cursor.getColumnIndex(Constants.CONTENT_NAME)));

                DateFormat dateFormat = DateFormat.getDateInstance();
                String dateData = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.DATE_NAME))).getTime());
                wish.setRecordDate(dateData);

                wishList.add(wish);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return wishList;
    }
}
