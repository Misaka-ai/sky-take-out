package com.sky.aspect;

import com.sky.context.BaseContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
public class AutoInsertFillAspect {

    @Pointcut("@annotation(com.sky.annotation.AutoInsertFill)")
    public void pt1() {

    }

    @Pointcut("@annotation(com.sky.annotation.AutoUpdateFill)")
    public void pt2() {

    }


    @Before("pt1()")
    public void autoInsertFill(JoinPoint joinPoint) {

        try {
            Object[] objects = joinPoint.getArgs();
            Object object = objects[0];

            /*//暴力反射
            Field createTime = object.getClass().getDeclaredField("createTime");
            Field updateTime = object.getClass().getDeclaredField("updateTime");
            Field createUser = object.getClass().getDeclaredField("createUser");
            Field updateUser = object.getClass().getDeclaredField("updateUser");

            createTime.setAccessible(true);
            updateTime.setAccessible(true);
            createUser.setAccessible(true);
            updateUser.setAccessible(true);

            createTime.set(object, LocalDateTime.now());
            updateTime.set(object, LocalDateTime.now());
            createUser.set(object, BaseContext.getCurrentId());
            updateUser.set(object, BaseContext.getCurrentId());*/
            Class<?> aClass = object.getClass();
            Method setCreateTime = aClass.getMethod("setCreateTime", LocalDateTime.class);
            Method setUpdateTime = aClass.getMethod("setUpdateTime", LocalDateTime.class);
            Method setCreateUser = aClass.getMethod("setCreateUser", Long.class);
            Method setUpdateUser = aClass.getMethod("setUpdateUser", Long.class);

            LocalDateTime now = LocalDateTime.now();
            Long currentId = BaseContext.getCurrentId();

            setCreateTime.invoke(object, now);
            setUpdateTime.invoke(object, now);
            setCreateUser.invoke(object, currentId);
            setUpdateUser.invoke(object, currentId);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Before("pt2()")
    public void autoUpdateFill(JoinPoint joinPoint) {

        try {
            Object[] objects = joinPoint.getArgs();
            Object object = objects[0];

            /*//暴力反射
            Field createTime = object.getClass().getDeclaredField("createTime");
            Field updateTime = object.getClass().getDeclaredField("updateTime");
            Field createUser = object.getClass().getDeclaredField("createUser");
            Field updateUser = object.getClass().getDeclaredField("updateUser");

            createTime.setAccessible(true);
            updateTime.setAccessible(true);
            createUser.setAccessible(true);
            updateUser.setAccessible(true);

            createTime.set(object, LocalDateTime.now());
            updateTime.set(object, LocalDateTime.now());
            createUser.set(object, BaseContext.getCurrentId());
            updateUser.set(object, BaseContext.getCurrentId());*/
            Class<?> aClass = object.getClass();
            Method setUpdateTime = aClass.getMethod("setUpdateTime", LocalDateTime.class);
            Method setUpdateUser = aClass.getMethod("setUpdateUser", Long.class);

            LocalDateTime now = LocalDateTime.now();
            Long currentId = BaseContext.getCurrentId();


            setUpdateTime.invoke(object, now);
            setUpdateUser.invoke(object, currentId);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
