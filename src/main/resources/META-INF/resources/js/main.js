$(document).ready(function() {
	
	if(!permisoEscritura) {
		$("#tablaPerfiles").find("input,button,textarea,select").attr("disabled", true);
	}
});

$("#perfilTMX").change(function() {
	
	$.post(getPermisosPerfilURL,{
		idPerfil: $(this).val()
	}).done(function(data) {
		console.log(data);
		
		var jsonData = JSON.parse(data);
		
		$.each(jsonData, function(key, value) {
			
			$("#" + value._moduloId).find('.restringido').prop('checked', value._restringido);
			$("#" + value._moduloId).find('.lectura').prop('checked', value._lectura);
			$("#" + value._moduloId).find('.escritura').prop('checked', value._escritura);
		})
	});
});

function generaInfoPermisos() {
	
	var auxArrayInfoPermisos = [];
	
	$.each($(".rowModulo"), function(key, value){
		
		var auxObj = new Object();
		
		auxObj.idPerfil = $("#perfilTMX").val();
		auxObj.idModulo = $(this).attr('id');
		auxObj.restringido = $(this).find('.restringido').is(':checked');
		auxObj.lectura = $(this).find('.lectura').is(':checked');
		auxObj.escritura = $(this).find('.escritura').is(':checked');
		
		auxArrayInfoPermisos.push(auxObj);
	});
	
	return auxArrayInfoPermisos;
} 

$("#guardarPermisos").click(function() {
	
	$("#infoPermisos").val(JSON.stringify(generaInfoPermisos()));
	
	$("#formGuardaPermisos").submit();
});


$("#seleccionarRestringido").click(function() {
	$(".restringido").prop('checked', true);
});

$("#seleccionarLectura").click(function() {
	$(".lectura").prop('checked', true);
});

$("#seleccionarEscritura").click(function() {
	$(".escritura").prop('checked', true);
});