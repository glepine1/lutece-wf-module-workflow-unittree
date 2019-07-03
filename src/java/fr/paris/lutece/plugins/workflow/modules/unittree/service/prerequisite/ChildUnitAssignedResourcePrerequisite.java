package fr.paris.lutece.plugins.workflow.modules.unittree.service.prerequisite;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.unittree.business.assignment.UnitAssignment;
import fr.paris.lutece.plugins.unittree.business.assignment.UnitAssignmentHome;
import fr.paris.lutece.plugins.unittree.business.unit.Unit;
import fr.paris.lutece.plugins.unittree.business.unit.UnitHome;
import fr.paris.lutece.plugins.workflow.service.prerequisite.IManualActionPrerequisiteService;
import fr.paris.lutece.plugins.workflowcore.business.prerequisite.DefaultPrerequisiteConfig;
import fr.paris.lutece.plugins.workflowcore.business.prerequisite.IPrerequisiteConfig;
import fr.paris.lutece.portal.business.user.AdminUser;

public class ChildUnitAssignedResourcePrerequisite implements IManualActionPrerequisiteService {

	private static final String PREREQUISITE_TITLE_I18N = "module.workflow.unittree.prerequisite.unit.child.resource.title";
	private static final String PREREQUISITE_TYPE = "module.workflow.unittree.prerequisite.unit.child.resource";

	public String getTitleI18nKey( )
	{
		return PREREQUISITE_TITLE_I18N;
	}

	public String getPrerequisiteType( )
	{
		return PREREQUISITE_TYPE;
	}

	@Override
	public boolean hasConfiguration( )
	{
		return false;
	}

	@Override
	public IPrerequisiteConfig getEmptyConfiguration( )
	{
		 return new DefaultPrerequisiteConfig( );
	}

	@Override
	public String getConfigurationDaoBeanName( )
	{
		return null;
	}

	@Override
	public String getConfigHtml( IPrerequisiteConfig config, HttpServletRequest request, Locale locale ) {
		return null;
	}

	@Override
	public boolean canActionBePerformed( int nIdResource, String strResourceType, IPrerequisiteConfig config,
			int nIdAction )
	{
		return false;
	}
	
	@Override
	public boolean canManualActionBePerformed (AdminUser user, int nIdResource, String strResourceType,
			IPrerequisiteConfig config, int nIdAction )
	{
		
		List<Unit> userUnitList = UnitHome.findByIdUser( user.getUserId( ) );
		Set<Integer> unitIdList = new HashSet<>( );
		for ( Unit userUnit : userUnitList )
		{
			unitIdList.add( userUnit.getIdUnit( ) );
			UnitHome.getAllSubUnitsId( userUnit.getIdUnit( ) );
		}
		
		List<UnitAssignment> resourceUnitList = UnitAssignmentHome.findByResource( nIdResource, strResourceType );
		
		for ( UnitAssignment resourceUnit : resourceUnitList )
		{
			if ( unitIdList.contains( resourceUnit.getIdAssignedUnit( ) ) );
			{
				return true;
			}
		}
		return false;
	}
}
