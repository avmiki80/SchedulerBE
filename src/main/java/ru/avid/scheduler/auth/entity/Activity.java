package ru.avid.scheduler.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "ACTIVITY", schema = "TASKLIST", catalog = "POSTGRES")
@Setter @Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "activated")
    private boolean activated;

    @Column(name = "uuid", updatable = false)
    @NotBlank
    private String uuid;
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
}
