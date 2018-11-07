package com.hoya.app.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hoya.app.demo.entity.User;

@Repository
public class UserDao {
	
	public void clear() {
		jdbcTemplate.execute("delete from user");
	}
	
	public Long rowCount() {
		return jdbcTemplate.queryForObject("select count(*) from user", Long.class);
	}
	
	public Long rowCount(String username) {
		return jdbcTemplate.queryForObject("select count(*) from user where name=?", Long.class, username);
	}
	
	public User getUser(String username) {
		String sql = "select * from user where name=?";
		try {
			User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), username);
			return user;
		} catch(Exception e) {
			return null;
		}
	}
	
	public void save(User user) {
		try {
			String sql = "insert into user (`name`, `password`) values (?,?)";
			jdbcTemplate.update(sql, user.getName(), user.getPassword());
		} catch(Exception e) {
			return;
		}

		return;
	}
	
	public void updatePassword(User user) {
		try {
			String sql = "update user set password=? where name=?";
			jdbcTemplate.update(sql, user.getPassword(), user.getName());
		} catch(Exception e) {
			return;
		}
		
		return;
	}
	
	public void delete(User user) {
		try {
			String sql = "delete from user where name=? and password=?";
			jdbcTemplate.update(sql, user.getName(), user.getPassword());
		} catch(Exception e) {
			return;
		}

		return;
	}
	
	public List<User> getUserList() {
		try {
			String sql = "select * from user";
			List<User> users = jdbcTemplate.query(sql, new UserRowMapper());
			return users;
		} catch(Exception e) {
			return null;
		}
	}
	
	static class UserRowMapper implements RowMapper<User> {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			return user;
		}
	}

	@Autowired
	JdbcTemplate jdbcTemplate;
}
