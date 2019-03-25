/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.zpo_lab1;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Darek
 */
public interface EmployeeDAO {
    Optional<Employee> findOne(Integer id); 
    List findAll(); 
    Optional findByName(String name); 
    void delete(Employee employee); 
    void save(Employee employee);
}
