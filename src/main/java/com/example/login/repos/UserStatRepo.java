package com.example.login.repos;


import com.example.login.domain.UserStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface UserStatRepo extends JpaRepository<UserStat, Integer> {
    List<UserStat> findByPersonId(Long id);

}
