package com.tokio.crm.modificacionperfiles73.commands.action;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.tokio.crm.modificacionperfiles73.constants.ModificacionPerfilesCrm73PortletKeys;
import com.tokio.crm.servicebuilder73.model.Catalogo_Detalle;
import com.tokio.crm.servicebuilder73.model.Permisos_Perfiles;
import com.tokio.crm.servicebuilder73.model.User_Crm;
import com.tokio.crm.servicebuilder73.service.Catalogo_DetalleLocalService;
import com.tokio.crm.servicebuilder73.service.Permisos_PerfilesLocalService;
import com.tokio.crm.servicebuilder73.service.User_CrmLocalService;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		immediate = true,
		property =
			{
				"javax.portlet.init-param.copy-request-parameters=true",
				"javax.portlet.name=" + ModificacionPerfilesCrm73PortletKeys.MODIFICACIONPERFILESCRM73,
				"mvc.command.name=/crm/perfiles/guardaPermisosPerfiles"
			},
		service = MVCActionCommand.class
)

public class GuardaPermisosPerfilActionCommand extends BaseMVCActionCommand {
	
	private static final Log _log = LogFactoryUtil.getLog(GuardaPermisosPerfilActionCommand.class);
	
	@Reference
	Permisos_PerfilesLocalService _Permisos_PerfilesLocalService;
	
	@Reference
	User_CrmLocalService _User_CrmLocalService;
	
	@Reference
	Catalogo_DetalleLocalService _Catalogo_DetalleLocalService;

	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {
		
		Gson gson = new Gson();
		
		String stringInfoPermisos = ParamUtil.getString(actionRequest, "infoPermisos");
		
		JsonArray arrayPermisos = gson.fromJson(stringInfoPermisos, JsonArray.class);
		
		for(int i = 0; i < arrayPermisos.size(); i++) {
			
			JsonObject auxObj = (JsonObject) arrayPermisos.get(i);
			
			int idPerfil = auxObj.get("idPerfil").getAsInt();
			int idModulo = auxObj.get("idModulo").getAsInt();
			boolean restringido = auxObj.get("restringido").getAsBoolean();
			boolean lectura = auxObj.get("lectura").getAsBoolean();
			boolean escritura = auxObj.get("escritura").getAsBoolean();
			
			Permisos_Perfiles aux = 
					_Permisos_PerfilesLocalService.getByPerfilIdModuloId(idPerfil, idModulo);
			
			if(aux == null) {
				_Permisos_PerfilesLocalService.addPermisos_Perfiles(idPerfil, idModulo, restringido, lectura, escritura);
			}
			else {
				_Permisos_PerfilesLocalService.deletePermisos_Perfiles(aux);
				_Permisos_PerfilesLocalService.addPermisos_Perfiles(idPerfil, idModulo, restringido, lectura, escritura);
			}
			
			System.out.println(auxObj.get("idPerfil") + "" + auxObj.get("idModulo"));
			
			
		}
		
		List<User_Crm> usuarios = _User_CrmLocalService.getUser_Crms(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		
		
		
		for(User_Crm u : usuarios) {
			
			_log.info("Permiso: " + u.getAccesoCRM());
			
			if(u.getAccesoCRM()) {
				
				try {
				
					List<Role> rolesAux = RoleLocalServiceUtil.getUserRoles(u.getUserId());
					
					if(validaUserAdmin(u.getUserId())) {
						RoleLocalServiceUtil.deleteUserRoles(u.getUserId(), rolesAux);
					}
				
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				
				List<Permisos_Perfiles> permisos = _Permisos_PerfilesLocalService.getPermisosByPerfilId(u.getPerfilId());
				
				try {
				
				for(Permisos_Perfiles p : permisos) {
					
					Catalogo_Detalle entrada = _Catalogo_DetalleLocalService.getCatalogo_Detalle(p.getModuloId());
					
					if(!p.getRestringido()) {
						
						try {
							
							_log.info("Asignando permiso al usuario con id " + u.getUserId() + " el rol " + entrada.getValorN());
							
							RoleLocalServiceUtil.addUserRole(u.getUserId(), entrada.getValorN());
						} catch(Exception e) {
							e.printStackTrace();
						}
					}
				}
				
				}
				catch(Exception e) {
					_log.error(e.getMessage());
				}
				
				
			}
		
		}
	
	}
	
	public boolean validaUserAdmin(int userId) {
		
		switch(userId) {
			case 1:
				return false;
			case 32868:
				return false;
			case 405176:
				return false;
			case 1940132:
				return false;
				/*
			case 408292:
				return false;
				*/
			default:
				return true;
		}
		
	}

}
