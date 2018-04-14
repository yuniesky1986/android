package cu.delrio.yun.friends;

import cu.delrio.yun.friends.base_datos.Estructura;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;


public class SQLBD extends SQLiteOpenHelper{
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Estructura.TABLE_NAME + " (" +
                    Estructura._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Estructura.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    Estructura.COLUMN_NAME_AGE + TEXT_TYPE + COMMA_SEP +
                    Estructura.COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEP +
                    Estructura.COLUMN_NAME_DIR + TEXT_TYPE + COMMA_SEP +
                    Estructura.COLUMN_NAME_USER + TEXT_TYPE + COMMA_SEP +
                   // Estructura.COLUMN_NAME_IMAGE + TEXT_TYPE + COMMA_SEP +
                    Estructura.COLUMN_NAME_PASS + TEXT_TYPE +" )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Estructura.TABLE_NAME;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "users.sqlite";

    public SQLBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
