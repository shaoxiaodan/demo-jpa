package com.example.demo.dao;

import java.util.Optional;

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
	
	/**
	 * 更新操作
	 * 更新girl信息
	 */
	@Test
	void testUpdateGirl(){
		Girl girl = Girl.builder().gId(1).gName("Lily'Shen").build();
		girlDao.save(girl);
	}
	
	/**
	 * 更新操作
	 * 更新boy信息
	 * 方法1：更新成功，但不合理，应该通过查询来获取对象信息
	 * 方法2：更新失败，getOne方法过期，已无法获得查询结果
	 * 方法3：更新成功。
	 */
	@Test
	void testUpdateBoy(){
		// 修改boy信息，重新分配girl
//		Boy boy = Boy.builder().bId(1).build(); // 方法1：成功：但不合理，正常情况下应该是通过查询获得数据
//		Boy boy = boyDao.getOne(1); //方法2：失败：getOne查询，但不能返回结果，也无法进入debug
		Optional<Boy> opt1 = boyDao.findById(1); // 方法3：findbyid查询，可以获取查询结果
		Boy boy = opt1.get();
		System.out.println("***** boy=" +boy.getBId() + "\t" + boy.getBName());

		// 目标分配girl
//		Girl girl = Girl.builder().gId(3).build();
//		Girl girl = girlDao.getOne(5);
		Optional<Girl> opt2 = girlDao.findById(5); // 同上，findbyid查询，可以获取查询结果
		Girl girl = opt2.get();
		System.out.println("***** girl=" +girl.getGId() + "\t" + girl.getGName());
		
		// 设置关联关系
		boy.setGirl(girl);
		
		// 更新boy
		boyDao.save(boy);
		
	}
	
	
	
	
	
}
