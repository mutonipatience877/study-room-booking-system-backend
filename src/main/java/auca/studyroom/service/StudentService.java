package auca.studyroom.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.studyroom.domain.Student;
import auca.studyroom.repository.StudentRepository;
import auca.studyroom.repository.SupervisorRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepo;
    @Autowired
    private SupervisorRepository supervisorRepo;

    public String saveStudent(Student student) {
        if (studentRepo.existsByRegNumber(student.getRegNumber())) {
            return "A student with that regNumber already exists";
        }
        studentRepo.save(student);
        return "Student saved successfully";
    }

    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    public Optional<Student> getStudentById(UUID id) {
        return studentRepo.findById(id);
    }

    public Optional<Student> updateStudent(UUID id, Student updatedStudent) {
        Optional<Student> existingStudent = studentRepo.findById(id);
        if (existingStudent.isEmpty()) {
            return Optional.empty();
        }
        Student studentToUpdate = existingStudent.get();
        studentToUpdate.setFirstName(updatedStudent.getFirstName());
        studentToUpdate.setLastName(updatedStudent.getLastName());
        studentToUpdate.setRegNumber(updatedStudent.getRegNumber());
        studentToUpdate.setEmail(updatedStudent.getEmail());
        studentToUpdate.setFaculty(updatedStudent.getFaculty());
        return Optional.of(studentRepo.save(studentToUpdate));
    }

    public String deleteStudent(UUID id) {
        if (!studentRepo.existsById(id)) {
            return "The student with that id does not exist";
        }
        studentRepo.deleteById(id);
        return "Student deleted successfully";
    }

    // B1
    public List<Student> getStudentsByFaculty(String faculty) {
        return studentRepo.findByFacultyIgnoreCaseOrderByLastNameAsc(faculty);
    }

    // C4
    public Optional<List<Student>> getStudentsOfSupervisor(UUID supervisorId) {
        if (!supervisorRepo.existsById(supervisorId)) {
            return Optional.empty();
        }
        return Optional.of(studentRepo.findStudentsOfSupervisor(supervisorId));
    }
}