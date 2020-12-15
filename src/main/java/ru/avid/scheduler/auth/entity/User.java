package ru.avid.scheduler.auth.entity;

import lombok.*;
import ru.avid.scheduler.business.entity.Category;
import ru.avid.scheduler.business.entity.Priority;
import ru.avid.scheduler.business.entity.Stat;
import ru.avid.scheduler.business.entity.Task;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "user_data", schema = "tasklist", catalog = "postgres")
@Getter @Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;
    @OneToMany(mappedBy = "userDataByUserId")
    private Collection<Activity> activitiesById;
    @OneToMany(mappedBy = "userDataByUserId")
    private Collection<Category> categoriesById;
    @OneToMany(mappedBy = "userDataByUserId")
    private Collection<Priority> prioritiesById;
    @OneToMany(mappedBy = "userDataByUserId")
    private Collection<Stat> statsById;
    @OneToMany(mappedBy = "userDataByUserId")
    private Collection<Task> tasksById;
//    @OneToOne(mappedBy = "userDataByUserId")
//    private UserRole userRoleById;
}
