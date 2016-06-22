package com.marcin.dao.implementation;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import com.marcin.dao.DAO;
import com.marcin.dao.JobDAO;
import com.marcin.dao.mappers.JobMapper;
import com.marcin.model.Job;

public class JdbcJobDAO extends DAO implements JobDAO {


    @Override
    public int createNewJob(Job job, String name) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("create_job");
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("job_name", job.getName())
                .addValue("description", job.getDescription())
                .addValue("in_order", job.isOrder())
                .addValue("username", name);
        Map<String, Object> out = jdbcCall.execute(in);
        return (int) out.get("job_id");
    }

    @Override
    public List<Job> getUserJobs(String username) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("username", username, Types.VARCHAR);
        String sql = "select j.job_id,"
                + "			j.name,"
                + "			j.description,"
                + "			j.in_order "
                + "from jobs j, users_jobs uj "
                + "where j.job_id = uj.job_id "
                + "and uj.user_id in (select user_id from users where username = :username)";
        return jdbcTemplate.query(sql, parameters, new JobMapper());
    }

    @Override
    public void deleteJob(Job job, String username) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("delete_job");
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("job_id", job.getJobId())
                .addValue("username", username);
        jdbcCall.execute(in);
    }

    @Override
    public void updateJob(Job job, String name) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("update_job");
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("job_id", job.getJobId())
                .addValue("name", job.getName())
                .addValue("description", job.getDescription())
                .addValue("username", name);
        jdbcCall.execute(in);

    }
}
