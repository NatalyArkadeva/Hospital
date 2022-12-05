package com.nataly.hospital.repository;

import com.nataly.hospital.entity.User;
import com.nataly.hospital.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByRole(Role role);

    Optional<User> findByPhoneNumber(String phone);
    Optional<User> findByNameAndSurnameAndPosition(String name, String surname, String position);

}
