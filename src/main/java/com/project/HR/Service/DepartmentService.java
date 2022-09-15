package com.project.HR.Service;

import com.project.HR.Repostory.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

}
