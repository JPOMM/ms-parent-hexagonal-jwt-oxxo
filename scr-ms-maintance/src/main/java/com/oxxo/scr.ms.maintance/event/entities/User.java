package com.oxxo.scr.ms.maintance.event.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users", uniqueConstraints = @UniqueConstraint(columnNames = "username", name = "UK_Users_Username"))
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column
  private String username;

  @Column
  private String password;

  @Column
  private Boolean isActive;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "Users_Roles",
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_User_Role_User")),
      inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_User_Role_Role")),
      uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"}, name = "UK_User_Role")}
  )
  private List<Role> roles = new ArrayList<>();
}
