package com.oxxo.scr.ms.maintance.maintenance.role.repository;

import com.oxxo.scr.ms.maintance.event.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

  List<Role> findByIsActiveIsTrue();

  @Modifying
  @Query("UPDATE Role R SET R.isActive = ?1 WHERE R.id = ?2")
  void disabledOrEnabledBrand(final Boolean isActive, final Integer id);
}
