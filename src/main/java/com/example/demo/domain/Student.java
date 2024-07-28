package com.example.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 外键表：子表
 * 外键列=主键表的主键字段cid
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "t_student")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sid;

	@Column
	private String sName;

	@Version
	private int version; // 乐观锁

	@ManyToOne
	@JoinColumn(name = "cid") // 增加（外键）列，如果不指定name属性，则为clazz
	private Clazz clazz; // 多个学生对象属于同一个班级

}
