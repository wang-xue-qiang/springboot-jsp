package com.banner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.banner.bean.User;
public interface UserRepository extends JpaRepository<User, Integer> {

}
