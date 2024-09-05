package org.example.studentservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cardservice.Card;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {
    private Long id;
    private String name;
    private Card card;
    public StudentResponse(Student student, Card card) {
        this.id = student.getId();
        this.name = student.getName();
        this.card = card;
    }
}
