package com.example.studying.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class FundHeavyInfo implements Parcelable {
    private String id;
    private String name;
    private String full_name;
    private String legal_person;
    private String manager;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_nameame(String full_name) {
        this.full_name = full_name;
    }

    public String getLegal_person() {
        return legal_person;
    }

    public void setLegal_person(String legal_person) {
        this.legal_person = legal_person;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    /*@Override
    public String toString() {
        return "Gate [id=" + id + ", name=" + name + "]";
    }
*/
    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel arg0, int arg1) {
        // TODO Auto-generated method stub
        // 1.必须按成员变量声明的顺序封装数据，不然会出现获取数据出错
        // 2.序列化对象
        arg0.writeString(id);
        arg0.writeString(name);
    }


    // 1.必须实现Parcelable.Creator接口,否则在获取Person数据的时候，会报错，如下：
    // android.os.BadParcelableException:
    // Parcelable protocol requires a Parcelable.Creator object called  CREATOR on class com.um.demo.Person
    // 2.这个接口实现了从Percel容器读取Person数据，并返回Person对象给逻辑层使用
    // 3.实现Parcelable.Creator接口对象名必须为CREATOR，不如同样会报错上面所提到的错；
    // 4.在读取Parcel容器里的数据事，必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
    // 5.反序列化对象
    public static final Parcelable.Creator<FundHeavyInfo> CREATOR = new Creator(){

        @Override
        public FundHeavyInfo createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            // 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
            FundHeavyInfo p = new FundHeavyInfo();
            p.setId(source.readString());
            p.setName(source.readString());
            return p;
        }

        @Override
        public FundHeavyInfo[] newArray(int size) {
            // TODO Auto-generated method stub
            return new FundHeavyInfo[size];
        }
    };



}
