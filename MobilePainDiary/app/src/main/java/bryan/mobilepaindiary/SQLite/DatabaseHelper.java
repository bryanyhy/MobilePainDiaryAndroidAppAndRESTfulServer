package bryan.mobilepaindiary.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import bryan.mobilepaindiary.ObjectClass.Record;

/**
 * Created by Bryanyhy on 25/4/16.
 */

// Building up the SQLite and setup methods to make use of it
public class DatabaseHelper extends SQLiteOpenHelper {

    // Set database and table properties
    public static final String DATABASE_NAME = "RecordDB";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "records";

    // Define the columns present in the records table
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERID = "userid";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_DATETIME = "dateTime";

    // Create table statement
    public static final String CREATE_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_USERID + " INTEGER NOT NULL, " +
                    COLUMN_ADDRESS + " TEXT NOT NULL, " +
                    COLUMN_LATITUDE + " TEXT NOT NULL, " +
                    COLUMN_LONGITUDE + " TEXT NOT NULL, " +
                    COLUMN_DATETIME + " TEXT NOT NULL" +
                    ");";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Extending SQLiteOpenHelper requires both onCreate and onUpgrade methods be implemented!
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STATEMENT);
    }

    // drop the old table and recreate it using the defined create statement if needed
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // add a new record
    public void addRecord(Record r) {
        // Get writable database instance
        SQLiteDatabase db = this.getWritableDatabase();

        // Store values needed for insert statement
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERID, r.getUserid());
        values.put(COLUMN_ADDRESS, r.getAddress());
        values.put(COLUMN_LATITUDE, r.getLatitude());
        values.put(COLUMN_LONGITUDE, r.getLongitude());
        values.put(COLUMN_DATETIME, r.getDateTime());

        // Insert row into table and close database connection
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // Get record by user id
    public Record getRecordByUserId(int id) throws ParseException {
        // Get readable database instance
        SQLiteDatabase db = this.getReadableDatabase();

        // Run query and get cursor to access row
        Cursor cursor = db.query(TABLE_NAME, // Table identifier
                new String[] { COLUMN_ID, COLUMN_USERID, COLUMN_ADDRESS, COLUMN_LATITUDE, COLUMN_LONGITUDE, COLUMN_DATETIME }, // Selection
                COLUMN_USERID + "= ?", // Where
                new String[] { String.valueOf(id) }, // Where parameters
                null, // Group by
                null, // Having
                null, // Order by
                null  // Limit
        );

        // Move to first element in data source if it exists
        if(cursor != null) {
            cursor.moveToFirst();
        }

        // Create and return contact
        Record r = new Record(cursor.getLong(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
        return r;
    }

    // getting all records in a record list
    public List<Record> getAllRecords() throws ParseException {
        List<Record> list = new ArrayList<Record>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Run select statement and access data via Cursor
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // Loop through rows and create list
        if(cursor.moveToFirst()) {
            do {
                Record r = new Record(cursor.getLong(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
                // Add contact to list
                list.add(r);
            } while(cursor.moveToNext());
        }

        // Return contents of table
        return list;
    }

    // This method is not called in the application, but it is better to keep it for future extensions
    public int getRecordCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        // Execute the query
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        cursor.close();
        // Return contact count
        return cursor.getCount();
    }

    // Update the attributes of existing record object by specific ID. All attributes excluding the id will be updated accordingly
    public int updateRecord(Record r) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Set values we want to change
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERID, r.getUserid());
        values.put(COLUMN_ADDRESS, r.getAddress());
        values.put(COLUMN_LATITUDE, r.getLatitude());
        values.put(COLUMN_LONGITUDE, r.getLongitude());
        values.put(COLUMN_DATETIME, r.getDateTime());

        // Update row and return number affected
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[] { String.valueOf(r.getId()) } );
    }
    // Delete the specific record object by its Primary Key, which is ID
    public void deleteRecord(Record record) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete record from database based on table and id
        db.delete(TABLE_NAME,
                COLUMN_ID + " = ?",
                new String[] { String.valueOf(record.getId()) }
        );
        // Close connection to database
        db.close();
    }
}
