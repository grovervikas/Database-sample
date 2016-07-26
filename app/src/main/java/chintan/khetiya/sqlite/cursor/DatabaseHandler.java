package chintan.khetiya.sqlite.cursor;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION 	= 	1;
    // Database Name
    private static final String DATABASE_NAME 	= 	"my_data_base";

	//Database Tables
	//Student Data table
	private static final String TABLE_STUDENT_DATA = "StudentData";

	//Student Data Record
	private static final String TABLE_STUDENT_RECORD = "StudentRecord";


	// student table columns names
	private static final String studentId 		= 		"studentId";
	private static final String studentName 	= 		"studentName";

	//student data table columns names
	private static final String studentRecordId = 		"studentId";
	private static final String studentCourse 	= 		"studentCourse";
	private static final String studentAddress 	= 		"studentAddress";
	private static final String studentFees 	=		"studentFees";


    private final ArrayList<StudentData> contact_list = new ArrayList<StudentData>();


    public DatabaseHandler(Context context) {
	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

		// query to create student Data Table
		String CREATE_STUDENT_TABLE = "CREATE TABLE " + TABLE_STUDENT_DATA + "("
				+ studentId + " TEXT," + studentName + " TEXT" +")";

		//query to create student record Table
		String CREATE_STUDENT_DATA_TABLE = "CREATE TABLE " + TABLE_STUDENT_RECORD + "("
				+ studentRecordId + " TEXT," + studentCourse + " TEXT,"
				+ studentAddress + " TEXT"  + studentFees + " TEXT" +")";


//		String CREATE_TABLE_FINAL_STUDENT_RESULT = "CREATE TABLE " + TABLE_FINAL_RESULT + "("
//				+ id + " INTEGER PRIMARY KEY, "  + forginKey + " TEXT, " + "FOREIGN KEY ("+forginKey+") REFERENCES "+TABLE_STUDENT+"("+studentDataId+"))";



		// Enable foreign key constraints
		if (!db.isReadOnly()) {
			db.execSQL("PRAGMA foreign_keys = ON;");
		}

		db.execSQL(CREATE_STUDENT_TABLE);
		db.execSQL(CREATE_STUDENT_DATA_TABLE);
//		db.execSQL(CREATE_TABLE_FINAL_STUDENT_RESULT);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	// Drop older table if existed
	db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT_DATA);
	db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT_RECORD);
	// Create tables again
	onCreate(db);
    }


	// add new student record
	public void Add_Student(StudentData student){

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(studentId,student.studentId);
		values.put(studentName, student.studentName);

		// Inserting
		db.insert(TABLE_STUDENT_DATA, null, values);
		db.close(); // Closing database connection
	}

	public void Add_Student_Record(StudentRecord studentRecord){

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(studentCourse, studentRecord.studentCourse);
		values.put(studentAddress, studentRecord.studentAddress);
		values.put(studentFees, studentRecord.studentFees);
		// Inserting
		db.insert(TABLE_STUDENT_RECORD, null, values);
		db.close(); // Closing database connection
	}


	// get student record
	public StudentData getStudentData(String id){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_STUDENT_DATA, new String[] { studentId,
						studentName}, studentId + "=?",
				new String[] { id }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		StudentData student = new StudentData();
		student.studentId = cursor.getString(0);
		student.studentName	= cursor.getString(1);
		// return contact
		cursor.close();
		db.close();
		return student;
	}

	public ArrayList<StudentData>  fireRowQuery(){
		int i = 0;

		ArrayList<StudentData> students = new ArrayList<StudentData>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select "+studentName +" , "+studentCourse+ " from " + TABLE_STUDENT_DATA +" order by "+studentName ,null);
			if (cursor.moveToFirst()) {
				do {
					StudentData student = new StudentData();
					student.studentName = cursor.getString(0);
					students.add(student);
				} while (cursor.moveToNext());
			}
			cursor.close();
			db.close();
		} catch (Exception e) {
			Log.e("all_contact", "" + e);
		}
		return students;
	}


	// get student
	public StudentRecord getStudentRecord(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_STUDENT_RECORD, new String[] { studentRecordId,
						studentCourse, studentAddress,studentFees}, studentRecordId + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		StudentRecord studentRecord	 	= 	new StudentRecord();
		studentRecord.studentId 		= 	cursor.getString(0);
		studentRecord.studentCourse	= 		cursor.getString(1);
		studentRecord.studentAddress 	= 	cursor.getString(2);
		studentRecord.studentFees		=	cursor.getString(3);
		// return contact
		cursor.close();
		db.close();
		return studentRecord;
	}


	// update student record
	public int updateStudent(StudentData student) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(studentName, student.studentName);
		// updating row
		return db.update(TABLE_STUDENT_DATA, values, studentId + " = ?",
				new String[] { student.studentId });
	}


	// update student data
	public int updateStudentRecord(StudentRecord studentRecord) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(studentId, studentRecord.studentId);
		values.put(studentCourse, studentRecord.studentCourse);
		values.put(studentAddress, studentRecord.studentAddress);
		values.put(studentFees,studentRecord.studentFees);

		// updating row
		return db.update(TABLE_STUDENT_DATA, values, studentId + " = ?",
				new String[] { String.valueOf(studentRecord.studentId)});

	}




	// get all student records
	public ArrayList<StudentData> getAllStudentData() {
		ArrayList<StudentData> students = new ArrayList<StudentData>();
		try {
			String selectQuery = "SELECT  * FROM " + TABLE_STUDENT_DATA;
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor.moveToFirst()) {
				do {
					StudentData student = new StudentData();
					student.studentId =  cursor.getString(0);
					student.studentName = cursor.getString(1);
					students.add(student);
				} while (cursor.moveToNext());
			}
			cursor.close();
			db.close();
			return students;
		} catch (Exception e) {
			Log.e("all_contact", "" + e);
		}
		return students;
	}


	// get all student data
	public ArrayList<StudentRecord> getAllStudentRecords(){

		ArrayList<StudentRecord> students = new ArrayList<StudentRecord>();
		try {
			String selectQuery = "SELECT  * FROM " + TABLE_STUDENT_RECORD;
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor.moveToFirst()) {
				do {
					StudentRecord student = new StudentRecord();
					student.studentId 	 = cursor.getString(0);
					student.studentAddress = cursor.getString(1);
					student.studentCourse = cursor.getString(2);
					student.studentFees	= cursor.getString(3);
					students.add(student);
				} while (cursor.moveToNext());
			}
			cursor.close();
			db.close();
			return students;
		} catch (Exception e) {
			Log.e("all_contact", "" + e);
		}
		return students;
	}

	// Delete Student Record
	public void deleteStudentData(String id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_STUDENT_DATA, studentId + " = ?",
				new String[] { id });
		db.close();
	}

	// Delete Student Data Record
	public void deleteStudnetRecord(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_STUDENT_RECORD, studentRecordId + " = ?",
				new String[] { String.valueOf(id) });
		db.close();
	}

}
