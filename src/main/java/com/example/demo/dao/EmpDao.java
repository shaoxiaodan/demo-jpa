package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Emp;

public interface EmpDao extends JpaRepository<Emp, Integer> {

}
