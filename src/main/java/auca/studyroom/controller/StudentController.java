package auca.studyroom.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import auca.studyroom.domain.Student;
import auca.studyroom.service.StudentService;

@RestController
@RequestMapping(value = "/api/students")
public class StudentController {

    @Autowired
    private StudentService studentServe;

    @PostMapping(value = "/save")
    public ResponseEntity<String> saveStudent(@RequestBody Student student) {
        String returnedMessage = studentServe.saveStudent(student);
        if (returnedMessage.equals("Student saved successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentServe.getAllStudents());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable UUID id) {
        Optional<Student> student = studentServe.getStudentById(id);
        if (student.isEmpty()) {
            return new ResponseEntity<>("The student with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(student.get(), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable UUID id, @RequestBody Student student) {
        Optional<Student> updatedStudent = studentServe.updateStudent(id, student);
        if (updatedStudent.isEmpty()) {
            return new ResponseEntity<>("The student with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedStudent.get(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable UUID id) {
        String returnedMessage = studentServe.deleteStudent(id);
        if (returnedMessage.equals("Student deleted successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
    }
}
