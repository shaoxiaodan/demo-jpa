package com.example.demo.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 员工类(被维护方)
 * 
 * @ClassName: Emp
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-27 08:49:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "t_emp")
public class Emp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String eName;

	// 当前Emp对象放弃外键的维护权利，
	// 由Project对象来负责对外键进行维护
	// 由于是【多对多】的关系，可以选择任一对象来维护外键（双方对象权利对等）
	@ManyToMany(mappedBy = "emps")
	private List<Project> projectList = new ArrayList<Project>();
}
