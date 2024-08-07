package com.project.sns.repository;


import com.project.sns.model.entity.LikeEntity;
import com.project.sns.model.entity.PostEntity;
import com.project.sns.model.entity.UserEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeEntityRepository extends JpaRepository<LikeEntity, Integer> {

  Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);

  @Query(value = "SELECT COUNT(*) FROM LikeEntity entity WHERE entity.post = :post")
  Integer countByPost(@Param("post") PostEntity post);

  List<LikeEntity> findAllByPost(PostEntity post);
}

