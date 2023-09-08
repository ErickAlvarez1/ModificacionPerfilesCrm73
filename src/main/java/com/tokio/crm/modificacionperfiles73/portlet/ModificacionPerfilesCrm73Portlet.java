package com.tokio.crm.modificacionperfiles73.portlet;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.WebKeys;
import com.tokio.crm.modificacionperfiles73.constants.ModificacionPerfilesCrm73PortletKeys;
import com.tokio.crm.servicebuilder73.model.Catalogo_Detalle;
import com.tokio.crm.servicebuilder73.model.Perfil_Crm;
import com.tokio.crm.servicebuilder73.model.Permisos_Perfiles;
import com.tokio.crm.servicebuilder73.model.User_Crm;
import com.tokio.crm.servicebuilder73.service.Catalogo_DetalleLocalService;
import com.tokio.crm.servicebuilder73.service.Perfil_CrmLocalService;
import com.tokio.crm.servicebuilder73.service.Permisos_PerfilesLocalService;
import com.tokio.crm.servicebuilder73.service.User_CrmLocalService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author urielfloresvaldovinos
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=ModificacionPerfilesCrm73",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ModificacionPerfilesCrm73PortletKeys.MODIFICACIONPERFILESCRM73,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.requires-namespaced-parameters=false",
		"com.liferay.portlet.private-request-attributes=false"
	},
	service = Portlet.class
)
public class ModificacionPerfilesCrm73Portlet extends MVCPortlet {
	
	private static final Log _log = LogFactoryUtil.getLog(ModificacionPerfilesCrm73Portlet.class);
	
	@Reference
	Catalogo_DetalleLocalService _Catalogo_DetalleLocalService;
	
	@Reference
	Permisos_PerfilesLocalService _Permisos_PerfilesLocalService;
	
	@Reference
	User_CrmLocalService _User_CrmLocalService;
	
	@Reference
	Perfil_CrmLocalService _Perfil_CrmLocalService;
	
	@Override
	public void doView( RenderRequest renderRequest, RenderResponse renderResponse) 
			throws IOException, PortletException {
		
		List<Perfil_Crm> listaPerfiles = new ArrayList<Perfil_Crm>();
		listaPerfiles = _Perfil_CrmLocalService.getPerfil_Crms(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		
		List<Catalogo_Detalle> listaModulos = _Catalogo_DetalleLocalService.findByCodigo("CATMODULOSPERFILES");
		
		renderRequest.setAttribute("listaModulos", listaModulos);
		renderRequest.setAttribute("listaPerfiles", listaPerfiles);
		
		renderRequest.setAttribute("permisoEscritura", validaPermisos(renderRequest));
		
		
		super.doView(renderRequest, renderResponse);
	}
	
	public boolean validaPermisos(RenderRequest renderRequest) {
		
		User user = (User) renderRequest.getAttribute(WebKeys.USER);
		
		try {
			User_Crm usuario = _User_CrmLocalService.getUser_Crm((int)user.getUserId());
			
			List<Permisos_Perfiles> permisos = _Permisos_PerfilesLocalService.getPermisosByPerfilId(usuario.getPerfilId());
			
			for(Permisos_Perfiles p : permisos) {
				if(p.getModuloId() == 57) {
					return p.getEscritura();
				}
			}
			
			return false;
		}
		catch(Exception e) {
			//e.printStackTrace();
			
			_log.error(e.getMessage(), e);
			
			return false;
		}
	}
}