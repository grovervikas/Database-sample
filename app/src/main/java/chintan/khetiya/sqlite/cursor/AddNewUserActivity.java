package chintan.khetiya.sqlite.cursor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.UUID;



/**
 * @author vikas.grover
 * Add a Studnet to DB
 */
public class AddNewUserActivity extends Activity implements View.OnClickListener{


    private static final String TAG = AddNewUserActivity.class.getSimpleName();
    private EditText addUserEditText;
    private Button addUserButton;
    private DatabaseHandler dbHandler = new DatabaseHandler(this);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_update_screen);
        addUserEditText         =       (EditText)findViewById(R.id.addUserEditText);
        addUserButton           =       (Button)findViewById(R.id.addUserButton);
        addUserButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addUserButton:{
                if(addUserEditText.getText().toString()!=null && addUserEditText.getText().toString().length()>0){
                    StudentData student = new StudentData();
                    student.studentId = UUID.randomUUID().toString();
                    student.studentName =  addUserEditText.getText().toString();
                    dbHandler.Add_Student(student);
                    Toast.makeText(AddNewUserActivity.this,"Student Added Successfully",Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
    }
}
