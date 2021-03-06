package root.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;

import root.busslogic.Services.interfaces.IConfigSrv;
import root.busslogic.Services.interfaces.IFundLineSrv;
import root.busslogic.Services.interfaces.IPortfolioSrv;
import root.busslogic.entity.FundLine;
import root.busslogic.entity.FundLineItem;
import root.busslogic.session.interfaces.ISessionUser;

@Controller
@RequestMapping(
    "/fl"
)
public class FLController
{
	@Autowired
	private ISessionUser sessUserSrv;
	
	@Autowired
	private IFundLineSrv flSrv;
	
	@Autowired
	private IPortfolioSrv pfSrv;
	
	@Autowired
	private IConfigSrv configSrv;
	
	/*
	 * It is used in the form validation process. Here we add support to trim empty strings to null.
	 */
	@InitBinder
	public void initBinder(
	        WebDataBinder dataBinder
	)
	{
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	/*
	 * ----------------- GET MAPPINGS ----------------------
	 */
	@GetMapping(
	    "/main"
	)
	public String showFLHome(
	        Model model
	)
	{
		if (sessUserSrv != null)
		{
			
			model.addAttribute("flList", flSrv.getFundLinesforUser(sessUserSrv.getSessionUser()));
			model.addAttribute("userName", sessUserSrv.getSessionUser().getUserName());
		}
		return "fl-main";
	}
	
	@GetMapping(
	    "/createFL"
	)
	public String showCreateFL(
	        Model model
	)
	{
		// Set new PF - Will be tied to user using Session User Service on PF POST
		if (sessUserSrv != null)
		{
			model.addAttribute("newFL", new FundLine());
		}
		
		return "fl-create";
	}
	
	@GetMapping(
	    "/deleteFL"
	)
	public String processdeletePF(
	        @RequestParam(
	            "flid"
	        ) int flid, Model model
	)
	{
		int numdel = flSrv.deleteFundLine(flid);
		if (numdel > 0)
		{
			model.addAttribute("formSucc", numdel + " - FundLine Deleted Successfully!");
		}
		
		return "redirect:/fl/main";
	}
	
	@GetMapping(
	    "/FLdetailsShow"
	)
	public String showDetailsFL(
	        @RequestParam(
	            "flid"
	        ) int flid, Model model
	)
	{
		if (flid > 0 && flSrv != null)
		{
			try
			{
				loadFLDEtailsinModel(flid, model);
			} catch (Exception e)
			{
				model.addAttribute("formError", e.getMessage());
				
			}
		}
		return "fl-details";
	}
	
	@GetMapping(
	    "/addFLItem"
	)
	public String showaddFLItem(
	        @RequestParam(
	            "flid"
	        ) int flid, Model model
	)
	{
		if (flid > 0 && flSrv != null)
		{
			model.addAttribute("flid", flid);
			model.addAttribute("txConfig", configSrv.getFinTxnConfig());
			model.addAttribute("newFLI", new FundLineItem());
		}
		return "fli-create";
	}
	
	/*
	 * ----------------- POST MAPPINGS ----------------------
	 */
	@PostMapping(
	    "/processCreateFL"
	)
	public String processCreateFL(
	        @Valid @ModelAttribute(
	            "newFL"
	        ) FundLine newfl, BindingResult bRes, Model model
	)
	{
		
		// Validate Form for Errors- Found stay at form!
		if (bRes.hasErrors())
		{
			return "fl-create";
		} else
		{
			if (sessUserSrv != null)
			{
				// Create the PF for Session User
				try
				{
					flSrv.addFundLineforUser(newfl, sessUserSrv.getSessionUser());
					model.addAttribute("formSucc", "FundLine Added Successfully!");
				} catch (Exception e)
				{
					// Populate Model with REgistration Error to display on Registration Form
					model.addAttribute("formError", e.getMessage());
					return "fl-create";
				}
			}
		}
		return "redirect:/fl/main";
	}
	
	@PostMapping(
	    "/processCreateFLI"
	)
	public String processaddFLItem(
	        @Valid @ModelAttribute(
	            "newFLI"
	        ) FundLineItem newfli, BindingResult bRes, Model model
	)
	{
		
		// Validate Form for Errors- Found stay at form!
		if (bRes.hasErrors())
		{
			return "fli-create";
			
		} else
		{
			if (flSrv != null && newfli != null)
			{
				
				try
				{
					flSrv.addFundLineItem(newfli.getFlid(), newfli);
					loadFLDEtailsinModel(newfli.getFlid(), model);
				} catch (Exception e)
				{
					
					model.addAttribute("formError", e.getMessage());
					loadnewFLIinModel(newfli.getFlid(), model);
					return "fli-create";
				}
			}
		}
		return "fl-details";
	}
	
	/*
	 * ----------------- PRIVATE METHODS ----------------------
	 */
	
	private void loadFLDEtailsinModel(
	        int flid, Model model
	) throws Exception
	{
		FundLine fl = flSrv.getFundLinebyId(flid);
		model.addAttribute("fl", fl);
		if (fl.getFlItems() != null)
		{
			model.addAttribute("flItems", fl.getFlItems());
		}
		
	}
	
	private void loadnewFLIinModel(
	        int flid, Model model
	)
	{
		model.addAttribute("flid", flid);
		model.addAttribute("txConfig", configSrv.getFinTxnConfig());
		model.addAttribute("newFLI", new FundLineItem());
	}
	
}
