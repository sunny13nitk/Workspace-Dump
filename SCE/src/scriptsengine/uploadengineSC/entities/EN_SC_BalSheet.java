package scriptsengine.uploadengineSC.entities;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import modelframework.implementations.DependantObject;

@Component("EN_SC_BalSheet")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EN_SC_BalSheet extends DependantObject
{

	private int	Year;
	private String	SCCode;
	private int	Sales;
	private int	OP;
	private int	OPM;
	private int	OI;
	private int	EBITDA;
	private int	Interest;
	private int	Depreciation;
	private int	PBT;
	private int	Tax;
	private int	TaxPer;
	private int	PAT;
	private int	NPM;
	private int	CFO;
	private int	Capex;
	private int	Debt;
	private int	CashI;
	private double	EPS;
	private int	SSGR;
	private int	ROE;
	private int	ROCE;
	private double	NFAT;
	private int	ReceivableD;
	private int	ITR;
	private int	NFA;
	private int	CWIP;
	private int	ShareCapital;
	private int	DividendPaid;
	private int	DividendPayout;
	private int	RE;
	private double	PE;
	private int	MCap;
	private double	FaceValue;
	private double	BookValue;
	private int	Equity;
	private double	DERatio;
	private double	ICR;
	private int	CFI;
	private int	CFF;
	private int	NCF;
	private double	RawMToSales;
	private double	EmpCToSales;
	private double	PowFToSales;
	private double	MktgToSales;
	private double	OtherToSales;
	private double	DepToSales;

	public String getSCCode()
	{
		return SCCode;
	}

	public void setSCCode(String sCCode)
	{
		SCCode = sCCode;
	}

	public int getYear()
	{
		return Year;
	}

	public void setYear(int year)
	{
		Year = year;
	}

	public int getSales()
	{
		return Sales;
	}

	public void setSales(int sales)
	{
		Sales = sales;
	}

	public double getFaceValue()
	{
		return FaceValue;
	}

	public void setFaceValue(double faceValue)
	{
		FaceValue = faceValue;
	}

	public double getBookValue()
	{
		return BookValue;
	}

	public void setBookValue(double bookValue)
	{
		BookValue = bookValue;
	}

	public int getOP()
	{
		return OP;
	}

	public void setOP(int oP)
	{
		OP = oP;
	}

	public int getOPM()
	{
		return OPM;
	}

	public void setOPM(int oPM)
	{
		OPM = oPM;
	}

	public int getOI()
	{
		return OI;
	}

	public void setOI(int oI)
	{
		OI = oI;
	}

	public int getEBITDA()
	{
		return EBITDA;
	}

	public void setEBITDA(int eBITDA)
	{
		EBITDA = eBITDA;
	}

	public int getInterest()
	{
		return Interest;
	}

	public void setInterest(int interest)
	{
		Interest = interest;
	}

	public int getDepreciation()
	{
		return Depreciation;
	}

	public void setDepreciation(int depreciation)
	{
		Depreciation = depreciation;
	}

	public int getPBT()
	{
		return PBT;
	}

	public void setPBT(int pBT)
	{
		PBT = pBT;
	}

	public int getTax()
	{
		return Tax;
	}

	public void setTax(int tax)
	{
		Tax = tax;
	}

	public int getTaxPer()
	{
		return TaxPer;
	}

	public void setTaxPer(int taxPer)
	{
		TaxPer = taxPer;
	}

	public int getPAT()
	{
		return PAT;
	}

	public void setPAT(int pAT)
	{
		PAT = pAT;
	}

	public int getNPM()
	{
		return NPM;
	}

	public void setNPM(int nPM)
	{
		NPM = nPM;
	}

	public int getCFO()
	{
		return CFO;
	}

	public void setCFO(int cFO)
	{
		CFO = cFO;
	}

	public int getCapex()
	{
		return Capex;
	}

	public void setCapex(int capex)
	{
		Capex = capex;
	}

	public int getDebt()
	{
		return Debt;
	}

	public void setDebt(int debt)
	{
		Debt = debt;
	}

	public int getCashI()
	{
		return CashI;
	}

	public void setCashI(int cashI)
	{
		CashI = cashI;
	}

	public double getEPS()
	{
		return EPS;
	}

	public void setEPS(double ePS)
	{
		EPS = ePS;
	}

	public int getSSGR()
	{
		return SSGR;
	}

	public void setSSGR(int sSGR)
	{
		SSGR = sSGR;
	}

	public int getROE()
	{
		return ROE;
	}

	public void setROE(int rOE)
	{
		ROE = rOE;
	}

	public int getROCE()
	{
		return ROCE;
	}

	public void setROCE(int rOCE)
	{
		ROCE = rOCE;
	}

	public int getNFA()
	{
		return NFA;
	}

	public void setNFA(int nFA)
	{
		NFA = nFA;
	}

	public double getNFAT()
	{
		return NFAT;
	}

	public void setNFAT(double nFAT)
	{
		NFAT = nFAT;
	}

	public int getReceivableD()
	{
		return ReceivableD;
	}

	public void setReceivableD(int receivableD)
	{
		ReceivableD = receivableD;
	}

	public int getITR()
	{
		return ITR;
	}

	public void setITR(int iTR)
	{
		ITR = iTR;
	}

	public int getCWIP()
	{
		return CWIP;
	}

	public void setCWIP(int cWIP)
	{
		CWIP = cWIP;
	}

	public int getShareCapital()
	{
		return ShareCapital;
	}

	public void setShareCapital(int shareCapital)
	{
		ShareCapital = shareCapital;
	}

	public int getDividendPaid()
	{
		return DividendPaid;
	}

	public void setDividendPaid(int dividendPaid)
	{
		DividendPaid = dividendPaid;
	}

	public int getDividendPayout()
	{
		return DividendPayout;
	}

	public void setDividendPayout(int dividendPayout)
	{
		DividendPayout = dividendPayout;
	}

	public int getRE()
	{
		return RE;
	}

	public void setRE(int rE)
	{
		RE = rE;
	}

	public double getPE()
	{
		return PE;
	}

	public void setPE(double pE)
	{
		PE = pE;
	}

	public int getMCap()
	{
		return MCap;
	}

	public void setMCap(int mCap)
	{
		MCap = mCap;
	}

	public int getEquity()
	{
		return Equity;
	}

	public void setEquity(int equity)
	{
		Equity = equity;
	}

	public double getDERatio()
	{
		return DERatio;
	}

	public void setDERatio(double dERatio)
	{
		DERatio = dERatio;
	}

	public double getICR()
	{
		return ICR;
	}

	public void setICR(double iCR)
	{
		ICR = iCR;
	}

	public int getCFI()
	{
		return CFI;
	}

	public void setCFI(int cFI)
	{
		CFI = cFI;
	}

	public int getCFF()
	{
		return CFF;
	}

	public void setCFF(int cFF)
	{
		CFF = cFF;
	}

	public int getNCF()
	{
		return NCF;
	}

	public void setNCF(int nCF)
	{
		NCF = nCF;
	}

	public double getRawMToSales()
	{
		return RawMToSales;
	}

	public void setRawMToSales(double rawMToSales)
	{
		RawMToSales = rawMToSales;
	}

	public double getEmpCToSales()
	{
		return EmpCToSales;
	}

	public void setEmpCToSales(double empCToSales)
	{
		EmpCToSales = empCToSales;
	}

	public double getPowFToSales()
	{
		return PowFToSales;
	}

	public void setPowFToSales(double powFToSales)
	{
		PowFToSales = powFToSales;
	}

	public double getMktgToSales()
	{
		return MktgToSales;
	}

	public void setMktgToSales(double mktgToSales)
	{
		MktgToSales = mktgToSales;
	}

	public double getOtherToSales()
	{
		return OtherToSales;
	}

	public void setOtherToSales(double otherToSales)
	{
		OtherToSales = otherToSales;
	}

	public double getDepToSales()
	{
		return DepToSales;
	}

	public void setDepToSales(double depToSales)
	{
		DepToSales = depToSales;
	}

	public EN_SC_BalSheet()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public EN_SC_BalSheet(int year, String sCCode, int sales, int oP, int oPM, int oI, int eBITDA, int interest, int depreciation, int pBT, int tax,
	          int taxPer, int pAT, int nPM, int cFO, int capex, int debt, int cashI, double ePS, int sSGR, int rOE, int rOCE, double nFAT,
	          int receivableD, int iTR, int nFA, int cWIP, int shareCapital, int dividendPaid, int dividendPayout, int rE, double pE, int mCap,
	          double faceValue, double bookValue, int equity, double dERatio, double iCR, int cFI, int cFF, int nCF, double rawMToSales,
	          double empCToSales, double powFToSales, double mktgToSales, double otherToSales, double depToSales)
	{
		super();
		Year = year;
		SCCode = sCCode;
		Sales = sales;
		OP = oP;
		OPM = oPM;
		OI = oI;
		EBITDA = eBITDA;
		Interest = interest;
		Depreciation = depreciation;
		PBT = pBT;
		Tax = tax;
		TaxPer = taxPer;
		PAT = pAT;
		NPM = nPM;
		CFO = cFO;
		Capex = capex;
		Debt = debt;
		CashI = cashI;
		EPS = ePS;
		SSGR = sSGR;
		ROE = rOE;
		ROCE = rOCE;
		NFAT = nFAT;
		ReceivableD = receivableD;
		ITR = iTR;
		NFA = nFA;
		CWIP = cWIP;
		ShareCapital = shareCapital;
		DividendPaid = dividendPaid;
		DividendPayout = dividendPayout;
		RE = rE;
		PE = pE;
		MCap = mCap;
		FaceValue = faceValue;
		BookValue = bookValue;
		Equity = equity;
		DERatio = dERatio;
		ICR = iCR;
		CFI = cFI;
		CFF = cFF;
		NCF = nCF;
		RawMToSales = rawMToSales;
		EmpCToSales = empCToSales;
		PowFToSales = powFToSales;
		MktgToSales = mktgToSales;
		OtherToSales = otherToSales;
		DepToSales = depToSales;
	}

}
