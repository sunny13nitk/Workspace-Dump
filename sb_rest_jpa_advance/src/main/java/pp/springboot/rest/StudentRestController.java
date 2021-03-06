package pp.springboot.rest;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pp.springboot.entity.Student;
import pp.springboot.entity.Student_Family;

/*
 * Custom Rest Controller to handle PUT method for associated entities as per below Framework Bug
 * https://stackoverflow.com/questions/58601905/create-relation-link-one-to-one-between-2-entities
 * -using-spring-data-rest
 */

@RestController
@RequestMapping("/jpa-api/students")
public class StudentRestController
{
	@Autowired
	private EntityManager entMgr;
	
	@PutMapping("/{studentId}/familyInfo/{pdId}")
	@Transactional
	public String updateStudentFamilyInfo(
	        @PathVariable int studentId, @PathVariable int pdId
	)
	{
		
		String msg = null;
		if (entMgr != null)
		{
			if (pdId > 0)
			{
				Student_Family familyInfoDb = entMgr.find(Student_Family.class, pdId);
				if (familyInfoDb != null)
				{
					Student student = entMgr.find(Student.class, studentId);
					if (student != null)
					{
						student.setFamilyInfo(familyInfoDb);
						entMgr.merge(student);
						msg = "Student Id - " + studentId + " Family Details Updated Successfully!";
					}
				}
			}
		}
		
		return msg;
	}
	
}
