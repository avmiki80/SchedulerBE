package ru.avid.scheduler.auth.entity;

import lombok.*;
import ru.avid.scheduler.business.entity.Category;
import ru.avid.scheduler.business.entity.Priority;
import ru.avid.scheduler.business.entity.Stat;
import ru.avid.scheduler.business.entity.Task;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "USER_DATA", schema = "TASKLIST", catalog = "POSTGRES")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Activity activity;
    @OneToMany(mappedBy = "user")
    @Column
    private Collection<Category> categories;
    @OneToMany(mappedBy = "user")
    private Collection<Priority> priorities;
    @OneToMany(mappedBy = "user")
    private Collection<Stat> stats;
    @OneToMany(mappedBy = "user")
    private Collection<Task> tasks;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USER_ROLE",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
//    @OneToOne(mappedBy = "userDataByUserId")
//    private UserRole userRoleById;
}
