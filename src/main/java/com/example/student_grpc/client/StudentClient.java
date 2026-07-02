package com.example.student_grpc.client;

import com.example.student_grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StudentClient implements CommandLineRunner {

    @Override
    public void run(String... args) {

        ManagedChannel channel =
                ManagedChannelBuilder
                        .forAddress("localhost",9090)
                        .usePlaintext()
                        .build();

        StudentServiceGrpc.StudentServiceBlockingStub stub =
                StudentServiceGrpc.newBlockingStub(channel);

        //ADD Student

        StudentRequest request1 = StudentRequest.newBuilder()
                .setId(1)
                .setName("Vyshu")
                .setDepartment("Information Science")
                .build();

        StudentResponse addResponse1 = stub.addStudent(request1);

        System.out.println("===== ADD STUDENT =====");
        System.out.println(addResponse1.getMessage());

        StudentRequest request = StudentRequest.newBuilder()
                .setId(2)
                .setName("Chakri")
                .setDepartment("Computer Science")
                .build();

        StudentResponse addResponse = stub.addStudent(request);

        System.out.println("===== ADD STUDENT =====");
        System.out.println(addResponse.getMessage());

        //Update Student

        StudentRequest updateRequest = StudentRequest.newBuilder()
                .setId(2)
                .setName("ChakriP")
                .setDepartment("Computer Science")
                .build();

        StudentResponse updateResponse = stub.updateStudent(updateRequest);

        System.out.println("===== UPDATE STUDENT =====");
        System.out.println(updateResponse.getMessage());


        // GET STUDENT

        StudentId id = StudentId.newBuilder()
                .setId(2)
                .build();

        StudentResponse getResponse = stub.getStudent(id);

        System.out.println("\n===== GET STUDENT =====");

        System.out.println("ID : " + getResponse.getId());
        System.out.println("Name : " + getResponse.getName());
        System.out.println("Department : " + getResponse.getDepartment());
        System.out.println("Message : " + getResponse.getMessage());

        // DELETE STUDENT
        // ==========================

        StudentId id1 = StudentId.newBuilder()
                .setId(2)
                .build();

        DeleteResponse deleteResponse = stub.deleteStudent(id1);

        System.out.println("===== DELETE STUDENT =====");
        System.out.println(deleteResponse.getMessage());
//
//        // ==========================
//        // GET DELETED STUDENT
//        // ==========================
//
        StudentResponse response = stub.getStudent(id1);

        System.out.println("\n===== GET AFTER DELETE =====");
        System.out.println(response.getMessage());
//
//        // GET ALL STUDENTS
//        // ==========================
//
        StudentList studentList = stub.getAllStudents(
                Empty.newBuilder().build()
        );

        System.out.println("===== ALL STUDENTS =====");

        for (StudentResponse student : studentList.getStudentsList()) {

            System.out.println("--------------------------");
            System.out.println("ID : " + student.getId());
            System.out.println("Name : " + student.getName());
            System.out.println("Department : " + student.getDepartment());
        }

        channel.shutdown();

    }

}
