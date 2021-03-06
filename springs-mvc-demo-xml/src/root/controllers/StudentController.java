package root.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import root.POJO.Student;

@Controller
@RequestMapping("/student")
public class StudentController
{
	/*Beans Initialized in dispatched-servlet.xml and injected here
	Similarly values from DB can be obtained in DAO and Injected via Auto-wiring here
	or directly in the Model as an attribute Autowired
	*/
	@Value("#{countryOptions}") 
	private Map<String, String> countryOptions;
	
	@Value("#{prLanguageOptions}") 
	private Map<String, String> prLanguageOptions;
	
	@Value("#{OSOptions}")
	private Map<String, String> OSOptions;
	
	/*
	 * Register String Trimmer Editor to all Strings for WEbdataBinder to remove
	 * leading and trailing whitespaces
	 */
	@InitBinder
	public void initBinder(WebDataBinder dataBinder)
	{
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
		
	}
	
	@GetMapping(
	    "/showForm"
	)
	public String showForm(
	        Model model
	)
	{
		// Create New Instance of Student and Bind it to the Model to be used in View
		//model.addAttribute("student", new Student());

		InitializeStudent(model);
		return "student-form";
	}
	
	@PostMapping(
	    "/processForm"
	)
	public String processForm(
	        @Valid @ModelAttribute(
	            "student"
	        ) Student student, BindingResult bRes, Model model
	)
	{
		System.out.println("Binding Result: " + bRes);
		
		if(bRes.hasErrors())
			return "student-form";
		else
		{
			model.addAttribute("message","Student Succcessfully Registered!");
			return "student-confirmation";
		}
	}
	
	@ModelAttribute
	public void InitializeStudent(
	        Model model
	)
	{
		model.addAttribute("student", new Student());
		model.addAttribute("thecountryOptions",countryOptions );
		model.addAttribute("theprLanguageOptions",prLanguageOptions );
		model.addAttribute("theOSOptions",OSOptions );
	}
}
