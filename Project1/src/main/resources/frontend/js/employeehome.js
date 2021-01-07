/**
 * Getting a user's session
 */



window.onload = function() {
	// console.log("js linked");
	getSessionUser();
}


function getSessionUser() {
	let xhttp = new XMLHttpRequest();
	
	xhttp.onreadystatechange = function() {
		if(xhttp.readyState == 4 && xhttp.status == 200) {
			let user = JSON.parse(xhttp.responseText);
			document.getElementById("welcome").innerHTML = `Hello, ${user.firstName}.`;		
		}
	}
	
	// figure out how to validate if someone is logged in
	// then, invalidate that when logged out
	
	xhttp.open("GET", "http://localhost:9001/employee/session");
	
	xhttp.send();

}

function hideUpperText(){
	let upperText = document.getElementById("upperText");
	if (upperText.style.display === "none") {
		upperText.style.display = "block";
	} 
	
	else {
	    upperText.style.display = "none";
	}
}

function viewReimbursements(){
	window.location.href = '/html/all-submissions.html';
}