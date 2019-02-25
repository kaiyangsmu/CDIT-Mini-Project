package com.example1.demo.DAO;

import com.example1.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean insertUser(User user) {
        String sql = "INSERT INTO user_table VALUES (?,?)";
        int rowsAffected = jdbcTemplate.update(sql, new Object[]{
                user.getName(),
                user.getSalary()
        });
        return rowsAffected != 0;
    }

    public List<User> getAllUsersWithSpecificSalary(double lowerBound, double higherBound) {
        String sql = "SELECT * from user_table where salary >= ? and salary <= ?";
        return this.jdbcTemplate.query(sql,  new Object[] { lowerBound,higherBound },
                new BeanPropertyRowMapper(User.class));
    }
}
