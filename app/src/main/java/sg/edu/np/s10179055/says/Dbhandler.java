package sg.edu.np.s10179055.says;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dbhandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String ACCOUNTS = "Accounts";
    public static final String COLUMN_USERNAME = "UserName";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_STUDENTNO = "StudentNo";
    public static final String COLUMNN_DOB = "DATE_OF_BIRTH";
    public static final String COLUMN_NRIC = "NRIC";
    public static final String COLUMN_Course = "Course";
    public static final String COLUMN_LoginCount = "LoginCount";
    public static final String COLUMN_NAME = "Name";
    private static final String TAG = "MyDBhandler";
    private static final String DATABASE_NAME = "accountDB.db";

    public Dbhandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ACCOUNTS_TABLE = "CREATE TABLE " + ACCOUNTS + " (" + COLUMN_USERNAME + " TEXT," + COLUMN_PASSWORD + " TEXT," + COLUMN_EMAIL + " TEXT," + COLUMN_NRIC + " TEXT," + COLUMN_STUDENTNO + " TEXT," + COLUMNN_DOB + " TEXT," + COLUMN_LoginCount + " TEXT," + COLUMN_Course + " TEXT," + COLUMN_NAME + " TEXT)";
        db.execSQL(CREATE_ACCOUNTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNTS);
        onCreate(db);
    }

    public void addAccount(Account a) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_Course, a.getCourse());
        values.put(COLUMN_EMAIL, a.getEmail());
        values.put(COLUMN_LoginCount, a.getLoginCount());
        values.put(COLUMN_NRIC, a.getNRIC());
        values.put(COLUMN_NAME, a.getName());
        values.put(COLUMN_PASSWORD, a.getPassword());
        values.put(COLUMN_USERNAME, a.getUsername());
        values.put(COLUMNN_DOB, a.getDOB());
        values.put(COLUMN_STUDENTNO, a.getStudentNo());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ACCOUNTS, null, values);
        db.close();
    }

    public boolean findAccount(String username, String password, Account a) {
        String query = "SELECT * FROM " + ACCOUNTS + " WHERE" + COLUMN_USERNAME + " =\"" + username + "\"" + " AND" + COLUMN_PASSWORD + " =\"" + password;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            a.setUsername(cursor.getString(6));
            a.setPassword(cursor.getString(7));
            db.close();
            return true;
        }
        db.close();
        return false;
    }
}
