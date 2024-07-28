package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.Pet;

public interface PetDao extends JpaRepository<Pet, Integer> {

	/**
	 * 如果spring boot data jpg提供的接口JpaRepository，无法满足业务需要的时候，就需要进行自定义查询sql接口
	 * 自定义查询sql接口的名称定义，必须满足规范：findByXXX
	 * 查询需要以：findBy开头，XXX为实体类中的属性名称，如：findByPetName，findByDescription等；
	 * 以下为自定义查询sql接口
	 */

	List<Pet> findByPetName(String petName);

	List<Pet> findByDescription(String description);

	/**
	 * 多个参数的自定义查询sql接口，方法名字的定义需要用And来连接：
	 * 
	 */
	List<Pet> findByPetNameAndDescription(String petName, String description);

	/**
	 * 根据参数范围来自定义查询sql接口
	 */
	List<Pet> findByIdBetweenOrderById(int miniId, int maxId);

	/**
	 * spring boot data jpa的第三种查询方法：JPQL - Java Persistence Query Language.
	 * 
	 */

	/*
	 * 查询所有Pet对象的JPQL sql=select * from t_pet jpql=select pet from
	 * com.example.demo.domain.Pet pet 或 from com.example.demo.domain.Pet
	 */
//	@Query(value = "from com.example.demo.domain.Pet")
	@Query(value = "select pet from com.example.demo.domain.Pet pet")
	List<Pet> loadPetList();

	/*
	 * 以下方式查询，返回的列表对象为Object[]，而非Pet
	 */
	@Query(value = "select pet.id, pet.petName from com.example.demo.domain.Pet pet")
//	List<Pet> loadPetList2();
	List<Object[]> loadPetList2();

	/*
	 * 以下是基于Object[]的另外一种解决方法： 把查询到的数据，直接封装到新创建pet对象中，并返回结果集
	 */
	@Query(value = "select new com.example.demo.domain.Pet(id, petName, description) from com.example.demo.domain.Pet pet")
	List<Pet> loadPetList3();
}
