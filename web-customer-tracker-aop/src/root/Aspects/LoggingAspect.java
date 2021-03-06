package root.Aspects;

import java.util.Collection;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import root.annotations.POJO;

@Aspect
@Component
public class LoggingAspect
{
	
	// -ADVICE
	/*
	 * Issued on Any Get Method of a DAO to log DAO method Name along with Query Parameters passed & the size of Results
	 * Object
	 */
	@AfterReturning(
	        pointcut = "all_get_MethodsDAO()", returning = "results"
	)
	public void logDAOGetters(
	        JoinPoint jp, Object results
	)
	{
		if (jp != null)
		{
			/*
			 * Later to be saved in DB with Advice Name, DAO Name, Method Name, Args concatenated and separated by '|'
			 * in String, NumRows
			 */
			String DAOName    = jp.getTarget().getClass().getSimpleName();
			String methodName = jp.getSignature().getName();
			System.out.println();
			System.out.println("- Logging ASpect -");
			System.out.println("DAO Name :" + DAOName);
			System.out.println("Method Name : " + methodName);
			
			POJO pjAnn = jp.getTarget().getClass().getAnnotation(root.annotations.POJO.class);
			if (pjAnn != null)
			{
				System.out.println("POJO - " + pjAnn.EntityName());
			}
			
			System.out.println("--Invoked DAO Arguments --");
			if (jp.getArgs() != null)
			{
				for (Object arg : jp.getArgs())
				{
					System.out.println(arg.getClass().getSimpleName() + "-" + arg.toString());
				}
			}
			
			int size = 0;
			if (results != null)
			{
				if (results instanceof Collection<?>)
				{
					size = ((Collection<?>) results).size();
				} else
					size = 1;
			}
			System.out.println(" Results Fetched from DB Size : " + size);
			System.out.println();
			
		}
	}
	
	@Before(
	    "anyControllerMethod()"
	)
	public void logControllers(
	        JoinPoint jp
	)
	{
		System.out.println();
		System.out.println("-----Controller Invoked --------");
		System.out.println("Controller - " + jp.getTarget());
		System.out.println("Method Name : " + jp.getSignature().getName());
		System.out.println();
	}
	
	// - POINTCUTS
	
	/*
	 * Any get Method Invocation on DAO - On any method in DAO.impl package Classes with Any return type and name
	 * Starting with get and having 0 or more than 0 parameters of any type
	 */
	@Pointcut(
	    "execution(* root.DAO.impl.*.get*(..))"
	)
	public void all_get_MethodsDAO(
	)
	{
		
	}
	
	// Any Controller Method
	@Pointcut(
	    "execution(* root.controllers.*.*(..))"
	)
	public void anyControllerMethod(
	
	)
	{
		
	}
	
	/*
	 * Any save Method Invocation on DAO - Any public method in DAO.impl package that does not returns anything and have
	 * name starting with save and accepts exactly 1 parameter of any type
	 */
	
	@Pointcut(
	    "execution(* root.DAO.impl.*.save*(*))"
	)
	public void anySaveMethodDAO(
	)
	{
		
	}
	
	/*
	 * Any delete Method Invocation on DAO - Any public method in DAO.impl package that does not returns anything and
	 * have name starting with save and accepts exactly 1 parameter of any type
	 */
	@Pointcut(
	    "execution( public void root.DAO.impl.*.delete*(*))"
	)
	public void anyDeleteMethodDAO(
	)
	{
		
	}
	
}
