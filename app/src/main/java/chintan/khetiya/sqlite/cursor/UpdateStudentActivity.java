package chintan.khetiya.sqlite.cursor;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author vikas.grover
 * Update Student Activity Class
 */
public class UpdateStudentActivity extends Activity implements View.OnClickListener{

    private static final String TAG     =   UpdateStudentActivity.class.getSimpleName();
    private EditText addUserEditText;
    private Button updateUserBtn;
    private String studentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        studentId  = intent.getStringExtra("USER_ID");
        setContentView(R.layout.activity_update_student);
        addUserEditText     =       (EditText)findViewById(R.id.addUserEditText);
        updateUserBtn       =       (Button)findViewById(R.id.updateUserBtn);
        updateUserBtn.setOnClickListener(this);
        DatabaseHandler dbHandler = new DatabaseHandler(this);
        StudentData studentData  = dbHandler.getStudentData(studentId);
        if(!TextUtils.isEmpty(studentData.studentName)){
            addUserEditText.setText(studentData.studentName);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.updateUserBtn:{
                DatabaseHandler dbHandler = new DatabaseHandler(this);
                StudentData studentData = new StudentData();
                studentData.studentId = studentId;
                studentData.studentName = addUserEditText.getText().toString();
                dbHandler.updateStudent(studentData);
                Toast.makeText(UpdateStudentActivity.this,"Data Updated success",Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}
