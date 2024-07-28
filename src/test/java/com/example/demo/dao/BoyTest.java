package com.example.demo.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.Boy;
import com.example.demo.domain.Girl;

@SpringBootTest
public class BoyTest {

	@Autowired
	private BoyDao boyDao;

	@Autowired
	private GirlDao girlDao;

	@Test
	void contextLoads() {
		System.out.println("初始化测试......");
	}

	/**
	 * 添加Girl操作，成功
	 */
	@Test
	void testAddGirl() {
//		Girl girl = Girl.builder().gName("Lily").build();
//		Girl girl = Girl.builder().gName("Aurora").build();
		Girl girl = Girl.builder().gName("Mahaqi").build();
		girlDao.save(girl);
	}

	/**
	 * 添加Boy操作，成功
	 */
	@Test
	void testAddBoy() {
		// 创建boy信息
//		Boy boy = Boy.builder().bName("Tom").build();
		Boy boy = Boy.builder().bName("Zipper").build();
		
		// 数据库存在的girl信息
		Girl girl = new Girl();
		girl.setGId(1);
		
		// 创建关联关系
		boy.setGirl(girl);
		
		// 保存boy
		boyDao.save(boy);
	}
	
	/**
	 * 级联操作：
	 * 添加boy信息，并级联添加girl信息，添加失败
	 * 解决方法：
	 * 1，在boy的实体类中，添加cascade的级联注解
	 */
	@Test
	void testAddBoyAndGirl() {
		Boy boy = Boy.builder().bName("VIVO").build();
		Girl girl = Girl.builder().gName("VIVO's gf").build();
		
		boy.setGirl(girl); // 创建关联关系
		
		boyDao.save(boy); // 保存boy
	}
}
