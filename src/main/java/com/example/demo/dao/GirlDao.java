package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Girl;

public interface GirlDao extends JpaRepository<Girl, Integer> {

}
