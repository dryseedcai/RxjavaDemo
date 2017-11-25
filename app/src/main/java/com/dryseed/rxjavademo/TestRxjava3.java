//package com.dryseed.rxjavademo;
//
//import android.util.Log;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import rx.Observable;
//import rx.functions.Action1;
//import rx.functions.Func1;
//
///**
// * Created by caiminming on 2017/11/24.
// * <p>
// * Rxjava1.0 map、flatMap
// */
//
//public class TestRxjava3 {
//    public static void test1() {
//        Observable.just("1", "2")
//                .map(new Func1<String, Integer>() {
//                    @Override
//                    public Integer call(String s) {
//                        return s.hashCode();
//                    }
//                })
//                .map(new Func1<Integer, String>() {
//                    @Override
//                    public String call(Integer integer) {
//                        return "hashcode : " + integer;
//                    }
//                })
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        Log.d("MMM", s);
//                    }
//                });
//    }
//
//    public static void test2() {
//        Course courseChina = new Course(1, "语文课");
//        Course courseEnglish = new Course(2, "英文课");
//        ArrayList<Course> list1 = new ArrayList<>();
//        list1.add(courseChina);
//        list1.add(courseEnglish);
//        ArrayList<Course> list2 = new ArrayList<>();
//        list2.add(courseChina);
//        Student student1 = new Student();
//        student1.name = "student1";
//        student1.coursesList = list1;
//        Student student2 = new Student();
//        student2.name = "student2";
//        student2.coursesList = list2;
//
//        //打印所有学生所修个课程名
//        Observable.just(student1, student2)
//                .map(new Func1<Student, List<Course>>() {
//                    @Override
//                    public List<Course> call(Student student) {
//                        return student.coursesList;
//                    }
//                })
//                .subscribe(new Action1<List<Course>>() {
//                    @Override
//                    public void call(List<Course> courses) {
//                        for (Course c : courses) {
//                            Log.d("MMM", c.name);
//                        }
//                    }
//                });
//
//        Log.d("MMM", "----------------------");
//
//        Observable.just(student1, student2)
//                .flatMap(new Func1<Student, Observable<Course>>() {
//                    @Override
//                    public Observable<Course> call(Student student) {
//                        return Observable.from(student.coursesList);
//                    }
//                })
//                .subscribe(new Action1<Course>() {
//                    @Override
//                    public void call(Course course) {
//                        Log.d("MMM", course.name);
//                    }
//                });
//    }
//
//
//}
//
///**
// * 学生类
// */
//class Student {
//    String name;//姓名
//    List<Course> coursesList;//所修的课程
//}
//
///**
// * 课程类
// */
//class Course {
//    String name;//课程名
//    int id;
//
//    Course(int id, String name) {
//        this.id = id;
//        this.name = name;
//    }
//}
