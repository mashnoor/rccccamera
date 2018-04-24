package net.rajit.rccccameraapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nowfel Mashnoor on 3/31/2018.
 */

public class ClassRoom {

    @SerializedName("id")
    private int id;

    @SerializedName("classroom_name")
    private String classroomName;

    @SerializedName("company_id")
    private String companyId;

    @SerializedName("department_id")
    private String departmentId;

    public String getClassroomName() {
        return classroomName;
    }

    public int getId() {
        return id;
    }
}
