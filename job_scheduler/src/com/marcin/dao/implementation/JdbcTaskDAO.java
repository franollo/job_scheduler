package com.marcin.dao.implementation;

import java.sql.Types;
import java.util.List;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.marcin.dao.DAO;
import com.marcin.dao.TaskDAO;
import com.marcin.dao.mappers.TaskMapper;
import com.marcin.model.Task;

public class JdbcTaskDAO extends DAO implements TaskDAO{
	

	@Override
	public List<Task> getTasksByJobId(int jobId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("job_id", jobId, Types.INTEGER);
		String sql = "select resource_id, duration from tasks where job_id = :job_id";
		return jdbcTemplate.query(sql, parameters, new TaskMapper());
	}


	@Override
	public void createNewTask(Task task, int jobId, int order) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("create_task");
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("resource_id", task.getResourceId())
				.addValue("job_id", jobId)
				.addValue("duration", task.getSecondsDuration())
				.addValue("order", order);
		jdbcCall.execute(in);
	}

}
