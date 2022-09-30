package com.greatlearning.controller;
import java.util.*;
import java.lang.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greatlearning.entity.Student;
import com.greatlearning.service.StudentService;

@Controller
@RequestMapping("/student")
public class  StudentsController {
	
	@Autowired
	private StudentService studentservice;
	
	@RequestMapping("/list")
	public String listStudents(Model theModel)
	{
		List<Student> student=studentservice.findAll();
		
		theModel.addAttribute("Student",student);
		
		return "list-Student";
	}
	
	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		// create model attribute to bind form data
		Student theStudent=new Student();

		theModel.addAttribute("Student",theStudent);

		return "Student-form";
	}

	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("studentId") int theId, Model theModel) {

		// get the Student from the service
		Student theStudent = studentservice.findById(theId);

		
		theModel.addAttribute("Student", theStudent);

		// send over to our form
		return "Student-form";
	}

	@PostMapping("/save")
	public String saveStudent(@RequestParam("id") int id, @RequestParam("name") String name,
			@RequestParam("department") String department, @RequestParam("country") String country) {

		System.out.println(id);
		Student theStudent;
		if (id != 0) {
			theStudent = studentservice.findById(id);
			theStudent.setName(name);
			theStudent.setDepartment(department);
			theStudent.setCountry(country);
		} else
			theStudent = new Student(name, department, country);
		// save the Student
		studentservice.save(theStudent);

		// use a redirect to prevent duplicate submissions
		return "redirect:/student/list";

	}

	@RequestMapping("/delete")
	public String delete(@RequestParam("studentId") int theId) {

		// delete the Student
		studentservice.deleteById(theId);

		// redirect to /Student/list
		return "redirect:/student/list";

	}

	@RequestMapping("/search")
	public String search(@RequestParam("name") String name, @RequestParam("country") String country, Model theModel) {

		// check names, if both are empty then just give list of all Students

		if (name.trim().isEmpty() && country.trim().isEmpty()) {
			return "redirect:/student/list";
		} else {
			
			List<Student> theStudent = studentservice.searchBy(name,country);

			// add to the spring model
			theModel.addAttribute("Student",theStudent);

			// send to list-Student
			return "list-Student";
		}

	}
}