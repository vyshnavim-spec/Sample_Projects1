package com.example.student_grpc.repository;

import com.example.student_grpc.model.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudentRepository {

    private final Map<Integer, Student> students = new HashMap<>();

    public void addStudent(Student student){

        students.put(student.getId(), student);

    }

    public Student getStudent(int id){

        return students.get(id);

    }

    public List<Student> getAllStudents(){

        return new ArrayList<>(students.values());

    }

    public boolean updateStudent(Student student){

        if(!students.containsKey(student.getId()))

            return false;

        students.put(student.getId(),student);

        return true;

    }

    public boolean deleteStudent(int id){

        return students.remove(id)!=null;

    }

}
