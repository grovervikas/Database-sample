package chintan.khetiya.sqlite.cursor;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * @author vikas.grover
 *         Student Record Class
 */
public class StudentRecord implements Parcelable {

    public String studentId;
    public String studentCourse;
    public String studentAddress;
    public String studentFees;


    public StudentRecord() {
    }


    public StudentRecord(Parcel source) {

        studentId = source.readString();
        studentCourse = source.readString();
        studentAddress = source.readString();
        studentFees = source.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(studentId);
        parcel.writeString(studentCourse);
        parcel.writeString(studentAddress);
        parcel.writeString(studentFees);

    }


    public static Creator<StudentRecord> CREATOR = new Creator<StudentRecord>() {
        public StudentRecord createFromParcel(Parcel source) {
            return new StudentRecord(source);
        }

        public StudentRecord[] newArray(int size) {
            return new StudentRecord[size];
        }
    };
}
