package chintan.khetiya.sqlite.cursor;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * @author vikas.grover
 *         Studnet Data Class
 */
public class StudentData implements Parcelable {

    public String studentId;
    public String studentName;


    public StudentData() {
    }


    public StudentData(Parcel source) {
        studentId = source.readString();
        studentName = source.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(studentId);
        parcel.writeString(studentName);
    }

    public static Creator<StudentData> CREATOR = new Creator<StudentData>() {
        public StudentData createFromParcel(Parcel source) {
            return new StudentData(source);
        }

        public StudentData[] newArray(int size) {
            return new StudentData[size];
        }
    };
}
