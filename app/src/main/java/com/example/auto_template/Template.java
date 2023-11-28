package com.example.auto_template;



import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;

public class Template implements Serializable {

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
   String template_id;
   String template_name;
   String template_content;
   String creation_date;
//   String last_edit;
//   String latest_use;
   com.google.firebase.Timestamp last_edit;
   com.google.firebase.Timestamp latest_use;

   ArrayList<String> tags;
   int reference_count = 0;

   public Template(String input_title, String input_content){
      //template_id = ;
      LocalDateTime tempTime = LocalDateTime.now();
      template_name = input_title;
      template_content = input_content;
//      creation_date = tempTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
//      last_edit = tempTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
      tags = new ArrayList<>();

   }
   public Template(){
      //template_id = ;
      LocalDateTime tempTime = LocalDateTime.now();
      this.template_name = "빈 템플릿";
      this.template_content = "text";
//      creation_date = tempTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
//      last_edit = tempTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
      tags = new ArrayList<>();
   }
}
