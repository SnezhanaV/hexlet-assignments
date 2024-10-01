package exercise.model;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

// BEGIN
@Entity
@Table(name = "task")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;
    private String description;

    @CreatedDate
    private LocalDate createdAt;

    @LastModifiedDate
    private LocalDate updatedAt;
}
// END
