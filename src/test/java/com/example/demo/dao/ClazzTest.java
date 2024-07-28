package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;

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
//		Clazz clazz = Clazz.builder().cName("JPA高级培训课程班").build();
		Clazz clazz = Clazz.builder().cName("疯狂Java培训班").build();
		clazzDao.save(clazz);
	}

	@Test
	void testAddStudent() {
		// 添加学生信息(方法1，不带外键)
//		Student stu1 = Student.builder().sName("stu1").build();
//		studentDao.save(stu1);

		// 添加学生信息(方法2，带外键)
		Clazz clazz = Clazz.builder().cid(2).build();
//		Student stu2 = Student.builder().sName("stu2").clazz(clazz).build();
//		studentDao.save(stu2);
		
		Student stu3 = Student.builder().sName("stu3").clazz(clazz).build();
		studentDao.save(stu3);
		
		Student stu4 = Student.builder().sName("stu4").clazz(clazz).build();
		studentDao.save(stu4);
	}
	
	/**
	 * 一对多的查询操作
	 * 查询信息
	 * 查询班级信息（关联查询了学生信息）
	 * 查询学生信息
	 */
	@Test
	void testFindClazz() {
		// sql=select c1_0.cid,c1_0.c_name,c1_0.description from t_clazz c1_0
		// 在查询语句中，只查询了班级信息，并没有查询班级中的学生信息
		// 所以，以上的查询方式为【懒加载】查询（没有查询出student的信息）
		// 懒加载: 	查询班级信息，并没有发送selectg语句查询班级当中的学生信息
		// 			只有当我们使用到学生信息的时候(调用getXXX)，才会发送select语句来查询学生信息
		List<Clazz> clazzes = clazzDao.findAll();
		
		for(Clazz clazz : clazzes) {
			System.out.println(clazz.getCid() + "\t" +  clazz.getCName());
//			System.out.println(clazz.getCid() + "\t" +  clazz.getCName() + "\t" + clazz.getList());
			
			List<Student> students = clazz.getList();
			for(Student stu : students) {
				System.out.println("stu=" + stu.getSid() + "\t" + stu.getSName());
			}
		}
		
		/**
		 * 总结：
		 * 一般情况下，查询【一的】对象数据，不需要立即加载【多的】数据，性能会下降；
		 * 建议使用默认值【懒加载】。
		 */
	}
	
	/**
	 * 查询学生信息
	 */
	@Test
	void testFindStudent() {
		List<Student> students = studentDao.findAll();
		for(Student stu : students) {
			// 没有使用学生所属的班级信息，但对班级信息也进行了查询 - 立即加载
			// 原因是：【多的】ManyToOne的一方，默认是立即加载模式
			System.out.println("stu=" + stu.getSid() + "\t" + stu.getSName());
			
			// 测试立即加载
			System.out.println("clazz=" + stu.getClazz().getCid());
		}
	}
	
	/**
	 * 一对多的删除操作
	 * 删除班级信息：主键表（主键字段可能被引用）
	 * 删除学生信息：外键表（子表）
	 */
	@Test
	void testDeleteClazz() {
		// 删除班级信息
		// case 1：没有被引用的班级，可以正常删除
		// case 2：有被引用的班级（外键有值），不能被删除
//		clazzDao.deleteById(1);
		
		// 解决方法1，级联删除（实体类配置cascade注解）
		clazzDao.deleteById(2);
		
		// 解决方法2，先断开主外键连接，再去删除主表当中的数据
		// 步骤1：断开主外键连接，update语句更新子表中的外键值
		// 步骤2：执行删除操作，删除主表中的数据
	}
	
	@Test
	void testDeleteStudent() {
		// 删除子表数据，无须要考虑主表的外键值cid，可以直接删除
		// 但要保证主表的fetch策略为懒加载模式，否则会删除不成功
		studentDao.deleteById(2);
	}
	
	/**
	 * 一对多的添加操作
	 * 添加班级信息的同时，也添加学生信息
	 */
	@Test
	void testAddClazzAndStudent() {
		// 1，创建班级信息
		Clazz clazz = Clazz.builder().cName("NAU计算机硕士学历班").build();
		
		// 2，创建学生信息
		Student stu1 = Student.builder().sName("stu7").build();
		Student stu2 = Student.builder().sName("stu8").build();
		Student stu3 = Student.builder().sName("stu9").build();
		Student stu4 = Student.builder().sName("stu10").build();
		
		// 2-1，将学生信息装入到list集合中
		List<Student> studentList = new ArrayList<Student>();
		studentList.add(stu1);
		studentList.add(stu2);
		studentList.add(stu3);
		studentList.add(stu4);
		
		// 3，创建班级与学生的关联关系
		clazz.setList(studentList);
		
		// 4，创建学生与班级的关联关系（双向关联）
		// 如果不创建双向关联，保存成功，但子表不会有主表cid的外键值（外键没有得到维护，值为空）
		stu1.setClazz(clazz);
		stu2.setClazz(clazz);
		stu3.setClazz(clazz);
		stu4.setClazz(clazz);
		
		// 5，保存班级信息
		clazzDao.save(clazz);
	}
	
}
