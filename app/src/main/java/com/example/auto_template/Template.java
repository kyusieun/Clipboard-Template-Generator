package com.example.auto_template;

import java.io.Serializable;

public class Template implements Serializable {

//{
//  "data" : [
//   {
//    "id": "고유넘버",
//    "template_name": "제목",
//    "template_content": "내용",
//    "last_edit": "마지막 작성",
//    "reference": "참조량",
//    "latest_use": "최근 사용 시점",
//    "tag": "태그"
//  }
//  {
//    "id": "고유넘버",
//    "template_name": "제목",
//    "template_content": "내용",
//    "last_edit": "마지막 작성",
//    "reference": "참조량",
//    "latest_use": "최근 사용 시점",
//    "tag": "태그"
//  },
// // 등등
//  ]
//}
   String template_name;
   String template_content;


   public Template(String template_name, String main){
      this.template_name = template_name;
      this.template_content = main;
   }
}
