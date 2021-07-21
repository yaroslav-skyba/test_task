package test_task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test_task.dao.EmployeeDao;
import test_task.model.Employee;
import test_task.service.EmployeeService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeDao employeeDao;

    @Override
    public List<Employee> findAllBySalaryGreaterThatBoss() {
        return employeeDao.findAllWhereSalaryGreaterThatBoss();
    }

    @Override
    public List<Employee> findAllByMaxSalary() {
        return employeeDao.findAllByMaxSalary();
    }

    @Override
    public List<Employee> findAllWithoutBoss() {
        return employeeDao.findAllWithoutBoss();
    }

    @Override
    public Long fireEmployee(String name) {
        Iterable<Employee> employees = employeeDao.findAll();
        final List<Employee> employeeList = new ArrayList<>();
        final AtomicLong atomicId = new AtomicLong();

        employees.forEach(employeeList::add);
        employeeList.removeIf(employee -> {
            final String checkedName = employee.getName();
            final Long id = employee.getId();
            atomicId.set(id);

            return checkedName.equals(name);
        });
        employees = employeeList;

        employeeDao.saveAll(employees);

        return atomicId.get();
    }

    @Override
    public Long changeSalary(String name) {
        Iterable<Employee> employees = employeeDao.findAll();
        final List<Employee> employeeList = new ArrayList<>();
        final AtomicLong atomicId = new AtomicLong();

        employees.forEach(employeeList::add);
        for (Employee employee : employeeList) {
            if (employee.getName().equals(name)) {
                final double nextDouble = new Random().nextDouble();
                final BigDecimal salary = BigDecimal.valueOf(nextDouble * 100);
                employee.setSalary(salary);

                final Long id = employee.getId();
                atomicId.set(id);
            }
        }

        employeeDao.saveAll(employees);

        return atomicId.get();
    }

    @Override
    public Long hireEmployee(Employee employee) {
        final Employee hiredEmployee = employeeDao.save(employee);

        return hiredEmployee.getId();
    }
}
