package com.ntu.edu.group5.ecommerce.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ntu.edu.group5.ecommerce.entity.UserSession;

@Repository
public interface SessionDao extends JpaRepository<UserSession, Integer>{

	Optional<UserSession> findByToken(String token);

	Optional<UserSession> findByUserId(Long id);

}
