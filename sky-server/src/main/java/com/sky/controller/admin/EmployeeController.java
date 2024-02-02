package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import com.sky.vo.EmployeeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@Api(tags = "员工管理")
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @ApiOperation(value = "退出")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }


    /*
     * 分页查询员工
     *
     * */
   /* @ApiOperation("分页查询员工")
    @GetMapping("//page")
    public Result<PageResult> selectAllEmp(String name,
                                           @RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "10") Integer pageSize) {
        PageResult pageResult = employeeService.selectAllEmp(name, page, pageSize);
        return Result.success(pageResult);
    }*/
    @ApiOperation("分页查询")
    @GetMapping("/page")
    public Result<PageResult> getALlEmp(EmployeePageQueryDTO employeePageQueryDTO) {
        PageResult pageResult = employeeService.getAllEmp(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /*
     * 添加员工
     * */
    @ApiOperation(value = "添加员工")
    @PostMapping()
    public Result<?> insertEmp(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.insertEmp(employeeDTO);
        return Result.success();
    }
    /*
     * 更新员工状态
     * */

    @ApiOperation("更新员工状态")
    @PostMapping("/status/{status}")
    public Result<?> updateStatus(Long id, @PathVariable Integer status) {
        employeeService.updateStatus(id, status);
        return Result.success();
    }
    /*
    * 根据ID查询
    * */
    @ApiOperation("根据ID查询")
    @GetMapping("/{id}")
    public Result<EmployeeVO> getEmpById( @PathVariable Long id){
        EmployeeVO employeeVO=employeeService.getEmpById(id);
        return Result.success(employeeVO);
    }
}
