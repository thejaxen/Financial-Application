package com.thejaxen.thejaxendemobank.repository;

import com.thejaxen.thejaxendemobank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>{

    Boolean existsByEmail(String username);

    Boolean existsByPhoneNumber(String phoneNumber);

}
