package com.example.supermarket_plan;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String TAG = "DatabaseHelper";

	private static final int DB_VERSION = 1;
	private static final String DB_PATH = "/data/data/com.example.supermarket_plan/databases/";
	private static final String DB_NAME = "supermarket_plan_db";

	public static final String CITY_NAME = "city_name";
	public static final String SUPERMARKET_NAME = "supermarket_name";
	public static final String ADDRESS_NAME = "address_name";

	private final Context context;

	private SQLiteDatabase myDataBase;

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
	}

	public void createDataBase() {
		Log.d(TAG, "createDataBase");
		boolean dbExist = checkDataBase();
		if (!dbExist) {
			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {
		File file = new File(DB_PATH + DB_NAME);
		boolean exist = file.exists();
		if (!exist) {
			this.getReadableDatabase();
		}
		Log.d(TAG, "checkDataBase - " + String.valueOf(exist));
		return exist;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transferring byte stream.
	 * */
	private void copyDataBase() throws IOException {
		Log.d(TAG, "copyDataBase");
		InputStream myInput = context.getAssets().open(DB_NAME);
		String outFileName = DB_PATH + DB_NAME;
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the input file to the output file
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}

	public void openDataBase() throws SQLException {
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);
	}

	@Override
	public synchronized void close() {
		if (myDataBase != null) {
			myDataBase.close();
		}
		super.close();
	}

	public Cursor getCityTable() {
		return myDataBase.rawQuery("select * from city", null);
	}

	public Cursor getSupermarketTable(int city_id) {
		return myDataBase.rawQuery("select * from supermarket where (_id in "
				+ "(select supermarket_id from address where city_id = "
				+ String.valueOf(city_id) + "))", null);
	}

	public Cursor getAddressTable(int city_id, int supermarket_id) {
		return myDataBase.rawQuery(
				"select _id, address_name, plan_path from address where (city_id = "
						+ String.valueOf(city_id) + " and supermarket_id = "
						+ String.valueOf(supermarket_id) + ")", null);
	}

	public int getCityId(String cityName) {
		Cursor cursor = myDataBase.rawQuery(
				"select _id from city where city_name = " + "'" + cityName
						+ "'", null);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}

	public int getSupermarketId(String supermarketName) {
		Cursor cursor = myDataBase.rawQuery(
				"select _id from supermarket where supermarket_name = " + "'"
						+ supermarketName + "'", null);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}

	public String getPlanPath(int cityId, int supermarketId,
			String supermarketAddress) {
		Cursor cursor = myDataBase.rawQuery(
				"select plan_path from address where (city_id = " + cityId
						+ " and supermarket_id = " + +supermarketId
						+ " and address_name = '" + supermarketAddress + "')",
				null);
		cursor.moveToFirst();
		return cursor.getString(0);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
