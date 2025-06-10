package com.oxxo.scr.ms.maintance.maintenance.user.repository;

import com.oxxo.scr.ms.maintance.event.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  @Modifying
  @Query("UPDATE User U SET U.isActive = ?1 WHERE U.id = ?2")
  void disabledOrEnabledUser(final Boolean isActive, final Integer id);

  Optional<User> findByUsername(final String username);
}
