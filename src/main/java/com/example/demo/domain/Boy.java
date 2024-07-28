package com.example.demo.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "t_boy")
public class Boy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bId;

	@Column
	private String bName;


	// 由Boy来维护外键，
	// 同时，与mappedBy相对立的需要添加@JoinColumn注解
	// name是外键id，unique是唯一值约束
	// cascade是级联配置的注解
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gid", unique = true)
	private Girl girl;
}
