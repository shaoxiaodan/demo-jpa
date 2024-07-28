package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Boy;

public interface BoyDao extends JpaRepository<Boy, Integer> {

}
