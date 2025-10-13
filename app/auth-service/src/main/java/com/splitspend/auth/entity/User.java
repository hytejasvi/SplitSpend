package com.splitspend.auth.entity;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  private Date createdAt;

  public User(String email, String password, Date createdAt) {
    this.email = email;
    this.password = password;
    this.createdAt = createdAt;
  }

  // the set will be useful in case of change password later
  public void setPassword(String password) {
    this.password = password;
  }
}
