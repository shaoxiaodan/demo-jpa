package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.Emp;
import com.example.demo.domain.Project;

@SpringBootTest
public class EmpTest {

	@Autowired
	private EmpDao empDao;

	@Autowired
	private ProjectDao projectDao;

	@Test
	void contextLoads() {
		System.out.println("初始化测试......");
	}

	/**
	 * 添加操作
	 * 添加员工信息：以及员工参与项目的信息
	 * 添加项目信息：添加参加当前项目的员工信息
	 */
	@Test
	void testAddEmp() {

		// 1，创建员工信息
		Emp emp = Emp.builder().eName("aaa").build();
		
		// 2，项目信息
		Project pro1 = Project.builder().pName("CRM").build();
		
		// 3，员工信息与项目信息建立关联关系
		emp.getProjectList().add(pro1);
		
		// 4，保存员工信息
		empDao.save(emp);
		/*
		 * 保存不成功，原因如下：
		 * 
		 */
	}
	
	/**
	 * 通过new来创建对象，
	 * 可能会在save的过程中，发生【瞬时对象异常】
	 * 解决参考：https://zhuanlan.zhihu.com/p/562086685?utm_id=0
	 */
	@Test
	void testAddProject() {
		// 1，创建员工信息
		Emp emp1 = Emp.builder().eName("bbb").build();
		Emp emp2 = Emp.builder().eName("ccc").build();
		Emp emp3 = Emp.builder().eName("ddd").build();
		
		List<Emp> emps1 = new ArrayList<Emp>();
		emps1.add(emp1);
		emps1.add(emp2);
		
		List<Emp> emps2 = new ArrayList<Emp>();
		emps2.add(emp3);
		
		// 2，创建项目信息，并对项目信息与员工信息建立关联关系
//		Project pro1 = Project.builder().pName("CRM").emps(emps1).build();
//		Project pro2 = Project.builder().pName("ERP").emps(emps2).build();
		Project pro3 = Project.builder().pName("NOC").emps(emps2).build();
		
		// 3，保存项目信息
//		projectDao.save(pro1);
//		projectDao.save(pro2);
		projectDao.save(pro3);
	}
	
	@Test
	void testAddProject2() {
//		Optional<Emp> opt = empDao.findById(18);
//		Emp emp1 = opt.get();
		
		Emp emp1 = empDao.getOne(18);
		System.out.println("***** emp1=" + emp1.toString());
		System.out.println("***** emp1=" + emp1.getId());
		System.out.println("***** emp1=" + emp1.getProjectList());
//		opt = empDao.findById(19);
//		Emp emp2 = opt.get();

		List<Emp> emps1 = new ArrayList<Emp>();
		emps1.add(emp1);
//		emps1.add(emp2);
		 
		/*
		 * 以下代码无效
		 */
//		Project pro1 = new Project();
//		pro1.setPName("NOC");
//		pro1.getEmps().add(emp1);
//		pro1.setEmps(emps1);
//		pro1.builder();
		
		Project pro1 = Project.builder().pName("NOC").build();
		projectDao.save(pro1);
		System.out.println("***** pro1=" + pro1.getId());
		
		pro1.setEmps(emps1);
		System.out.println("***** pro1=" + pro1.getEmps());
		projectDao.save(pro1);
		
	}
}
