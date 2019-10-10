var keycloak = new Keycloak();
var livrariaURL = 'http://localhost:8580/api/livraria';
var selecionado;
var dados = [];

carregar();

function notAuthenticated() {
	$('#_login').show();
	$('#_logout').hide();
	$('#_account').hide();
	$('td:nth-child(5),th:nth-child(5)').hide();
	$('#_campos').hide();
	$('#_vendas').hide();
}

function authenticated() {
	$('#_login').hide();
	$('#_logout').show();
	$('#_account').show();
	$('td:nth-child(5),th:nth-child(5)').show();
	$('#_campos').show();
	$('#message').html("<h4>Olá " + keycloak.tokenParsed['preferred_username'] + "</h4>");
	
	if(keycloak.hasRealmRole('gerente')) {
		carregaVendas();
		$('#_vendas').show();
	} else {
		$('#_vendas').hide();
	}
}

function carregaVendas() {
	
	$.ajax({ 
		method : "GET", 
		contentType : "application/json", 
		url : livrariaURL + "/vendas",
		success: function(vendas) { 
			
			var vendaHtml = [];
			
			$.each(vendas, function(i, venda) {
				
				vendaHtml.push("<tr>");
				
				vendaHtml.push("<td>" + venda.isbn + "</td>" +
							       "<td>" + venda.quantidade + "</td>" +
							       "<td>" + venda.dataVenda + "</td>");
				vendaHtml.push("</tr>");
			});
		
			$("#tblVendas").html(vendaHtml.join());
			limpar();
		},
		headers: {
			"Authorization": "Bearer " + keycloak.token
		}
	});
	
}

function carregar() {
	var livroHtml = [];
	
	$.getJSON(livrariaURL + "/livros", function(livros) {
		dados = livros;
	
		$.each(livros, function(i, livro) {
			
			livroHtml.push("<tr>");
			
				livroHtml.push("<td>" + livro.isbn + "</td>" +
						       "<td>" + livro.titulo + "</td>" +
						       "<td>" + livro.autor + "</td>" +
							   "<td>" + livro.quantidade + "</td>" +
							   "<td> R$ " + livro.valor + "</td>");
		
				livroHtml.push("<td>" +
									"<button class=\"btn btn-link\" onclick='selecionar(" + i + ")'>VENDER</button>" +
							  "</td>");
			
			livroHtml.push("</tr>");
		});
		
		$("#tblLivros").html(livroHtml.join());
		limpar();
	});
}


function vender() {
	
	var metodo = "PUT";
	var url = livrariaURL + "/livro/" +  $("#_isbn").val() + "/vender/" + $("#_quantidade").val()
			
	$.ajax({ 
		method : metodo, 
		contentType : "application/json", 
		url : url,
		headers: {
			"Authorization": "Bearer " + keycloak.token
		}
	}).done(carregar);
	
}

function limpar() {
	selecionado = null;
	$("#_campos input[type=text]").val("");
}

function selecionar(index) {
	selecionado = dados[index];
	
	$("#_titulo").val(selecionado.titulo);
	$("#_autor").val(selecionado.autor);
	$("#_isbn").val(selecionado.isbn);
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