package com.hoya.app.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hoya.app.demo.entity.User;

public interface UserRepository extends JpaRepository<User, String>{

	@Query(value="select * from user", nativeQuery=true)
	public List<User> getListBySql();
	
	@Modifying
	@Query(value="update User set password=?2 where name=?1", nativeQuery=true)
	public void updateUserBySql(String username, String password);
}
