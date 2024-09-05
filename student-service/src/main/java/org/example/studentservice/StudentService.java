package org.example.studentservice;

import org.example.cardservice.Card;
import org.example.common.exception.CustomNotfoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CardClient cardClient;

    public StudentResponse createStudent(Student student) {
        // Verify that the Card exists before associating it with the Student
        Card card = cardClient.getCardById(student.getCardId());
        if (card == null) {
            throw new CustomNotfoundException("Card not found");
        }
        Student student1= studentRepository.save(student);
        StudentResponse studentResponse = new StudentResponse(student1, card);
        return studentResponse;
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setName(updatedStudent.getName());
                    student.setCardId(updatedStudent.getCardId());
                    return studentRepository.save(student);
                })
                .orElseThrow(() -> new CustomNotfoundException("Student not found"));
    }

    public void deleteStudent(Long id) {
        // First, find the student and get the associated card ID
        Optional<Student> studentOptional = studentRepository.findById(id);

        if (studentOptional.isPresent()) {
            Long cardId = studentOptional.get().getCardId();

            // Delete the student
            studentRepository.deleteById(id);

            // Then, delete the associated card
            try {
                cardClient.deleteCardById(cardId);
            } catch (Exception e) {
                throw new CustomNotfoundException("Failed to delete associated card with ID " + cardId);
            }
        } else {
            throw new CustomNotfoundException("Student not found with ID " + id);
        }
    }
}
