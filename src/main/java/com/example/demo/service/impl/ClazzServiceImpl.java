package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ClazzDao;
import com.example.demo.dao.StudentDao;
import com.example.demo.domain.Clazz;
import com.example.demo.domain.Student;
import com.example.demo.service.ClazzService;

import jakarta.transaction.Transactional;

@Service
public class ClazzServiceImpl implements ClazzService {

	@Autowired
	private ClazzDao clazzDao;

	@Autowired
	private StudentDao studentDao;

	@Override
	@Transactional // 给当前方法添加事务，方法体内的所有操作都会在同一个session当中完成
	// 原来的session是在dao层方法执行完毕后关闭，而现在是servcie层方法执行完毕后才关闭session
	public void deleteClazzWithStu() {
		/*
		 * 删除班级，以及删除班级信息中关联的学生信息
		 */
		List<Clazz> clazzes = clazzDao.findAll();
		for (Clazz clazz : clazzes) {
			// 1，先删除学生
			// 获取班级的学生列表，
			// 然后遍历删除所有学生信息
			List<Student> students = clazz.getList(); // 注意：这里会产生no session的异常！！解决方法是使用@Transactional注解来进行事务控制
			for (Student stu : students) {
				studentDao.delete(stu);
			}

			// 2，再删除班级
			// 断开主外键连接 - 把班级的学生列表设置为null
			// 然后开始调用deletebyid，把班级信息删除
			clazz.setList(null);
			clazzDao.deleteById(clazz.getCid());
		}

	}

}
