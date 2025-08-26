package ru.akmula.score.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NewScoreDto {

    @Size(min = 3, max = 5, message = "Уровень сложности должен быть от трех до пяти!")
    @NotNull(message = "Уровень сложности не может быть пустым!")
    Long level;

    @Size(min = 1, message = "Количество выполненных шагов, должно быть больше 1!")
    @NotNull(message = "Количество выполненных шагов не может быть пустым!")
    Long score;

    @Size(min = 1, message = "Время игры должно быть должно быть больше 1!")
    @NotBlank(message = "Время игры не может быть пустым!")
    String gameTime;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @NotNull(message = "Дата и время игры не может быть пустым!")
    LocalDateTime created;
}