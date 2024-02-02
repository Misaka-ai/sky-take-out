package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;
import com.sky.vo.EmployeeVO;

public interface EmployeeService {

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);


    /*
     * 分页查询员工
     *
     * */
    PageResult selectAllEmp(String name, Integer page, Integer pageSize);

    /*
     * 添加员工
     * */
    void insertEmp(EmployeeDTO employeeDTO);

    /*
     * 分页查询
     * */
    PageResult getAllEmp(EmployeePageQueryDTO employeePageQueryDTO);

    /*
     *更新员工状态
     * */
    void updateStatus(Long id, Integer status);

    /*
     * 根据ID查询
     * */
    EmployeeVO getEmpById(Long id);
}
