package test_task.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test_task.model.Employee;
import java.util.List;

@Repository
public interface EmployeeDao extends CrudRepository<Employee, Long> {
    @Query( value = "select * from employee where id != boss_id and salary > (select salary from employee where id = boss_id limit 1)",
            nativeQuery = true)
    List<Employee> findAllWhereSalaryGreaterThatBoss();

    @Query( value = "select * from employee as e where salary = (select max(salary) from employee where e.department_id = department_id)",
            nativeQuery = true)
    List<Employee> findAllByMaxSalary();

    @Query( value = "select * from employee as e where department_id != (select department_id from employee where id = e.boss_id) or boss_id is null",
            nativeQuery = true)
    List<Employee> findAllWithoutBoss();
}
