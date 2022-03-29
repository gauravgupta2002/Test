package com.example.test;


public class filterdata {

    private String Text,Text2;

   /* public filterdata(String text,String text2){
        Text=text;
        Text2=text2;
    }

    public String getText(){
        return Text;
    }
    public String getText2(){
        return Text2;
    }*/

    public filterdata(String text, String text2) {
        Text = text;
        Text2 = text2;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getText2() {
        return Text2;
    }

    public void setText2(String text2) {
        Text2 = text2;
    }
}
