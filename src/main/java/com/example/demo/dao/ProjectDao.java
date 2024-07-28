package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Project;

public interface ProjectDao extends JpaRepository<Project, Integer> {

}
