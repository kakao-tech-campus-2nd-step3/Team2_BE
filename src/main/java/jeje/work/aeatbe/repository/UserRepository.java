package jeje.work.aeatbe.repository;

import java.util.Optional;
import jeje.work.aeatbe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);
    Optional<Long> findByKakaoId(String userId);

}