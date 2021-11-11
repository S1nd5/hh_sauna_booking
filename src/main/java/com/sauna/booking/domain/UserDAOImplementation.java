package com.sauna.booking.domain;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.sauna.booking.web.UserRowMapper;

@Repository
public class UserDAOImplementation {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void save(User user) {
		String sql = "insert into users(firstname, lastname, username, password, usergroup) values(?,?,?,?,?)";
		Object[] parameters = new Object[] { user.getFirstname(),
				user.getLastname(), user.getEmail(), user.getPassword(), user.getGroup() };
		jdbcTemplate.update(sql, parameters);
	}

	public User findOne(int id) {
		String sql = "select uid, firstname, lastname, username, usergroup, apartment, phonenumber from customer where uid = ?";
		Object[] parameters = new Object[] { id };
		RowMapper<User> mapper = new UserRowMapper();
		@SuppressWarnings("deprecation")
		User user = jdbcTemplate.queryForObject(sql, parameters, mapper);
		return user;
	}
	
	public User findByEmail(String email) {
		String sql = "select uid, firstname, lastname, usergroup, apartment, phonenumber from customer where username = ?";
		Object[] parameters = new Object[] { email };
		RowMapper<User> mapper = new UserRowMapper();
		@SuppressWarnings("deprecation")
		User user = jdbcTemplate.queryForObject(sql, parameters, mapper);
		return user;
	}

	public List<User> findAll() {
		String sql = "select uid, firstname, lastname, usergroup, apartment, phonenumber from users";
		RowMapper<User> mapper = new UserRowMapper();
		List<User> users = jdbcTemplate.query(sql, mapper);
		return users;
	}
}