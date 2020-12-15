package ru.avid.scheduler.business.entity;

import lombok.*;
import ru.avid.scheduler.auth.entity.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "TASK", schema = "TASKLIST", catalog = "POSTGRES")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "completed")
    private Short completed;

    @Column(name = "task_date")
    private Timestamp taskDate;

    @ManyToOne
    @JoinColumn(name = "priority_id", referencedColumnName = "id")
    private Priority priorityByPriorityId;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category categoryByCategoryId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User userDataByUserId;

}
