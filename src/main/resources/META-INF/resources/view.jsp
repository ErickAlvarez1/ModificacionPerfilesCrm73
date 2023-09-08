<%@ include file="/init.jsp" %>

<portlet:actionURL var="guardaPermisosPerfiles" name="/crm/perfiles/guardaPermisosPerfiles" />

<portlet:resourceURL id="/crm/perfiles/getPermisosPerfil" var="getPermisosPerfilURL" cacheability="FULL" />

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css?v=${version}&browserId=other" />

<section class="ModificacionPerfilesCRMPortlet">
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<p
					class="font-weight-bold h1-responsive center-block mt-4 mb-4 animated zoomInDown animation-delay-5">
					<liferay-ui:message key="MODIFICACIONPERFILESCRM73.modificacion" />
				</p>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<div class="md-form form-group">
					<select name="perfilTMX" class="mdb-select" id="perfilTMX">
						<option value="-1">Seleccionar Perfil</option>
						<c:forEach items="${listaPerfiles}" var="option">
							<option value="${option.perfilId}">${option.descripcion}</option>
						</c:forEach>
					</select>
					<label for="perfilTMX">
						<liferay-ui:message key="MODIFICACIONPERFILESCRM73.perfil" />
					</label>
				</div>
			</div>
			<div class="col-md-4"></div>
		</div>
		<div class="table-wrapper">
			<table class="table data-table table-bordered" style="width: 100%;" id="tablaPerfiles">
				<thead>
					<tr>
						<th><liferay-ui:message key="MODIFICACIONPERFILESCRM73.modulos" /></th>
						<th><liferay-ui:message key="MODIFICACIONPERFILESCRM73.restringido" /></th>
						<th><liferay-ui:message key="MODIFICACIONPERFILESCRM73.lectura" /></th>
						<th><liferay-ui:message key="MODIFICACIONPERFILESCRM73.escritura" /></th>
					</tr>
					
				</thead>
				<tbody>
					<tr class="rowTodos">
						<td>
							Seleccionar Todos
						</td>
						<td class="text-center">
							<button id="seleccionarRestringido" class="btn btn-primary btn-sm">Seleccionar Todos</button>
						</td>
						<td class="text-center">
							<button id="seleccionarLectura" class="btn btn-primary btn-sm">Seleccionar Todos</button>
						</td>
						<td class="text-center">
							<button id="seleccionarEscritura" class="btn btn-primary btn-sm">Seleccionar Todos</button>
						</td>
					</tr>
					<c:forEach items="${listaModulos}" var="option">
						<tr class="rowModulo" id="${option.catalogoDetalleId}">
							<td>
								${option.valorS}
							</td>
							<td class="text-center">
								<input type="radio" name="group-${option.catalogoDetalleId}" class="restringido" id="restringido-${option.catalogoDetalleId}" />
								<label for="restringido-${option.catalogoDetalleId}"></label>
							</td>
							<td class="text-center">
								<input type="radio" name="group-${option.catalogoDetalleId}" class="lectura" id="lectura-${option.catalogoDetalleId}"/>
								<label for="lectura-${option.catalogoDetalleId}"></label>
							</td>
							<td class="text-center">
								<input type="radio" name="group-${option.catalogoDetalleId}" class="escritura" id="escritura-${option.catalogoDetalleId}"/>
								<label for="escritura-${option.catalogoDetalleId}"></label>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="row ${ permisoEscritura ? '' : 'd-none' }">
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<div class="btn btn-primary" id="guardarPermisos">
					<liferay-ui:message key="MODIFICACIONPERFILESCRM73.guardar" />
				</div>
			</div>
			<div class="col-md-4"></div>
		</div>
	</div>
	
	<form action="${guardaPermisosPerfiles}" method="post" id="formGuardaPermisos">
		<input type="hidden" id="infoPermisos" name="infoPermisos" value=""/>
	</form>
</section>

<script src="<%=request.getContextPath()%>/js/main.js?v=${version}"></script>

<script>

	var getPermisosPerfilURL = '${getPermisosPerfilURL}';
	
	var permisoEscritura = ${permisoEscritura};
</script>