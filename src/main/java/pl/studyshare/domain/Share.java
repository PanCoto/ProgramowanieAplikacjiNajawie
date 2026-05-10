package pl.studyshare.domain;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;

/* REQ_ID: ENTITY: Share, SH1 */
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Share implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String token; 

    @ManyToOne(fetch = FetchType.LAZY)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    private User recipient;

    private LocalDate createdDate;
}