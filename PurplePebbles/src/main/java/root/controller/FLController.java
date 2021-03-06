package root.controller;

import java.util.List;

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

import root.busslogic.SQLProc.entities.Rep_FL;
import root.busslogic.SQLProc.entities.Rep_FL_TR;
import root.busslogic.Services.interfaces.IConfigSrv;
import root.busslogic.Services.interfaces.IFundLineSrv;
import root.busslogic.Services.interfaces.IPortfolioSrv;
import root.busslogic.entity.FundLine;
import root.busslogic.entity.FundLineItem;
import root.busslogic.session.interfaces.ISessionUser;
import root.busslogic.utilities.UtilDecimaltoMoneyString;

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
			// Determine # FundLines for the User
			List<FundLine> fundLines = flSrv.getFundLinesforUser(sessUserSrv.getSessionUser());
			int            numFL     = fundLines.size();
			
			model.addAttribute("numFL", flSrv.getFundLinesforUser(sessUserSrv.getSessionUser()).size());
			
		}
		
		return "fl-mainM";
	}
	
	@GetMapping(
	    "/listFl"
	)
	public String showFLList(
	        Model model
	)
	{
		if (sessUserSrv != null)
		{
			Rep_FL_TR flDetail = null;
			// Determine # FundLines for the User
			List<FundLine> fundLines = flSrv.getFundLinesforUser(sessUserSrv.getSessionUser());
			int            numFL     = fundLines.size();
			if (numFL > 1)
			{
				
			} else
			{
				/*
				 * For a Single FL navigate to the details Try to get from Stored Procedure first
				 */
				try
				{
					Rep_FL flDetails = flSrv.getFLDetailsbyflId(fundLines.get(0).getFlid());
					if (flDetails != null)
					{
						flDetail = flDetails.transformToMoneyFormat();
						model.addAttribute("flDetail", flDetail);
					}
					
				} catch (javax.persistence.PersistenceException e)
				{
					// Create a new Blank Report Entity from Raw Fund Line Data
					flDetail = new Rep_FL_TR();
					flDetail.setFlid(fundLines.get(0).getFlid());
					if (fundLines.get(0).getBalance() > 0)
					{
						flDetail.setBalance(
						        UtilDecimaltoMoneyString.getMoneyStringforDecimal(fundLines.get(0).getBalance(), 1));
					} else
					{
						flDetail.setBalance("Nil");
					}
					flDetail.setFlD2C(0);
					flDetail.setFlUtilization(0);
					flDetail.setName(fundLines.get(0).getName());
					flDetail.setNumPf(0);
					flDetail.setTotalCredits(0);
					flDetail.setTotalDeposits(0);
					flDetail.setDescription(fundLines.get(0).getDesc());
					
				}
				
			}
			
			model.addAttribute("flDetail", flDetail);
			
		}
		
		return "fl-mainM";
	}
	
	@GetMapping(
	    "/createFL"
	)
	public String showCreateFL(
	        Model model
	)
	{
		// Set new FL - Will be tied to user using Session User Service on FL POST
		if (sessUserSrv != null)
		{
			model.addAttribute("newFL", new FundLine());
		}
		
		return "fl-createM";
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
			return "fl-createM";
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
					return "fl-createM";
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
