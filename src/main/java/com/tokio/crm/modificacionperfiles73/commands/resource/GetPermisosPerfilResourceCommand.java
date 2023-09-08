package com.tokio.crm.modificacionperfiles73.commands.resource;

import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.tokio.crm.modificacionperfiles73.constants.ModificacionPerfilesCrm73PortletKeys;
import com.tokio.crm.servicebuilder73.model.Permisos_Perfiles;
import com.tokio.crm.servicebuilder73.service.Permisos_PerfilesLocalService;

import java.io.PrintWriter;
import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + ModificacionPerfilesCrm73PortletKeys.MODIFICACIONPERFILESCRM73,
			"mvc.command.name=/crm/perfiles/getPermisosPerfil"
		},
		service = MVCResourceCommand.class
	)

public class GetPermisosPerfilResourceCommand extends BaseMVCResourceCommand {
	
	@Reference
	Permisos_PerfilesLocalService _Permisos_PerfilesLocalService;

	@Override
	protected void doServeResource(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws Exception {
		
		int idPerfil = ParamUtil.getInteger(resourceRequest, "idPerfil");
		
		Gson gson = new Gson();
		
		List<Permisos_Perfiles> permisos =
				_Permisos_PerfilesLocalService.getPermisosByPerfilId(idPerfil);
		
		String permisosJsonString = gson.toJson(permisos);
		
		PrintWriter writer = resourceResponse.getWriter();
		writer.write(permisosJsonString);
	}

}
