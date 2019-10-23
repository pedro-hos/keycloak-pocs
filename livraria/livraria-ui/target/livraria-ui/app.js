var keycloak = new Keycloak();
var serviceUrl = 'http://localhost:8280/api/livros';
var selecionado;
var dados = [];

carregar();

function notAuthenticated() {
	$('#not-authenticated').show();
	
	$('#authenticated').hide();
	$('td:nth-child(5),th:nth-child(5)').hide();
	$('#_campos').hide();
}

function authenticated() {
	$('#not-authenticated').hide();
	
	$('#authenticated').show();
	$('td:nth-child(5),th:nth-child(5)').show();
	$('#_campos').show();
	
	$('#message').html("<span>User: " + keycloak.tokenParsed['preferred_username'] + "</span>");
}

function carregar() {
	var livroHtml = [];
	
	$.getJSON(serviceUrl, function(livros) {
		dados = livros;
	
		$.each(livros, function(i, livro) {
			
			livroHtml.push("<tr>");
			
				livroHtml.push("<td>" + livro.id + "</td>" +
						       "<td>" + livro.titulo + "</td>" +
						       "<td>" + livro.descricao + "</td>" +
						       "<td>" + livro.autor + "</td>");
				livroHtml.push("<td>" +
									"<button onclick='apagar(" + livro.id + ")'>apagar</button> | " +
									"<button onclick='editar(" + i + ")'>editar</button>" +
							  "</td>");
			
			livroHtml.push("</tr>");
		});
		
		$("#tblLivros").html(livroHtml.join());
		limpar();
	});
}

function salvar() {
	
	var metodo = "POST";
	var livro = { 
				titulo : $("#_titulo").val(), 
				descricao : $("#_descricao").val(), 
				autor : $("#_autor").val() 
			};
	
	if(selecionado) {
		livro.id = selecionado.id;
		metodo = "PUT";
	}
	
	$.ajax({ 
		method : metodo, 
		contentType : "application/json", 
		url : serviceUrl,
		headers: {
			"Authorization": "Bearer " + keycloak.token
		},
		data : JSON.stringify(livro)}
	).done(carregar);
}

function limpar() {
	selecionado = null;
	$("#_campos input[type=text]").val("");
}

function apagar(id) {
	
	$.ajax({ 
		method : "DELETE", 
		contentType : "application/json", 
		url : serviceUrl + "/" + id,
		headers: {
			"Authorization": "Bearer " + keycloak.token
		}
	}).done(carregar);
}

function editar(index) {
	selecionado = dados[index];
	
	$("#_titulo").val(selecionado.titulo);
	$("#_descricao").val(selecionado.descricao);
	$("#_autor").val(selecionado.autor);
}

window.onload = function () {
	
    keycloak.init({ onLoad: 'check-sso', checkLoginIframeInterval: 1 }).success(function () {
    	
        if (keycloak.authenticated) {
        	authenticated();
        } else {
        	notAuthenticated();
        }

        document.body.style.display = 'block';
    });
}