package jeje.work.aeatbe.repository;

import jeje.work.aeatbe.entity.UserAllergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAllergyRepository extends JpaRepository<UserAllergy, Long> {

}
