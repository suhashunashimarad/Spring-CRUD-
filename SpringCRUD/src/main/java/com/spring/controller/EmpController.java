package com.spring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spring.bean.Employee;
import com.spring.dao.EmpDao;

@Controller
public class EmpController {
	
	@Autowired
	EmpDao dao;		/** will inject dao from XML file */
	
	 @RequestMapping("/empform")
	 public ModelAndView showForm(HttpServletRequest req,HttpServletResponse res) throws Exception
	 { 
		 ModelAndView mv = new ModelAndView("empform");
	     mv.addObject("command", new Employee()); 
	     return mv; 
	 }
	
//	@RequestMapping("/empform")
//	public String showForm(Model m) {
//		m.addAttribute("command", new Employee());
//		return "empform";
//	}
	
	/** It saves object into database.
	 *  The @ModelAttribute puts request data intomodel object
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String save(@ModelAttribute("emp") Employee emp) {    
		dao.save(emp);
		return "redirect:/viewemp";
	}
	
	@RequestMapping("/viewemp")
	public String viewEmp(Model model) {
		List<Employee> list = dao.getAllEmployees();
		model.addAttribute("list", list);
		return "viewemp";
	}
	
	/** It displays object data into form for the given id.   
     * The @PathVariable puts URL data into variable.*/  
	
	@RequestMapping(value="/editemp/{id}")
	public String edit(@PathVariable int id,Model m) {
		/** Pagination Example 1 2 3*/
//		int total = 5;
//		if(id==1) {}
//		else {
//			id = (id-1)*total + 1; 
//		}
//		System.out.println(id);
//		List<Employee> list = dao.getEmpByPage(id,total);
//		m.addAttribute("command",list);
//		return "empeditform";
		
		Employee emp = dao.getEmpById(id);
		m.addAttribute("command", emp);
		return "empeditform";
	}
	
	@RequestMapping(value="/editsave", method=RequestMethod.POST)
	public String editSave(@ModelAttribute("emp") Employee emp) {
		dao.update(emp);
		return "redirect:/viewemp";
	}
	
	@RequestMapping(value="/deleteemp/{id}", method=RequestMethod.GET)
	public String delete(@PathVariable int id) {
		dao.delete(id);
		return "redirect:/viewemp";
	}
}
