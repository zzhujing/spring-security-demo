package com.hujing.springsecuritytest.repository;

import com.hujing.springsecuritytest.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hj
 * @create 2019-05-01 8:07
 */
public interface UserRepository extends JpaRepository<User,Integer> {
}
