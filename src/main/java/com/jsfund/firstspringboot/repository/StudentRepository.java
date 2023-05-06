package com.jsfund.firstspringboot.repository;

import com.jsfund.firstspringboot.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Administrator
 * @create 2023/4/30 8:27
 */
public interface StudentRepository extends JpaRepository<Student,String>, JpaSpecificationExecutor<Student> {

    Student findByUuid(String uuid);

    Student findByName(String name);
}
