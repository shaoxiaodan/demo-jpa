package com.example.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "t_girl")
public class Girl {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int gId;

	@Column
	private String gName;

	// 任何一方建立外键维护都可以，
	// 因为one to one关系，双方关系对等
	// 当前的Girl来mappedBy，也就是放弃外键维护权利(Boy来维护外键)
	@OneToOne(mappedBy = "girl")
	private Boy boy;

//	@Version
//	private Long version; // 乐观锁
}
