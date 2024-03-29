package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.annotation.AutoInsertFill;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.CheckException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.sky.vo.EmployeeVO;
import org.apache.poi.poifs.storage.BATBlock;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;


@Service
public class EmployeeServiceImpl2 implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;


    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // TODO 后期需要进行md5加密，然后再进行比对
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(password.getBytes());

        if (!md5DigestAsHex.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (Objects.equals(employee.getStatus(), StatusConstant.DISABLE)) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }


    @Override
    public PageResult selectAllEmp(String name, Integer page, Integer pageSize) {


        PageResult pageResult = new PageResult();
        //开启分页
        PageHelper.startPage(page, pageSize);
        //原始查询
        List<Employee> employees = employeeMapper.selectAllEmp(name);
        //强制转换
        Page<Employee> page1 = (Page<Employee>) employees;

        pageResult.setRecords(page1.getResult());
        pageResult.setTotal(page1.getTotal());
       /* if (Objects.isNull(name)) {
            throw new RuntimeException("未找到相关员工");
        }*/
        return pageResult;
    }


    @Override
    public void insertEmp(EmployeeDTO employeeDTO) {
        String username = employeeDTO.getUsername();
        if (!StringUtils.hasText(username) || username.length() < 3 || username.length() > 20) {
            throw new CheckException("账号输入有误，请输入3-20个字符");
        }
        Employee employeeQuery = new Employee();
        employeeQuery.setUsername(username);
        Integer count = employeeMapper.selectCount(employeeQuery);
        if (count > 0) {
            throw new CheckException("账号重复，请调整");
        }
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes());
        employee.setPassword(md5DigestAsHex);
        employee.setStatus(1);
        employeeMapper.insert(employee);

    }

    @Override
    public PageResult getAllEmp(EmployeePageQueryDTO employeePageQueryDTO) {
        //判断传入的Page鹤PageSize是否为空
        Integer page = Objects.isNull(employeePageQueryDTO.getPage()) ? 1 : employeePageQueryDTO.getPage();
        Integer pageSize = Objects.isNull(employeePageQueryDTO.getPageSize()) ? 10 : employeePageQueryDTO.getPageSize();
        //开启分页
        PageHelper.startPage(page, pageSize);
        //原始查询
        Employee employee = new Employee();
        employee.setName(employeePageQueryDTO.getName());
        List<Employee> employeeList = employeeMapper.getAllEmp(employee);
        //封装数据
        PageResult pageResult = new PageResult();
        Page<Employee> employeePage = (Page<Employee>) employeeList;
        pageResult.setTotal(employeePage.getTotal());
        pageResult.setRecords(employeePage.getResult());

        return pageResult;
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setStatus(status);
        employee.setUpdateUser(BaseContext.getCurrentId());
        employee.setUpdateTime(LocalDateTime.now());
        employeeMapper.updateById(employee);
    }

    @Override
    public EmployeeVO getEmpById(Long id) {
        Employee employee = employeeMapper.getEmpById(id);
        EmployeeVO employeeVO = new EmployeeVO();
        BeanUtils.copyProperties(employee, employeeVO);
        return employeeVO;
    }

    @Override
    public void updateEmpById(EmployeeDTO employeeDTO) {


        Employee employee = new Employee();

        BeanUtils.copyProperties(employeeDTO, employee);

        Integer count = employeeMapper.selectCount(employee);
        if (count > 0) {
            throw new CheckException("账号重复，请调整");
        }
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.updateById(employee);
    }

    @Override
    public void updatePassword(PasswordEditDTO passwordEditDTO) {
        //对旧密码加密
        String oldPassword = DigestUtils.md5DigestAsHex(passwordEditDTO.getOldPassword().getBytes());
        //获取堂当前登陆人的ID
        Long id = BaseContext.getCurrentId();
        //通过ID查询并比对旧密码是否正确
        Employee employee = employeeMapper.selectByEmpId(id);
        if (!Objects.equals(oldPassword, employee.getPassword())) {
            throw new CheckException("输入的旧密码不正确，请重新输入");
        }
        //比对成功后将新的密码加密后存入到数据库中
        String newPassword = DigestUtils.md5DigestAsHex(passwordEditDTO.getNewPassword().getBytes());
        employee.setPassword(newPassword);
        employeeMapper.updateById(employee);

    }

}
