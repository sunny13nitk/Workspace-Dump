package root.scripsEngine.uploadEngine.scripSheetServices.implementations;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.scripsEngine.uploadEngine.entities.EN_SC_GeneralQ;
import root.scripsEngine.uploadEngine.exceptions.EX_General;
import root.scripsEngine.uploadEngine.scripSheetServices.interfaces.ISCExistsDB_Srv;

@Service(
    "SCExistsDB_Srv"
)

public class SCExistsDB_Srv implements ISCExistsDB_Srv
{
	
	// need to inject the session factory
	@Autowired
	private SessionFactory sFac;
	
	@Override
	@Transactional
	public boolean Is_ScripExisting_DB(
	        String scCode
	) throws EX_General
	{
		boolean scExists = false;
		
		if (scCode != null && sFac != null)
		{
			Session sess = sFac.getCurrentSession();
			if (sess != null)
			{
				Query<EN_SC_GeneralQ> qscRoot = sess.createQuery("from EN_SC_GeneralQ where SCCode =: lv_scCode",
				        EN_SC_GeneralQ.class);
				if (qscRoot != null)
				{
					qscRoot.setParameter("lv_scCode", scCode);
					
					List<EN_SC_GeneralQ> scrips = qscRoot.getResultList();
					if (scrips != null)
					{
						if (scrips.size() > 0)
						{
							if (scrips.get(0) != null)
							{
								scExists = true;
							}
						}
					}
				}
			}
			
		}
		
		return scExists;
	}
	
	@Override
	@Transactional
	public EN_SC_GeneralQ Get_ScripExisting_DB(
	        String scCode
	) throws EX_General
	{
		EN_SC_GeneralQ scRoot = null;
		
		if (scCode != null && sFac != null)
		{
			Session sess = sFac.getCurrentSession();
			if (sess != null)
			{
				Query<EN_SC_GeneralQ> qscRoot = sess.createQuery("from EN_SC_GeneralQ where SCCode =: lv_scCode",
				        EN_SC_GeneralQ.class);
				if (qscRoot != null)
				{
					qscRoot.setParameter("lv_scCode", scCode);
					
					List<EN_SC_GeneralQ> scrips = qscRoot.getResultList();
					if (scrips != null)
					{
						if (scrips.size() > 0)
						{
							if (scrips.get(0) != null)
							{
								scRoot = scrips.get(0);
							}
						}
					}
				}
			}
		}
		
		return scRoot;
	}
	
	@Override
	@Transactional
	public boolean Is_ScripExisting_DB_DescSW(
	        String scDesc
	) throws EX_General
	{
		boolean scExists = false;
		
		if (scDesc != null && sFac != null)
		{
			Session sess = sFac.getCurrentSession();
			if (sess != null)
			{
				
				String scName = '%' + scDesc;
				
				Query<EN_SC_GeneralQ> qscRoot = sess.createQuery("from EN_SC_GeneralQ where SCName LIKE : scName",
				        EN_SC_GeneralQ.class);
				if (qscRoot != null)
				{
					qscRoot.setParameter("scName", scName);
					
					List<EN_SC_GeneralQ> scrips = qscRoot.getResultList();
					if (scrips != null)
					{
						if (scrips.size() > 0)
						{
							if (scrips.get(0) != null)
							{
								scExists = true;
							}
						}
					}
				}
			}
			
		}
		
		return scExists;
	}
	
	@Override
	@Transactional
	public EN_SC_GeneralQ Get_ScripExisting_DB_DescSW(
	        String scDesc
	) throws EX_General
	{
		EN_SC_GeneralQ scRoot = null;
		if (scDesc != null && sFac != null)
		{
			Session sess = sFac.getCurrentSession();
			if (sess != null)
			{
				
				String scName = '%' + scDesc;
				
				Query<EN_SC_GeneralQ> qscRoot = sess.createQuery("from EN_SC_GeneralQ where SCName LIKE : scName",
				        EN_SC_GeneralQ.class);
				if (qscRoot != null)
				{
					qscRoot.setParameter("scName", scName);
					
					List<EN_SC_GeneralQ> scrips = qscRoot.getResultList();
					if (scrips != null)
					{
						if (scrips.size() > 0)
						{
							if (scrips.get(0) != null)
							{
								scRoot = scrips.get(0);
							}
						}
					}
				}
			}
			
		}
		
		return scRoot;
	}
	
}
