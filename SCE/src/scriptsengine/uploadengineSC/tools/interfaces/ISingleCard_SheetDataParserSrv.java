package scriptsengine.uploadengineSC.tools.interfaces;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import scriptsengine.uploadengine.exceptions.EX_General;
import scriptsengine.uploadengineSC.tools.definitions.TY_SingleCard_SheetRawData;

/**
 * 
 * Interface for a Single entity Data Parser Service where Is collection in sheet is <false>
 */
public interface ISingleCard_SheetDataParserSrv
{
	public TY_SingleCard_SheetRawData getFldValsbyWbPathandSheetName(String wbPath, String sheetName) throws EX_General;

	public TY_SingleCard_SheetRawData getFldValsbywbCtxtandSheetName(XSSFWorkbook wbCtxt, String sheetName) throws EX_General;

	public TY_SingleCard_SheetRawData getFldValsbySheetRef(XSSFSheet sheetRef) throws EX_General;

}
