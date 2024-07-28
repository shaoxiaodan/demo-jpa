package com.example.demo.service;

public interface ClazzService {

	/*
	 * 延迟session的生命周期到service层
	 * 删除班级信息，并级联删除学生信息
	 */
	void deleteClazzWithStu();
}
