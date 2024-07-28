package com.example.demo.domain;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 主键表，主表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "t_clazz")
public class Clazz {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cid;

	@Column()
	private String cName;

	@Column()
	private String description;

	@OneToMany(mappedBy = "clazz") // mappedBy 创建一个一对多的映射关系，值为对方外键对象中的属性名称
	private List<Student> list; // 一个班级里面有多个学生

}
