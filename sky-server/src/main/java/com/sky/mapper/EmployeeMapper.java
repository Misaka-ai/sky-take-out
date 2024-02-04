package com.sky.mapper;

import com.sky.annotation.AutoInsertFill;
import com.sky.annotation.AutoUpdateFill;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     *
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /*
     * 根据ID查询
     * */
    @Select("select * from employee where id=#{id};")
    Employee selectByEmpId(Long id);

    void editPassword(PasswordEditDTO passwordEditDTO);

    /*
     * 原始查询
     * */
    List<Employee> selectAllEmp(String name);


    /*
     *判断是否重复
     * */
    Integer selectCount(Employee employeeQuery);

    @AutoInsertFill
    void insert(Employee employee);

    List<Employee> getAllEmp(Employee employee);

    /*
     * 根据ID更新
     * */
    @AutoUpdateFill
    void updateById(Employee employee);

    /*
     *
     * 根据ID查询
     * */
    @Select("select * from employee where id=#{id};")
    Employee getEmpById(Long id);
}
