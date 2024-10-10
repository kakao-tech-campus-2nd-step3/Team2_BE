package jeje.work.aeatbe.repository;

import jeje.work.aeatbe.entity.UserFreeFrom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFreeFromRepository extends JpaRepository<UserFreeFrom, Long> {

}
