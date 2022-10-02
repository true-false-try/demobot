package com.example.demobot.template;

import com.example.demobot.model.LectorDegree;

import java.util.*;

public interface TemplateMessage {
    Map<Integer,String> mapTemplate = new TreeMap<>(Map.of(
            1, "Who is head of department {department_name}",
            2, "Show {department_name} statistics",
            3, "Show the average salary for the department {department_name}",
            4, "Show count of employee for {department_name}",
            5, "Global search by {template}"
    ));

    List<LectorDegree> lectorDegrees = new ArrayList<>(List.of(
            LectorDegree.ASSISTANT,
            LectorDegree.ASSOCIATE_PROFESSOR,
            LectorDegree.PROFESSOR
    ));

    default int isTheSame(String messageInput) {
        String[] arrayInput = messageInput.split(" ");
        for (Map.Entry<Integer,String> entry:
             mapTemplate.entrySet()) {
            String[] arrayDefault = entry.getValue().split(" ");
            if (arrayInput.length == arrayDefault.length) {
                for (int i = 0; i < arrayDefault.length; i++) {
                    if (arrayDefault[i].equals(arrayInput[i])) {
                        if (arrayDefault[i + 1].equals("{department_name}") || arrayDefault[i + 1].equals("{template}")) {
                            return entry.getKey();
                        }
                    }
                }
            }
        }

        return -1;
    }

}
