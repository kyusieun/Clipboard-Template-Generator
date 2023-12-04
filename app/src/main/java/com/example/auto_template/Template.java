package com.example.auto_template;



import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.sql.Time;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Template implements Parcelable {
   public Template(){}
//  {
//    "id": "고유넘버",
//    "template_name": "제목",
//    "template_content": "내용",
//    "last_edit": "마지막 작성",
//    "reference": "참조량",
//    "latest_use": "최근 사용 시점",
//    "tags": "태그"
//    "creation_date:"최초 생성일"
//  },
//
   String title;
   String content = null;
   Timestamp last_edit = null;
   Timestamp latest_use = null;

   ArrayList<String> tag = null;
   long reference = 0;
   String id = " ";
   public Template(String id){
      this.id = id;
   }

   @NonNull
   @Override
   public String toString() {
      return "id : "+id+", reference :" + String.valueOf(reference) + ", last_edit : " + last_edit.toString() + ", tag :" + tag.toString() +
              ", latest_use : " + latest_use.toString() + ", title: " + title + ", content : " + content;
   }

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(@NonNull Parcel parcel, int i) {
      parcel.writeString(id);
      parcel.writeString(title);
      parcel.writeString(content);
      parcel.writeLong(reference);
      parcel.writeParcelable(last_edit, i);
      parcel.writeParcelable(latest_use, i);
      parcel.writeList(tag);

   }
   public static final Parcelable.Creator<Template> CREATOR
           = new Parcelable.Creator<Template>() {
      public Template createFromParcel(Parcel in) {
         return new Template(in);
      }

      @Override
      public Template[] newArray(int i) {
         return new Template[i];
      }
   };
   public Template(Parcel in){
      id = in.readString();
      title = in.readString();
      content = in.readString();
      reference = in.readLong();
      last_edit = in.readParcelable(Template.class.getClassLoader());
      latest_use = in.readParcelable(Template.class.getClassLoader());
      tag = in.readArrayList(ArrayList.class.getClassLoader(), String.class);
   }

   public String getTitle() {
      return title;
   }
   public long getReference() {
      return reference;
   }

   public ArrayList<String> getTag() {
      return tag;
   }

   public String getContent() {
      return content;
   }

   public String getId() {
      return id;
   }

   public Timestamp getLast_edit() {
      return last_edit;
   }

   public Timestamp getLatest_use() {
      return latest_use;
   }
}