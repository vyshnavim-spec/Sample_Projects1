package com.example.student_grpc.service;

import com.example.student_grpc.*;
import com.example.student_grpc.model.Student;
import com.example.student_grpc.repository.StudentRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
public class StudentGrpcService extends StudentServiceGrpc.StudentServiceImplBase {

    private final StudentRepository repository;

    public StudentGrpcService(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addStudent(StudentRequest request,
                           StreamObserver<StudentResponse> responseObserver) {

        StudentResponse response;

        // Check whether the student already exists
        if (repository.getStudent(request.getId()) != null) {

            response = StudentResponse.newBuilder()
                    .setMessage("Student Already Exists")
                    .build();

        } else {

            Student student = new Student(
                    request.getId(),
                    request.getName(),
                    request.getDepartment());

            repository.addStudent(student);

            response = StudentResponse.newBuilder()
                    .setId(student.getId())
                    .setName(student.getName())
                    .setDepartment(student.getDepartment())
                    .setMessage("Student Added Successfully")
                    .build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getStudent(StudentId request,
                           StreamObserver<StudentResponse> responseObserver) {

        Student student = repository.getStudent(request.getId());

        StudentResponse response;

        if (student == null) {

            response = StudentResponse.newBuilder()
                    .setMessage("Student Not Found")
                    .build();

        } else {

            response = StudentResponse.newBuilder()
                    .setId(student.getId())
                    .setName(student.getName())
                    .setDepartment(student.getDepartment())
                    .setMessage("Student Found")
                    .build();

        }

        responseObserver.onNext(response);

        responseObserver.onCompleted();

    }

    @Override
    public void updateStudent(StudentRequest request,
                              StreamObserver<StudentResponse> responseObserver) {

        Student student = new Student(
                request.getId(),
                request.getName(),
                request.getDepartment());

        boolean updated = repository.updateStudent(student);

        StudentResponse response;

        if (updated) {

            response = StudentResponse.newBuilder()
                    .setId(student.getId())
                    .setName(student.getName())
                    .setDepartment(student.getDepartment())
                    .setMessage("Student Updated Successfully")
                    .build();

        } else {

            response = StudentResponse.newBuilder()
                    .setMessage("Student Not Found")
                    .build();

        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteStudent(StudentId request,
                              StreamObserver<DeleteResponse> responseObserver) {

        boolean deleted = repository.deleteStudent(request.getId());

        DeleteResponse response;

        if (deleted) {

            response = DeleteResponse.newBuilder()
                    .setMessage("Student Deleted Successfully")
                    .build();

        } else {

            response = DeleteResponse.newBuilder()
                    .setMessage("Student Not Found")
                    .build();

        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllStudents(Empty request,
                               StreamObserver<StudentList> responseObserver) {

        List<Student> students = repository.getAllStudents();

        StudentList.Builder listBuilder = StudentList.newBuilder();

        for (Student student : students) {

            StudentResponse response = StudentResponse.newBuilder()
                    .setId(student.getId())
                    .setName(student.getName())
                    .setDepartment(student.getDepartment())
                    .setMessage("Student Found")
                    .build();

            listBuilder.addStudents(response);
        }

        responseObserver.onNext(listBuilder.build());
        responseObserver.onCompleted();
    }

}
