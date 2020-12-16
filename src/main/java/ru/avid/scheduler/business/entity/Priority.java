package ru.avid.scheduler.business.entity;

import lombok.*;
import ru.avid.scheduler.auth.entity.User;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "PRIORITY", schema = "TASKLIST", catalog = "POSTGRES")
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Priority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "color")
    private String color;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    @OneToMany(mappedBy = "priority")
    private Collection<Task> tasks;
}
