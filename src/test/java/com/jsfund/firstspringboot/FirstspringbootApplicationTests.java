package com.jsfund.firstspringboot;

import com.jsfund.firstspringboot.entity.Student;
import com.jsfund.firstspringboot.model.Hobby;
import com.jsfund.firstspringboot.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
@Slf4j
class FirstspringbootApplicationTests {

	@Autowired
	StudentRepository studentRepository;

	@Test
	void contextLoads() {
		//saveStudent();
		findStudentByName("皓阳");
	}

	void findStudentByName(String name){
		Student student = studentRepository.findByName(name);
		log.info("查询学生{}的详细信息：{}", name, student);
	}

	void saveStudent() {
		Student student = new Student();
		student.setName("皓阳");
		student.setAge(18);
		student.setBirthday(LocalDate.parse("2013-11-21", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		student.setCreateTime(LocalDateTime.now());
		student.setUpdateTime(LocalDateTime.now());
		Hobby hobby = new Hobby();
		hobby.setName("美食");
		hobby.setContext("榴莲、火锅");
		student.setHobby(hobby);
		log.info("保存学生信息：{}", student);
		Student saveStudent = studentRepository.save(student);
		log.info("保存学生返回信息：{}",saveStudent);
	}

}
