package com.kevindai.test;

import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.*;

/**
 * 乱七八糟的测试类
 * Created by daiwenkai on 2017/2/10.
 */
public class Test {
    public static void main(String[] args) {
        //测试序列化、反序列化
        //serializeBean();
        //ClassB classB = deSerialzeBean();
        //System.out.println(classB.getName());

        /*String str = "abc,def,ghi,xyz,,test,,";
        String[] strs = str.split(",");
        System.out.println(strs.length);
        for (String s : strs){
            System.out.println(s);
            System.out.println(s.length());
        }*/

        //深复制
        /*ClassA classA = new ClassA();
        classA.setName("kevindai");
        classA.setEmail("741007187@qq.com");
        classA.setPass("dai777");
        ClassA classB = new ClassA();
        BeanUtils.copyProperties(classA,classB);
        System.out.println(classB.getName());
        classB.setName("test");
        System.out.println(classB.getName());
        System.out.println(classA.getName());*/

        /*ArrayList<String> list = new ArrayList<String>();
        list.add("str1");
        list.add("str2");
        list.add("str3");
        list.add("str4");*/
        //List<String> list2 = list.subList(0,2);
        // ArrayList<String> list2 = (ArrayList)list.subList(0,2);
        //list.add("str5");
//        for (String str : list2){
//            System.out.println(str);
//        }

        //list转array的正确用法
        /*String[] str = (String[]) list.toArray();
        for (String s : str){
            System.out.println(s);
        }*///错误用法
        /*String[] strs = new String[list.size()];
        strs = list.toArray(strs);
        for (String s: strs){
            System.out.println(s);
        }
        List<String> list1 = Arrays.asList(strs);
        list1.add("ddd");*///正确用法

        //删除list制定元素
        /*Iterator it = list.iterator();
        while (it.hasNext()){
            if("str1".equals(it.next())){
                it.remove();
            }
        }
        for (String s : list) {
            System.out.println(s);
        }*/

        /*Map<String,String> map = new HashMap();
        map.put("key1","val1");
        map.put("key2","val2");
        map.put("key3","val3");
        map.put("key4","val4");
        for(Map.Entry<String,String> entry : map.entrySet()){
            String key = entry.getKey();
            String val = entry.getValue();

            System.out.println(key);
            System.out.println(val);
        }*/
        int a = 10;
        int b = 20;
        method(a,b);
        System.out.println(a);
        System.out.println(b);
    }
    private static void method(int a,int b){
        System.setOut(new PrintStream(System.out, true) {
            @Override
            public void println(String x) {
                super.println(x + '0');
            }
        });
    }

    private static void serializeBean(){
        ClassA classA = new ClassA();
        classA.setName("kevindai");
        classA.setEmail("741007187@qq.com");
        classA.setPass("dai777");

        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(new File("e:/test/classA.txt")));
            outputStream.writeObject(classA);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ClassB deSerialzeBean(){
        ObjectInputStream ois = null;
        ClassB person = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(new File("e:/test/classA.txt")));
            try {
                person = (ClassB) ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("Person对象反序列化成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return person;
    }
}
