package com.example.demo.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.Clazz;
import com.example.demo.domain.Student;

@SpringBootTest
public class ClazzTest {

	@Autowired
	private ClazzDao clazzDao;

	@Autowired
	private StudentDao studentDao;

	/**
	 * 添加操作
	 * 添加班级信息 
	 * 添加学生信息
	 */
	@Test
	void testAddClazz() {
		// 添加班级信息
		Clazz clazz = Clazz.builder().cName("JPA高级培训课程班").build();
		clazzDao.save(clazz);
	}

	@Test
	void testAddStudent() {
		// 添加学生信息(方法1，不带外键)
		Student stu1 = Student.builder().sName("stu1").age(20).description("Java").build();
//		studentDao.save(stu1);

		// 添加学生信息(方法2，带外键)
		Clazz clazz = Clazz.builder().cid(1).build();
		Student stu2 = Student.builder().sName("stu2").age(21).description("JPA").clazz(clazz).build();
		studentDao.save(stu2);
		
	}
	
}
