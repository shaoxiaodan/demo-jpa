package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;

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
		
		Emp emp1 = empDao.getOne(18);
		Emp emp2 = empDao.getOne(19);
		
		Project pro1 = new Project();
		pro1.setPName("NOC-1");
		pro1.getEmps().add(emp1);
		pro1.getEmps().add(emp2);
		
		projectDao.save(pro1);
	}
	
	/**
	 * 更新操作
	 * 更新项目信息，关联更新项目的人员信息，可以成功更新
	 * 更新员工信息，关联更新人员所参与的项目信息，不能完成更新（原因：放弃外键维护权利）
	 * 
	 * 注意：
	 * JPA技术中，要谨慎使用Lombok插件，会导致出现一些莫名其妙的问题（无法成功更新等）
	 */
	@Test
	void testUpdateProject() {
		
		// 查询到员工信息
		Emp emp1 = empDao.getOne(18);
		Emp emp2 = empDao.getOne(19);
		Emp emp3 = empDao.getOne(20);
		
		// 更新项目信息
//		Project project = Project.builder().id(20).pName("BOS").emps(emps).build();
		Project project = new Project();
//		project.setId(15);
//		project.setPName("BOS");

		project.setId(24);
		project.setPName("NOC-2");
		
		
		// 给当前项目重新分配员工
//		project.getEmps().add(emp1);
//		project.getEmps().add(emp2);
		project.getEmps().add(emp3);
		
//		projectDao.delete(project);
		projectDao.save(project);
	}
	
	/**
	 * 更新员工信息
	 * 
	 * 注意：
	 * 该更新方法无法成功，原因是：
	 * 1，Emp对象是【被维护方】，即没有对外键进行维护的一方
	 * 2，Project对象是【维护方】，所有外键的维护，都有Project来负责
	 * 3，多对多关系中，只需要由【维护方】的一方，来完成更新即可
	 */
	@Test
	void testUpdateEmp() {
		// 更新员工信息
//		Emp emp1 = empDao.getOne(18);
		Emp emp1 = new Emp();
		emp1.setId(18);
		emp1.setEName("eee");
		
		// 查询项目信息
		Project pro = projectDao.getOne(24);
		pro.getEmps().add(emp1);
		
		// 设置员工与项目的关联关系
		emp1.getProjectList().add(pro);
		
		// 保存员工信息
		empDao.save(emp1);
	}
	
	/**
	 * 查询操作
	 * 查询项目信息，查询项目关联的员工信息
	 * 		查询项目信息时候，并没有发送select语句去查询员工信息，是为懒加载模式（默认）
	 * 查询员工信息，查询员工参与的项目信息（无法实现，理由同上：Emp不是外键关系维护方）
	 */
	@Test
	void testFindProject() {
		// 查询所有
		List<Project> list = projectDao.findAll();
		for(Project pro : list) {
			System.out.println(pro.getId() + "\t" + pro.getPName()); // 懒加载
//			System.out.println("*** emps=" + pro.toString());
			
			
			//需要使用Emp的员工信息，但如果获取的数据中，集合有空值，数据返回有问题，
//			List<Emp> emps = pro.getEmps(); 
//			if(emps != null && emps.size() >0) {
//				for(Emp emp : emps) {
//					System.out.println(emp.getId() + "\t" + emp.getEName());
//				}
//			}
		}
	}
	
	/**
	 * 查询员工信息，但只能查询员工的信息，无法获取得到员工所关联参与的项目信息
	 */
	@Test
	void testFindEmp() {
		List<Emp> emps = empDao.findAll();
		for(Emp emp : emps) {
			System.out.println(emp.getId() + "\t" + emp.getEName());
		}
	}
	
	/**
	 * 删除操作
	 * 删除项目信息，关联删除员工信息(中间表的关联数据一起删除) —— 可以删除成功
	 * 删除员工信息，关联删除员工所参与的项目信息—— 不能成功删除
	 */
	@Test
	void testDeleteProject() {
		// 查询一个project
		Project pro = projectDao.getOne(15);
		
		// 删除project
		projectDao.delete(pro);
	}
	
	/**
	 * 删除员工信息，该员工参与了项目开发
	 * 但删除操作不成功，原因如下：
	 * 1，员工emp被项目（中间表emps_pros）所引用
	 * 2，员工emp没有对外键的维护权利（被维护方）
	 * 
	 * 所有，删除员工信息，只能成功删除未被项目引用的员工数据
	 * 
	 */
	@Test
	void testDeleteEmp() {
		// 查询一个emp
		Emp emp = empDao.getOne(18);
		
		// 删除emp
		empDao.delete(emp);
	}
	
}
