package com.example.demo.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目类(维护方)
 * 
 * @ClassName: Project
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-27 08:52:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "t_project")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String pName;

	// 由Project对象来与中间表进行关联关系的映射(使用JoinTable注解)
	// 通过JoinTable注解，来说明Project与中间表的关联关系
	// @UniqueConstraint(数组类型)：唯一性约束，数组中放的是注解类型
	// 		columnNames：注解的属性（数组类型），数组总放的是字符串（联合主键的字段名字）
	// 		joinColumns：当前对象在中间表的外键：
	// 			name：外键名称
	//			referencedColumnName：参照主键表的主键名称
	// 		inverseJoinColumns：配置对方对象在中间表的外键
	// 			name：对方的外键名称
	//			referencedColumnName：对方的参照主键表的主键名称
	/**
	 * emp		projecgt
	 * 1		1
	 * 		emps_pros
	 * 			eid + pid = 联合主键
	 * 			eid	= emp的主键
	 * 			pid	= project的主键
	 * 			1	1
	 */
	@ManyToMany
	@JoinTable(name = "emps_pros", 
		uniqueConstraints = {@UniqueConstraint(columnNames = {"e_id", "p_id"})}, 
		joinColumns= {@JoinColumn(name = "p_id", referencedColumnName = "id")},
		inverseJoinColumns = {@JoinColumn(name = "e_id", referencedColumnName = "id")}
	)
	private List<Emp> emps = new ArrayList<Emp>();

}
