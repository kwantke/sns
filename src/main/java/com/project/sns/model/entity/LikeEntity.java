package com.project.sns.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "\"post\"")
@SQLDelete(sql = "update \"post\" set deleted_at = now() where id = ?")
@Where(clause = "deleted_at is null")
public class LiketEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "title")
  private String title;

  @Column(name = "body", columnDefinition = "TEXT")
  private String body;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserEntity user;
  @Column(name = "registered_at")
  private Timestamp registeredAt;

  @Column(name = "updated_at")
  private Timestamp updatedAt;

  @Column(name = "deleted_at")
  private Timestamp deletedAt;

  @PrePersist
  void registeredAt() {
    this.registeredAt = Timestamp.from(Instant.now());
  }

  @PreUpdate
  void updatedAt() {
    this.updatedAt = Timestamp.from(Instant.now());
  }

  public static LiketEntity of(String title, String body, UserEntity userEntity) {
    LiketEntity entity = new LiketEntity();
    entity.setTitle(title);
    entity.setBody(body);
    entity.setUser(userEntity);
    return entity;
  }

}
