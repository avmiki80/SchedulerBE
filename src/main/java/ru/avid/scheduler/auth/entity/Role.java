package ru.avid.scheduler.auth.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "ROLE_DATA", schema = "TASKLIST", catalog = "POSTGRES")
@Getter @Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
//    @OneToMany(mappedBy = "roleDataByRoleId")
//    private Collection<UserRole> userRolesById;
}
