$(document).ready(function(){
	var emailInput = $('#email');
	var passwordInput = $('#password');
	
	$('#submit').on('click',function(event){
		
		var email = emailInput.val();
		var pass = passwordInput.val();
		var param = {
				'email': email,
				'password': pass
		};
		
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url :"http://localhost:8443/api/user/create",
			data :  JSON.stringify(param),
			dataType : 'json',
			success : function(result) {
				window.location.replace('index.html');
			},
			error : function(e) {
				alert("Greska!");
			}
		});
		event.preventDefault();
		return false;
	});
});