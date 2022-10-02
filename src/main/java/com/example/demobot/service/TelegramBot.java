package com.example.demobot.service;

import com.example.demobot.model.Department;
import com.example.demobot.model.DepartmentName;
import com.example.demobot.model.Lector;
import com.example.demobot.model.LectorDegree;
import com.example.demobot.template.TemplateMessage;

import java.util.ArrayList;
import java.util.List;

public interface TelegramBot extends TemplateMessage {
    //1
    String responseHeadOfDepartments(DepartmentName name);
    //2
    String responseStatistic(DepartmentName name);
    //3
    String responseAvgSalaryOfDepartmentName(DepartmentName name);
    //4
    String responseCountEmployeeForDepartmentName(DepartmentName name);

}
