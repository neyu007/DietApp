package com.example.dietaryapplication;


import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
	private String name;
	private String age;
	private String gender;
	private String constraints;
	private double weight;
	private double height;
	private double activityFactor;
	private double TEA;
	private double TEACurrent; 
	private double DBW;
	private boolean isNormal;
	private long id;
	private String updateDate;

	
	public User() {

	}
	
	public User(String name, String age, String gender, String constraints,
			double weight, double height, double activityFactor, double tEA, long id, String updateDate) {
		super();
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.constraints = constraints;
		this.weight = weight;
		this.height = height;
		this.activityFactor = activityFactor;
		this.TEA = tEA;
		this.id=id;
		this.updateDate = updateDate;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getConstraints() {
		return constraints;
	}
	public void setConstraints(String constraints) {
		this.constraints = constraints;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getActivityFactor() {
		return activityFactor;
	}
	public void setActivityFactor(double activityFactor) {
		this.activityFactor = activityFactor;
	}
	public double getTEA() {
		return TEA;
	}
	public void setTEA(double tEA) {
		this.TEA = tEA;
	}

	public double getTEACurrent() {
		return TEACurrent;
	}

	public void setTEACurrent(double tEACurrent) {
		this.TEACurrent = tEACurrent;
	}

	public double getDBW() {
		return DBW;
	}

	public void setDBW(double dBW) {
		this.DBW = dBW;
	}



    public boolean isNormal() {
		return isNormal;
	}

	public void setNormal(boolean isNormal) {
		this.isNormal = isNormal;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	protected User(Parcel in) {
        name = in.readString();
        age = in.readString();
        gender = in.readString();
        constraints = in.readString();
        weight = in.readDouble();
        height = in.readDouble();
        activityFactor = in.readDouble();
        TEA = in.readDouble();
        TEACurrent = in.readDouble();
        DBW = in.readDouble();
        isNormal = in.readByte() != 0x00;
        id = in.readLong();
        updateDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(age);
        dest.writeString(gender);
        dest.writeString(constraints);
        dest.writeDouble(weight);
        dest.writeDouble(height);
        dest.writeDouble(activityFactor);
        dest.writeDouble(TEA);
        dest.writeDouble(TEACurrent);
        dest.writeDouble(DBW);
        dest.writeByte((byte) (isNormal ? 0x01 : 0x00));
        dest.writeLong(id);
        dest.writeString(updateDate);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}