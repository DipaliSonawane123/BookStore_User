package com.example.userregistrations.Repo;

import com.example.userregistrations.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    @Query(value = "SELECT * FROM user WHERE  email = :email", nativeQuery = true)
    User findByEmail(String email);
}