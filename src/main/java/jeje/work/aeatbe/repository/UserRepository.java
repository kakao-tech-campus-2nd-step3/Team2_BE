package jeje.work.aeatbe.repository;

import jeje.work.aeatbe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}