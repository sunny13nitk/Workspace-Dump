package scriptsengine.uploadengineSC.scripSheetServices.implementations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import modelframework.definitions.Object_Info;
import modelframework.exceptions.EX_InvalidRelationException;
import modelframework.exposed.FrameworkManager;
import modelframework.implementations.DependantObject;
import modelframework.implementations.MessagesFormatter;
import modelframework.utilities.PropertiesMapper;
import scriptsengine.uploadengine.exceptions.EX_General;
import scriptsengine.uploadengineSC.Metadata.definitions.SCSheetMetadata;
import scriptsengine.uploadengineSC.Metadata.definitions.SheetFieldsMetadata;
import scriptsengine.uploadengineSC.Metadata.services.implementations.SCWBMetadataSrv;
import scriptsengine.uploadengineSC.entities.EN_SC_General;
import scriptsengine.uploadengineSC.scripSheetServices.interfaces.ISCCode_Getter_XLS;
import scriptsengine.uploadengineSC.scripSheetServices.interfaces.ISCExistsDB_Srv;
import scriptsengine.uploadengineSC.scripSheetServices.interfaces.IXLS_Scrip_Update_Srv;
import scriptsengine.uploadengineSC.tools.definitions.FldVals;
import scriptsengine.uploadengineSC.tools.definitions.SheetFldValsHeadersList;
import scriptsengine.uploadengineSC.tools.definitions.TY_SingleCard_SheetRawData;
import scriptsengine.uploadengineSC.tools.definitions.TY_WBRawData;
import scriptsengine.uploadengineSC.tools.interfaces.IHeadersDeltaGetSrv;
import scriptsengine.uploadengineSC.tools.interfaces.IWBRawDataSrv;

