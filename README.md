### TASK:
>Create a simple Spring Boot java project with the console interface for university, which consists of departments and lectors. The lectors could work in more than one department. A lector could have one degree (assistant, associate professor, professor).
All data is stored in the relational database.  
Please send us a link to the GitHub repository with your version of this project. The app should implement such commands:

1. User Input: Who is head of department {department_name}
   Answer: Head of {department_name} department is {head_of_department_name}
2. User Input: Show {department_name} statistics.
   Answer: assistans - {assistams_count}.
   associate professors - {associate_professors_count}
   professors -{professors_count}
3. User Input: Show the average salary for the department {department_name}.
   Answer: The average salary of {department_name} is {average_salary}
4. User Input: Show count of employee for {department_name}.
   Answer: {employee_count}
5. User Input: Global search by {template}.   
   Example: Global search by van
   Answer: Ivan Petrenko, Petro Ivanov

### RUN_APP:
1. You must use command ***mvn clean package -DskipTests***.
2. You should run ***docker-compose.yml*** run.

> If ***app-telegram-bot*** doesn't run, then you must run ***postgres***, and run application as ***DemobotApplication.class***.
> Link for telegram bot: ***t.me/DemoSptingJavaBot***
