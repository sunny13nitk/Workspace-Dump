package pp.springboot.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "dbo.StudentGeneral")
public class Student_Family
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pdID;
	
	private String parentName;
	
	private String parentEmail;
	
	private boolean isSiblingAlumuni;
	
	private String siblingName;
	
	/*
	 * ------------------ Relations ------------------
	 */
	
	@OneToMany(fetch = FetchType.LAZY, cascade =
	{ CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, mappedBy = "familyInfo")
	
	private List<Student> student;
	
	public int getPdID(
	)
	{
		return pdID;
	}
	
	public void setPdID(
	        int pdID
	)
	{
		this.pdID = pdID;
	}
	
	public String getParentName(
	)
	{
		return parentName;
	}
	
	public void setParentName(
	        String parentName
	)
	{
		this.parentName = parentName;
	}
	
	public String getParentEmail(
	)
	{
		return parentEmail;
	}
	
	public void setParentEmail(
	        String parentEmail
	)
	{
		this.parentEmail = parentEmail;
	}
	
	public boolean isSiblingAlumuni(
	)
	{
		return isSiblingAlumuni;
	}
	
	public void setSiblingAlumuni(
	        boolean isSiblingAlumuni
	)
	{
		this.isSiblingAlumuni = isSiblingAlumuni;
	}
	
	public String getSiblingName(
	)
	{
		return siblingName;
	}
	
	public void setSiblingName(
	        String siblingName
	)
	{
		this.siblingName = siblingName;
	}
	
	public List<Student> getStudent(
	)
	{
		return student;
	}
	
	public void setStudent(
	        List<Student> student
	)
	{
		this.student = student;
	}
	
	public Student_Family(
	        String parentName, String parentEmail, boolean isSiblingAlumuni, String siblingName
	)
	{
		super();
		this.parentName       = parentName;
		this.parentEmail      = parentEmail;
		this.isSiblingAlumuni = isSiblingAlumuni;
		this.siblingName      = siblingName;
	}
	
	public Student_Family(
	)
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
}
