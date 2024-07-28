package com.example.demo.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
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

	@Version
	private int version; // 乐观锁
	
	// mappedBy 创建一个一对多的映射关系，值为对方外键对象中的属性名称
	// 默认情况下，开启了懒加载模式：fetch = FetchType.LAZY
	// cascade = CascadeType.ALL，级联删除
	@OneToMany(mappedBy = "clazz",fetch = FetchType.EAGER, cascade = CascadeType.ALL) // EAGER为立即加载模式，非懒加载
	private List<Student> list; // 一个班级里面有多个学生

}
