package pl.studyshare.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Answer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(min = 10, max = 2000)
    @Column(columnDefinition = "TEXT")
    private String content;

    @NotNull
    private LocalDate createdDate;

    private Integer upvotes = 0;
    private Integer downvotes = 0;

    private Boolean anonymous = false;
    private Boolean isOfficial = false;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    @Transient
    public int getScore() {
        return upvotes - downvotes;
    }
}