package com.spring.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;

import com.spring.bean.Employee;

public class EmpDao {
	JdbcTemplate template;
	
	
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	public boolean save(final Employee e) {
		String query ="insert into employee values(?,?,?,?)";
		return template.execute(query, new PreparedStatementCallback<Boolean>() {

			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, e.getId());
				ps.setString(2, e.getName());
				ps.setFloat(3, e.getSalary());
				ps.setString(4, e.getDesignation());
				return ps.execute();
			}
		});
	}
	
	public int update(Employee e) {
		 String sql="update employee set name='"+e.getName()+"', salary="+e.getSalary()+",designation='"+e.getDesignation()+"' where id="+e.getId()+"";
		return template.update(sql);  
	}
	
	public int delete(int id) {
		String sql="delete from employee where id="+id+"";
		return template.update(sql);    
	}
	
	public Employee getEmpById(int id) {
		String sql="select * from employee where id=?"; 
		return template.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<Employee>(Employee.class));
	}
	
	public List<Employee> getAllEmployees(){
		return template.query("select * from employee", new RowMapper<Employee>() {

			public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
				Employee e = new Employee();
				e.setId(rs.getInt(1));
				e.setName(rs.getString(2));
				e.setSalary(rs.getFloat(3));
				e.setDesignation(rs.getString(4));
				return e;
			}
		});
	}
	
	/** Pagination Example */
	public List<Employee> getEmpByPage(int id,int total){
		 String sql="select * from emp limit "+(id-1)+","+total;
		return template.query(sql, new RowMapper<Employee>() {

			public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
				Employee e = new Employee();
				e.setId(rs.getInt(1));
				e.setName(rs.getString(2));
				e.setSalary(rs.getFloat(3));
				e.setDesignation(rs.getString(4));
				return e;
			}
			
		}); 
	}
}
