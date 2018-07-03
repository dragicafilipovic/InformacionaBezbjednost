$(document).ready(function(){
	var row = $('.table');
	var logged;
	var admin=false;
	
	var token = localStorage.getItem("token");
	console.log(token);
	
	$.ajax({
		url: "http://localhost:8443/api/user/whoami",
		type: 'GET',
		headers: { "Authorization": "Bearer " + token},
		contentType : "application/json",
		success : function(data) {
			console.log(data);
			logged = data;
			console.log(logged);	
		},
		error : function(e) {
			admin=true;
			console.log("ERROR: ", e);
		}	
	});
	
	$.ajax({
		url: "http://localhost:8443/api/user",
		type: 'GET',
		headers: { "Authorization": "Bearer " + token},
		contentType : "application/json",
		success : function(data) {
			for(var i=0; i<data.length; i++){
				user = data[i];
				row.append("<div class='column'>" + 
	  					"<a id='emailUser' href='#'>"+user.email+"</a><br>" +
	  					"<button id='downloadSertifikat'>Download certificate</button><br>" +
	  					"<p></p>"
	  					);
				if(admin == true && user.active == false){
					row.append("<button id='setActiveUser' name='"+user.email+"'>Set user active</button>" +
	  				"</div>");
				}else{
					row.append(
	  				"</div>");
				}			
			}		
		},
		error : function(e) {
			console.log("ERROR: ", e);
		}
	});
	
	$.ajax({
		url: "http://localhost:8443/api/user/all/active",
		type: 'GET',
		headers: { "Authorization": "Bearer " + token},
		contentType : "application/json",
		success : function(data) {
			for(var i=0; i<data.length; i++){
				user = data[i];
				row.append("<div class='column'>" + 
	  					"<a id='emailUser' href='#'>"+user.email+"</a><br>" +
	  					"<button id='downloadSertifikat'>Download certificate</button><br>" +
	  					"<p></p>"
	  					);
				row.append("</div>");		
			}		
		},
		error : function(e) {
			console.log("ERROR: ", e);
		}
	});
	
	$('body').on('click', '#setActiveUser',function(event){
		var email = $(this).attr('name');
		console.log("Prosledjeni email: " + email);
		$.ajax({
			url: "http://localhost:8443/api/user/edit",
			type: 'PUT',
			data : email,
			headers: { "Authorization": "Bearer " + token},
			contentType : "application/json",
			dataType: 'json',
			success : function(data) {
				$('#setActiveUser').hide();
			},
			error : function(e) {
				console.log("ERROR: ", e);
			}
		});
		
		event.preventDefault();
		return false;
	});

});

function signOut(){
	localStorage.removeItem("token");
	window.location.replace('index.html');
}

function f(){
	var input = $('#search').val().toUpperCase();
	$(".column").each(function(){
		  if($(this).html().toUpperCase().includes(input)){
		    $(this).show();
		  }
		  else{
			$(this).hide();
		  }
	});
}