package test_task.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test_task.model.Department;
import java.util.List;

@Repository
public interface DepartmentDao extends CrudRepository<Department, Long> {
    @Query( value = "select id from department where (select count(*) from employee where department.id = employee.department_id) < 4",
            nativeQuery = true)
    List<Long> findAllWhereDepartmentDoesntExceedThreePeople();

    @Query( value = "select department_id from employee group by department_id having sum(salary) =" +
                    "    (select sum(salary) as sum from employee group by department_id order by sum desc limit 1);",
            nativeQuery = true)
    List<Long> findAllByMaxTotalSalary();
}
