package org.example.studentservice;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cardservice.Card;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "student_tb")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long cardId;

}
