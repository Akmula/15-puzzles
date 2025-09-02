package ru.akmula.game.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "help")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Help {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "help_text", nullable = false)
    String helpText;

}