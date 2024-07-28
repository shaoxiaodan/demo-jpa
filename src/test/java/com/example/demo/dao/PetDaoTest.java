package com.example.demo.dao;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.demo.domain.Pet;

@SpringBootTest
public class PetDaoTest {

	@Autowired
	private PetDao petDao;

	@Test
	public void testAddPet() {
		Pet pet = Pet.builder().petName("aaa").description("this is a aaa.").build();
		petDao.save(pet);
	}

	@Test
	public void testUpdatePet() {
//		Pet pet = Pet.builder().id(2).petName("bbb").build();
		Pet pet = Pet.builder().id(20).petName("ccc").build();
		petDao.save(pet);
	}

	@Test
	public void testFindPet() {

		// 根据id查找记录
//		Optional<Pet> opt = petDao.findById(1);

		// 如果查不到数据，返回异常
		Optional<Pet> opt = petDao.findById(10);
		Pet pet = opt.get();
		System.out.println(pet);
	}

	@Test
	public void testFindAllPet() {
		// 列表查询
//		List<Pet> list = petDao.findAll();

		// 排序列表查询
		List<Pet> list = petDao.findAll(Sort.by("petName"));

		for (Pet pet : list) {
			System.out.println(pet);
		}

	}

	@Test
	public void testFindAllPetByPage() {
		Pageable pageable = PageRequest.of(1, 2, Sort.Direction.DESC, "id");
		Page<Pet> petPage = petDao.findAll(pageable);
		System.out.println(petPage);
	}

	@Test
	public void testDeletePet() {
		Pet pet = Pet.builder().id(3).build();
		petDao.delete(pet);

//		petDao.deleteById(3);  // 等效删除
	}

	@Test
	public void testFindByPetName() {
		String name = "bbb";
		List<Pet> list = petDao.findByPetName(name);
		System.out.println(list);

	}

	@Test
	public void testFIndById2() {
		List<Pet> list = petDao.findByIdBetweenOrderById(1, 5);
		System.out.println(list);
	}

	@Test
	public void testLoadPetList() {
		List<Pet> list = petDao.loadPetList();
		System.out.println(list);
	}

	@Test
	public void testLoadPetList2() {
//		List<Pet> list = petDao.loadPetList2();
		List<Object[]> list = petDao.loadPetList2();
		for (Object[] pet : list) {
			System.out.println(Arrays.toString(pet));
		}
	}
	
	@Test
	public void testLoadPetList3() {
		List<Pet> list = petDao.loadPetList3();
		System.out.println(list);
	}

}
