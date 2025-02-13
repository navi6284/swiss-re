package com.re.swiss;

import java.io.IOException;
import java.util.List;

import com.re.swiss.model.Employee;
import com.re.swiss.service.EmployeeManager;
import com.re.swiss.util.CSVFileReader;

public class SwissApplication {

	public static void main(String[] args) throws IOException {
		List<Employee> employees = CSVFileReader.readEmployees("employees.csv");
		if (employees != null) {
			EmployeeManager employeeManager = new EmployeeManager(employees);
			employeeManager.findManagersEarningLess();
			employeeManager.findManagersEarningMore();
			employeeManager.findLongReportingLines();
		}

	}
}
