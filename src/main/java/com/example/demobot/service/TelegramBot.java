package com.example.demobot.service;

import com.example.demobot.model.Department;
import com.example.demobot.model.DepartmentName;
import com.example.demobot.model.Lector;
import com.example.demobot.model.LectorDegree;

import java.util.ArrayList;
import java.util.List;

public interface TelegramBot {
    List<LectorDegree> lectorDegrees = new ArrayList<>(List.of(
            LectorDegree.ASSISTANT,
            LectorDegree.ASSOCIATE_PROFESSOR,
            LectorDegree.PROFESSOR
    ));
    //1
   String responseHeadOfDepartments(DepartmentName name);
    //2
   String responseStatistic(DepartmentName name);

}
