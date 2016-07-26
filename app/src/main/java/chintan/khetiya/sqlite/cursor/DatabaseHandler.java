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
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";
	// student table
	private static final String TABLE_STUDENT = "student_table";
	// student data table
	private static final String TABLE_STUDENT_DATA = "student_table_data";
	// final result
	private static final String TABLE_FINAL_RESULT = "student_result";




	// student table columns names
	private static final String studentId = "studentId";
	private static final String studentName = "studentName";
	private static final String studentCourse = "studentCourse";
	private static final String studentKey = "studentKey";


	//student data table columns names
	private static final String studentDataId = "studentId";
	private static final String studentFName = "studentFName";
	private static final String studentMName = "studentMName";


	//student final result table
	private static final String id  = "id";
	private static final String forginKey  = "forginKey";



	// Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_EMAIL = "email";
    private final ArrayList<Contact> contact_list = new ArrayList<Contact>();

    public DatabaseHandler(Context context) {
	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
//
//	String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
//		+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
//		+ KEY_PH_NO + " TEXT," + KEY_EMAIL + " TEXT" + ")";

		// query to create student table
		String CREATE_STUDENT_TABLE = "CREATE TABLE " + TABLE_STUDENT + "("
				+ studentId + " INTEGER PRIMARY KEY," + studentName + " TEXT,"
				+ studentKey + " TEXT," + studentCourse + " TEXT" +")";


		//query to create student data table
		String CREATE_STUDENT_DATA_TABLE = "CREATE TABLE " + TABLE_STUDENT_DATA + "("
				+ studentDataId + " INTEGER PRIMARY KEY," + studentFName + " TEXT,"
				+ studentMName + " TEXT" +")";


		String CREATE_TABLE_FINAL_STUDENT_RESULT = "CREATE TABLE " + TABLE_FINAL_RESULT + "("
				+ id + " INTEGER PRIMARY KEY, "  + forginKey + " TEXT, " + "FOREIGN KEY ("+forginKey+") REFERENCES "+TABLE_STUDENT+"("+studentDataId+"))";



		// Enable foreign key constraints
		if (!db.isReadOnly()) {
			db.execSQL("PRAGMA foreign_keys = ON;");
		}

		db.execSQL(CREATE_STUDENT_TABLE);
		db.execSQL(CREATE_STUDENT_DATA_TABLE);
		db.execSQL(CREATE_TABLE_FINAL_STUDENT_RESULT);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	// Drop older table if existed
	db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
	db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT_DATA);
	// Create tables again
	onCreate(db);
    }


	// add new student record
	public void Add_Student(Student student){

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(studentName, student.studentName);
		values.put(studentKey,student.studentKey);
		values.put(studentCourse, student.studentCourse); // Contact Email
		// Inserting Row
		db.insert(TABLE_STUDENT, null, values);

		ContentValues values1 = new ContentValues();
		values1.put(forginKey,student.studentKey);
		db.insert(TABLE_FINAL_RESULT,null,values1);

		db.close(); // Closing database connection

	}

	public void Add_Student_Data(StudentData studentData){

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(studentFName, studentData.studentFName); // Contact Phone
		values.put(studentMName, studentData.studentMName); // Contact Email
		// Inserting Row
		db.insert(TABLE_STUDENT_DATA, null, values);
		db.close(); // Closing database connection

	}

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public void Add_Contact(Contact contact) {
	SQLiteDatabase db = this.getWritableDatabase();
	ContentValues values = new ContentValues();
	values.put(KEY_NAME, contact.getName()); // Contact Name
	values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone
	values.put(KEY_EMAIL, contact.getEmail()); // Contact Email
	// Inserting Row
	db.insert(TABLE_CONTACTS, null, values);
	db.close(); // Closing database connection
    }


	// get student record
	public Student getStudent(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_STUDENT, new String[] { studentId,
						studentName, studentCourse}, studentId + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		Student student = new Student();
		student.studentId = Integer.parseInt(cursor.getString(0));
		student.studentName = cursor.getString(1);
		student.studentCourse = cursor.getString(2);
		// return contact
		cursor.close();
		db.close();
		return student;
	}

	public ArrayList<Student>  fireRowQuery(){
		int i = 0;

		ArrayList<Student> students = new ArrayList<Student>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select "+studentName +" , "+studentCourse+ " from " + TABLE_STUDENT +" order by "+studentName ,null);
			if (cursor.moveToFirst()) {
				do {
					Student student = new Student();
					student.studentName = cursor.getString(0);
					student.studentCourse = cursor.getString(1);
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
	public StudentData getStudentData(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_STUDENT_DATA, new String[] { studentId,
						studentFName, studentMName}, studentId + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		StudentData studentData = new StudentData();
		studentData.studentId = Integer.parseInt(cursor.getString(0));
		studentData.studentFName= cursor.getString(1);
		studentData.studentMName = cursor.getString(2);
		// return contact
		cursor.close();
		db.close();
		return studentData;
	}


	// update student record
	public int updateStudent(Student student) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(studentName, student.studentName);
		values.put(studentCourse, student.studentCourse);
		// updating row
		return db.update(TABLE_STUDENT, values, studentId + " = ?",
				new String[] { String.valueOf(student.studentId)});
	}


	// update student data
	public int updateStudent(StudentData studentData) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(studentId, studentData.studentId);
		values.put(studentFName, studentData.studentFName);
		values.put(studentMName, studentData.studentMName);

		// updating row
		return db.update(TABLE_STUDENT_DATA, values, KEY_ID + " = ?",
				new String[] { String.valueOf(studentData.studentId)});

	}




	// get all student records
	public ArrayList<Student> getAllStudent() {
		ArrayList<Student> students = new ArrayList<Student>();
		try {
			String selectQuery = "SELECT  * FROM " + TABLE_STUDENT;
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor.moveToFirst()) {
				do {
					Student student = new Student();
					student.studentId = Integer.parseInt(cursor.getString(0));
					student.studentName = cursor.getString(1);
					student.studentKey = cursor.getString(2);
					student.studentCourse = cursor.getString(3);
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


	// get all result data
	public ArrayList<FinalResult> getAllStudentResult() {
		ArrayList<FinalResult> students = new ArrayList<FinalResult>();
		try {
			String selectQuery = "SELECT  * FROM " + TABLE_FINAL_RESULT;
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor.moveToFirst()) {
				do {
					FinalResult student = new FinalResult();
					student.id = Integer.parseInt(cursor.getString(0));
					student.forginKey = cursor.getString(1);
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
	public ArrayList<StudentData> getAllStudentData(){

		ArrayList<StudentData> students = new ArrayList<StudentData>();
		try {
			String selectQuery = "SELECT  * FROM " + TABLE_STUDENT_DATA;
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			if (cursor.moveToFirst()) {
				do {
					StudentData student = new StudentData();
					student.studentId = Integer.parseInt(cursor.getString(0));
					student.studentFName = cursor.getString(1);
					student.studentMName = cursor.getString(2);
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
	public void delete_Student(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_STUDENT, studentId + " = ?",
				new String[] { String.valueOf(id) });
		db.close();
	}

	// Delete Student Data Record
	public void delete_Student_Data(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_STUDENT_DATA, studentDataId + " = ?",
				new String[] { String.valueOf(id) });
		db.close();
	}


	// number of records in student table
	public int get_All_studentsRecords() {
		String countQuery = "SELECT  * FROM " + TABLE_STUDENT;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();
		return cursor.getCount();
	}

	// number of records in student data table
	public int get_All_studentsRecordsData() {
		String countQuery = "SELECT  * FROM " + TABLE_STUDENT_DATA;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();
		return cursor.getCount();
	}
}
