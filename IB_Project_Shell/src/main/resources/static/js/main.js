$(document).ready(function(){
	var emailInput = $('#email');
	var passwordInput = $('#password');
	
	$('body').on('click', '#submit',function(event){
	
		var email = emailInput.val();
		var pass = passwordInput.val();
		var json = {
				'username': email,
				'password': pass
		};
			
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url :"http://localhost:8443/auth/login",
			data :  JSON.stringify(json),
			dataType : 'json',
			success : function(data) {
				console.log(data);
				var token = data.access_token;
				$.ajax({
					url: "http://localhost:8443/api/user/whoami",
					type: 'GET',
					headers: { "Authorization": "Bearer " + token},
					contentType : "application/json",
					success : function(data) {
						alert("Uspeh");
						console.log(data);
						if(data.active == true){
							localStorage.setItem("token", token);
							window.location.replace('home.html');
						}else{
							alert("You aren't activated!");
						}
					},
					error : function(e) {
						localStorage.setItem("token", token);
						window.location.replace('home.html');
					}
				});
				
			},
			error : function(e) {
				console.log("ERROR: ", e);
				alert("You aren't activated!");
			}
		});
		
		event.preventDefault();
		return false;
	
	});
});

function download() {

	var xhr = new XMLHttpRequest();
	xhr.open('GET', "/api/demo/download", true);
	xhr.responseType = 'blob';

	xhr.onload = function(e) {
		if (this.status == 200) {
			var blob = this.response;
			console.log(blob);
			var a = document.createElement('a');
			var url = window.URL.createObjectURL(blob);
			a.href = url;
			a.download = xhr.getResponseHeader('filename');
			a.click();
			window.URL.revokeObjectURL(url);
		}
	};

	xhr.send();
};