@Service("XLS_Scrip_Update_Srv")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class XLS_Scrip_Update_Srv implements IXLS_Scrip_Update_Srv
{

	/*
	 * -------------- AUTOWIRED MEMEBERS ----------------------
	 */

	@Autowired
	private MessagesFormatter	msgFormatter;

	@Autowired
	private IWBRawDataSrv		wbRawDataSrv;

	@Autowired
	private SCWBMetadataSrv		wbMdtSrv;

	@Autowired
	private ISCExistsDB_Srv		scExistsSrv;

	@Autowired
	private ISCCode_Getter_XLS	scCodeGetSrv;

	@Autowired
	private FrameworkManager		frameworkManager;

	@Autowired
	private IHeadersDeltaGetSrv	headerDeltaSrv;

	/*
	 * -------------- PRIVATE MEMBERS ----------------------
	 */

	private TY_WBRawData		wbRawData;

	private EN_SC_General		scRoot;

	/*
	 * Interface Implementation
	 */

	@Override
	public boolean Update_Scrip_WbContext(XSSFWorkbook wbCtxt) throws EX_General
	{
		boolean updated = false;

		if (wbCtxt != null)
		{

			try
			{

				// 1. GEt Scrip Root
				getScRoot(wbCtxt);
				if (this.scRoot != null)
				{
					// 2. Get the RawData
					getWbRawData(wbCtxt);

					// 3. Update Root Entity
					updateRootEntity();

					// 4. Update Related Entities
					updateRelatedEntities(wbCtxt);

					// n. Finally SAve the Entity
					updated = scRoot.Save();
				}

			}

			catch (Exception e)
			{
				if (scRoot == null)

				{
					EX_General egen = new EX_General("ERRCRSCROOT", new Object[]
					{ null, e.getMessage()
					});
					msgFormatter.generate_message_snippet(egen);
					throw egen;
				}
				else
				{
					EX_General egen = new EX_General("ERRCRSCROOT", new Object[]
					{ " ", e.getMessage()
					});
					msgFormatter.generate_message_snippet(egen);
					throw egen;

				}
			}
		}
		return updated;
	}

	/*
	 * ------------------------ PRIVATE METHODS -----------------------------
	 */

	private void getScRoot(XSSFWorkbook wbCtxt) throws EX_General
	{
		if (scExistsSrv != null && scCodeGetSrv != null)
		{
			String scCode = scCodeGetSrv.getSCCode(wbCtxt);
			if (scCode != null)
			{
				this.scRoot = scExistsSrv.Get_ScripExisting_DB(scCode);
			}

		}
	}

	private void getWbRawData(XSSFWorkbook wbCtxt) throws EX_General
	{
		if (wbRawDataSrv != null)
		{

			this.wbRawData = wbRawDataSrv.getSheetFldVals(wbCtxt);

		}
	}

	@SuppressWarnings("static-access")
	private void updateRootEntity() throws EX_General, ReflectiveOperationException, IllegalArgumentException, InvocationTargetException
	{
		if (scRoot != null)
		{

			// GEt Shet Metadata
			SCSheetMetadata shMdt = wbMdtSrv.getMetadataforBaseSheet();
			if (shMdt != null)
			{

				// Basics
				scRoot.lock();
				scRoot.switchtoChangeMode();

				// GEt Raw Data from WB
				TY_SingleCard_SheetRawData baseSheetRawData = this.wbRawData.getNonCollSheetsRawData().stream()
				          .filter(x -> x.getSheetName().equals(shMdt.getSheetName())).findFirst().get();

				if (baseSheetRawData != null)
				{

					// Get Object Info for Root
					Object_Info obj_Info = frameworkManager.getObjectsInfoFactory().Get_ObjectInfo_byName(shMdt.getBobjName());
					if (obj_Info != null)
					{
						for ( SheetFieldsMetadata fldMdt : shMdt.getFldsMdt() )
						{
							// For Non Unmodifiable - i.e. Modifiable Fields
							if (!fldMdt.isUnModifiable())
							{
								// Get the value from Raw Data
								Object val = baseSheetRawData.getSheetRawData().stream()
								          .filter(y -> y.getAttrName().equals(fldMdt.getObjField())).findFirst().get().getValue();

								if (val != null)
								{
									// Get the Setter by field Name from POJO
									Method setM = obj_Info.Get_Setter_for_FieldName(fldMdt.getObjField());
									if (setM != null)
									{
										// Invoke the POJO Setter with Raw Sheet Data Object Value
										setM.invoke(scRoot, val);
									}
								}
							}
						}
					}
				}

			}

		}

	}

	private void updateRelatedEntities(XSSFWorkbook wbCtxt) throws EX_General, EX_InvalidRelationException, IllegalAccessException,
	          IllegalArgumentException, InvocationTargetException, InstantiationException
	{
		ArrayList<DependantObject> entList = null;
		ArrayList<SCSheetMetadata> sheetsMdt = wbMdtSrv.getWbMetadata().getSheetMetadata();
		if (sheetsMdt != null)
		{
			for ( SCSheetMetadata shMdt : sheetsMdt )
			{
				if (!shMdt.isBaseSheet())
				{
					String relName = wbMdtSrv.getRelationNameforSheetName(shMdt.getSheetName());
					if (relName != null)
					{
						entList = scRoot.getRelatedEntities(relName);
						if (entList != null)
						{
							if (entList.size() > 0)
							{
								if (!shMdt.isUpdHeaderDeltaMode())
								{
									// Update Everything
									updateWODeltaHdrs(shMdt, entList);
								}

								else
								{
									// Update using DElta Headers
									// DElta Headers only need to be created - No Updation needed
									XSSFSheet sheetRef = wbCtxt.getSheet(shMdt.getSheetName());
									if (sheetRef != null)
									{
										updateWithDeltaHdrs(sheetRef, entList, shMdt);
									}

								}
							}
						}
					}
				}
			}
		}

	}

	@SuppressWarnings("static-access")
	private void updateWODeltaHdrs(SCSheetMetadata shMdt, ArrayList<DependantObject> entList)
	          throws EX_General, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		TY_SingleCard_SheetRawData singleCardRawData = null;
		SheetFldValsHeadersList collRawData = null;
		int cnt = 0;

		// Get Object Info for Root
		Object_Info obj_Info = frameworkManager.getObjectsInfoFactory().Get_ObjectInfo_byName(shMdt.getBobjName());

		if (obj_Info != null)
		{

			if (!shMdt.isCollection()) // Single Cardinality Relations
			{
				// Get Sheet Raw Data
				singleCardRawData = wbRawData.getNonCollSheetsRawData().stream().filter(x -> x.getSheetName().equals(shMdt.getSheetName()))
				          .findFirst().get();
				if (singleCardRawData != null)
				{
					DependantObject depObj = entList.get(0);
					if (depObj != null)
					{
						depObj.switchtoChangeMode(); // Root is already locked in previous method Call for
						                             // Updating Root
						for ( SheetFieldsMetadata fldMdt : shMdt.getFldsMdt() )
						{
							// For Non Unmodifiable - i.e. Modifiable Fields
							if (!fldMdt.isUnModifiable())
							{
								// Get the value from Raw Data
								Object val = singleCardRawData.getSheetRawData().stream()
								          .filter(y -> y.getAttrName().equals(fldMdt.getObjField())).findFirst().get().getValue();

								if (val != null)
								{
									// Get the Setter by field Name from POJO
									Method setM = obj_Info.Get_Setter_for_FieldName(fldMdt.getObjField());
									if (setM != null)
									{
										// Invoke the POJO Setter with Raw Sheet Data Object Value
										setM.invoke(depObj, val);
									}
								}
							}
						}
					}
				}
			}
			else // 1..n Cardinality Relations
			{
				cnt = 0;
				// Get Sheet Raw Data
				collRawData = wbRawData.getCollSheetsRawData().stream().filter(x -> x.getSheetName().equals(shMdt.getSheetName())).findFirst()
				          .get();
				if (collRawData != null)
				{
					// For Each Entity in Related Entities
					for ( DependantObject depObjBean : entList )
					{
						depObjBean.switchtoChangeMode();
						for ( SheetFieldsMetadata fldMdt : shMdt.getFldsMdt() )
						{
							// For Non Unmodifiable - i.e. Modifiable Fields
							if (!fldMdt.isUnModifiable())
							{
								// Get the value from Raw Data
								Object val = collRawData.getFldValList().stream().filter(y -> y.getFieldName().equals(fldMdt.getSheetField()))
								          .findFirst().get().getFieldVals().get(cnt);

								if (val != null)
								{
									// Get the Setter by field Name from POJO
									Method setM = obj_Info.Get_Setter_for_FieldName(fldMdt.getObjField());
									if (setM != null)
									{
										// Invoke the POJO Setter with Raw Sheet Data Object Value
										setM.invoke(depObjBean, val);
									}
								}
							}
						}
						cnt++; // Move to Next Entity Field Values
					}

				}

			}
		}
	}

	private void updateWithDeltaHdrs(XSSFSheet sheetRef, ArrayList<DependantObject> entList, SCSheetMetadata shMdt) throws EX_General,
	          IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, EX_InvalidRelationException
	{
		SheetFldValsHeadersList collRawData = null;

		// Get Object Information from Object Factory
		Object_Info objInfo = FrameworkManager.getObjectsInfoFactory().Get_ObjectInfo_byName(shMdt.getBobjName());

		// Get FieldsMetadata
		ArrayList<SheetFieldsMetadata> fldsMdt = shMdt.getFldsMdt();
		Object depPOJO = null;
		Object headerVal = null;
		int cnt = 0;

		String headerObjFldName = shMdt.getHeadScanConfig().getObjField();

		if (headerDeltaSrv != null)
		{

			for ( Object deltaHdr : headerDeltaSrv.getHeadersDelta(sheetRef, entList, shMdt) )
			{
				// Need to get raw Data for this header

				collRawData = wbRawData.getCollSheetsRawData().stream().filter(x -> x.getSheetName().equals(shMdt.getSheetName())).findFirst()
				          .get();
				if (collRawData != null)
				{
					cnt = 0;
					for ( Object header : collRawData.getHeaders() )
					{
						if (header.equals(deltaHdr))
						{
							headerVal = deltaHdr;
							break; // get out of loop
						}
						cnt++;
					}

				}

				String relName = wbMdtSrv.getRelationNameforSheetName(shMdt.getSheetName());
				if (relName != null)
				{
					// One POJO for Each Header
					depPOJO = objInfo.getCurr_Obj_Class().newInstance();
					if (depPOJO != null)
					{
						// 1. Set Header Value in POJO

						// 1.a. Get Setter by Header Field NAme

						Method setterH = objInfo.Get_Setter_for_FieldName(headerObjFldName);
						if (setterH != null)
						{
							// 1.b. Invoke the Setter to Set the Header in POJO
							setterH.invoke(depPOJO, headerVal);
						}

						// Set Each Field Value in POJO
						for ( SheetFieldsMetadata fldMdt : fldsMdt )
						{
							// Get Field Values for the Field in question - Currently
							FldVals fldVals = collRawData.getFldValList().stream().filter(z -> z.getFieldName().equals(fldMdt.getSheetField()))
							          .findFirst().get();
							if (fldVals != null)
							{
								Method setterF = objInfo.Get_Setter_for_FieldName(fldMdt.getObjField());
								if (setterF != null)
								{
									// Exception in case Field is mandatory and is not specified
									if (fldMdt.isMandatory() && fldVals.getFieldVals().get(cnt) == null)
									{
										EX_General egen = new EX_General("ERRNOMANDTVALUE", new Object[]
										{ fldVals.getFieldName(), shMdt.getSheetName()
										});
										msgFormatter.generate_message_snippet(egen);
										throw egen;
									}
									else
									{
										// Non Mandatory and Still Null
										if (fldVals.getFieldVals().get(cnt) == null)
										{
											Object val = null;
											switch (fldMdt.getDataType())
											{
												case Numerical:
													val = 0;
													break;

												case Decimal:
													val = 0;
													break;

												case Date:
													val = " ";
													break;

												case String:
													val = " ";
													break;
											}

											setterF.invoke(depPOJO, val);
										}
										else
										{
											// Non Mandatory - No Null - Go Ahead and SET
											// 1.b. Invoke the Setter to Set the Header in POJO
											setterF.invoke(depPOJO, fldVals.getFieldVals().get(cnt));
										}
									}

								}
							}
						}

					}

					// Once POJO Completely Set - Create DEpendent Object Bean
					// Create Dependant Entity Object
					DependantObject depObjBean = this.scRoot.Create_RelatedEntity(relName);
					if (depObjBean != null)
					{
						PropertiesMapper.setPropertiesforDependantProxyBeanUpdateMode(depObjBean, (DependantObject) depPOJO);
						depPOJO = null;
					}
				}

			}

		}

	}

}
