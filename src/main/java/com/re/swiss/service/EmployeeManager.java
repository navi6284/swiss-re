package com.re.swiss.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.re.swiss.model.Employee;

public class EmployeeManager {

	private List<Employee> employees;

	public EmployeeManager(List<Employee> employees) {
		this.employees = employees;
	}

	public void findLongReportingLines() {
		for (Employee employee : employees) {
			int reportingLineLength = getReportingLineLength(employee.getManagerId());
			if (reportingLineLength > 4) {
				System.out.println("Employee " + employee.getFirstName() + " " + employee.getLastName()
						+ " has a reporting line too long by " + (reportingLineLength - 4) + " managers.");
			}
		}
	}

	private int getReportingLineLength(Integer managerId) {
		int length = 0;
		while (managerId != null) {
			length++;
			final Integer currentManagerId = managerId;
			Optional<Employee> manager = employees.stream().filter(e -> e.getId() == currentManagerId).findFirst();
			if (manager.isPresent()) {
				managerId = manager.get().getManagerId();
			} else {
				break;
			}
		}
		return length;
	}

	public void findManagersEarningLess() {
		for (Employee manager : employees) {
			if (manager.getManagerId() != null) {
				List<Employee> subordinates = getSubordinatesByManagerId(manager.getId());
				if (subordinates != null) {
					double averageSalary = subordinates.stream().mapToDouble(Employee::getSalary).average().orElse(0.0);
					double expectedSalary = averageSalary * 1.2;

					if (manager.getSalary() < expectedSalary) {
						System.out.println("Manager " + manager.getFirstName() + " " + manager.getLastName() + " earns "
								+ (expectedSalary - manager.getSalary()) + " less than expected.");
					}
				}

			}
		}
	}

	public void findManagersEarningMore() {
		for (Employee manager : employees) {
			if (manager.getManagerId() != null) {
				List<Employee> subordinates = getSubordinatesByManagerId(manager.getId());
				if (subordinates != null) {
					double averageSalary = subordinates.stream().mapToDouble(Employee::getSalary).average().orElse(0.0);
					double expectedSalary = averageSalary * 1.5;

					if (manager.getSalary() > expectedSalary) {
						System.out.println("Manager " + manager.getFirstName() + " " + manager.getLastName() + " earns "
								+ (manager.getSalary() - expectedSalary) + " more than expected.");
					}

				}
			}
		}
	}

	private List<Employee> getSubordinatesByManagerId(int managerId) {
		return employees.stream().filter(employee -> {
			if (employee.getManagerId() == null)
				return false;
			else
				return employee.getManagerId() == managerId;
		}).collect(Collectors.toList());
	}
}
