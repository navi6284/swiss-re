package com.re.swiss.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.re.swiss.model.Employee;

public class CSVFileReader {

	public static List<Employee> readEmployees(String filePath) throws IOException {
		File file = new File(filePath);
		if (file == null || !file.exists()) {
			System.err.println("File not found:	" + filePath);
			return null;
		}

		List<Employee> employees = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		reader.readLine();
		while ((line = reader.readLine()) != null) {
			line = line.replace("\"", "").trim();
			String[] data = line.split(",");
			int id = Integer.parseInt(data[0]);
			String firstName = data[1];
			String lastName = data[2];
			double salary = Double.parseDouble(data[3]);
			Integer managerId = (data.length > 4 && !data[4].isEmpty()) ? Integer.parseInt(data[4]) : null;

			Employee employee = new Employee(id, firstName, lastName, salary, managerId);
			employees.add(employee);
		}

		reader.close();
		return employees;
	}
}
