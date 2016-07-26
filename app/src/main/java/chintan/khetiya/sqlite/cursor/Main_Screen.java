package chintan.khetiya.sqlite.cursor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Main_Screen extends Activity {


    private Button add_btn;
    private ListView Contact_listview;
    private ArrayList<Student> students = new ArrayList<Student>();
    private Contact_Adapter cAdapter;
    private DatabaseHandler db;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        try {
            Contact_listview = (ListView) findViewById(R.id.list);
            Contact_listview.setItemsCanFocus(false);
            add_btn = (Button) findViewById(R.id.add_btn);
            Set_Referash_Data();
            db = new DatabaseHandler(this);


        } catch (Exception e) {
            Log.e("some error", "" + e);
        }

        add_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent add_user = new Intent(Main_Screen.this, Add_Update_User.class);
                add_user.putExtra("called", "add");
                add_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(add_user);
                finish();

            }
        });
    }

    public void Call_My_Blog(View v) {
        Intent intent = new Intent(Main_Screen.this, My_Blog.class);
        startActivity(intent);

    }

    public void Set_Referash_Data() {
        students.clear();
        db = new DatabaseHandler(this);
        ArrayList<Student> contact_array_from_db = db.getAllStudent();
        ArrayList<FinalResult> arrayList = db.getAllStudentResult();
        students.addAll(contact_array_from_db);
        db.close();
        cAdapter = new Contact_Adapter(Main_Screen.this, R.layout.listview_row, students);
        Contact_listview.setAdapter(cAdapter);
        cAdapter.notifyDataSetChanged();
    }

    public void Show_Toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Set_Referash_Data();


    }

    public class Contact_Adapter extends ArrayAdapter<Student> {
        Activity activity;
        int layoutResourceId;
        Student user;
        ArrayList<Student> data = new ArrayList<Student>();

        public Contact_Adapter(Activity act, int layoutResourceId,
                               ArrayList<Student> data) {
            super(act, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.activity = act;
            this.data = data;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            UserHolder holder = null;

            if (row == null) {
                LayoutInflater inflater = LayoutInflater.from(activity);

                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new UserHolder();
                holder.student_name = (TextView) row.findViewById(R.id.student_name);
                holder.student_course = (TextView) row.findViewById(R.id.student_course);
                holder.btn_update = (Button) row.findViewById(R.id.btn_update);
                holder.btn_delete = (Button) row.findViewById(R.id.btn_delete);
                row.setTag(holder);
            } else {
                holder = (UserHolder) row.getTag();
            }
            user = data.get(position);
            holder.btn_update.setTag(user.studentId);
            holder.btn_delete.setTag(user.studentId);
            holder.student_name.setText(user.studentName);
            holder.student_course.setText(user.studentCourse);

            holder.btn_update.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

//                    Intent update_user = new Intent(activity, Add_Update_User.class);
//                    update_user.putExtra("called", "update");
//                    update_user.putExtra("USER_ID", v.getTag().toString());
//                    activity.startActivity(update_user);
                    db = new DatabaseHandler(Main_Screen.this);
                    students.clear();
                    students.addAll(db.fireRowQuery());
                    cAdapter.notifyDataSetChanged();



                }
            });
            holder.btn_delete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View v) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(activity);
                    adb.setTitle("Delete?");
                    adb.setMessage("Are you sure you want to delete ");
                    final int user_id = Integer.parseInt(v.getTag().toString());
                    adb.setNegativeButton("Cancel", null);
                    adb.setPositiveButton("Ok",
                            new AlertDialog.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    DatabaseHandler dBHandler = new DatabaseHandler(
                                            activity.getApplicationContext());
                                    dBHandler.delete_Student(user_id);
                                    Main_Screen.this.onResume();
                                }
                            });
                    adb.show();
                }
            });
            return row;

        }

        class UserHolder {

            public TextView student_name,student_course;
            public Button btn_update,btn_delete;


        }
    }
}
