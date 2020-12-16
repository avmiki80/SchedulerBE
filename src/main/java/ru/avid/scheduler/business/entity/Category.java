package ru.avid.scheduler.business.entity;

import lombok.*;
import ru.avid.scheduler.auth.entity.User;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "CATEGORY", schema = "TASKLIST", catalog = "POSTGRES")
@Getter @Setter @EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "completed_count", updatable = false)
    private Long completedCount;
    @Basic
    @Column(name = "uncompleted_count", updatable = false)
    private Long uncompletedCount;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "category")
    private Collection<Task> tasks;
}
