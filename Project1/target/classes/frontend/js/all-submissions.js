/**
 * Load all the user's submissions
 */

window.onload = function() {
	console.log("js linked");
	getUserSubmissions();
}

function getUserSubmissions(){
	let xhttp = new XMLHttpRequest();
	
	xhttp.onreadystatechange = function() {
		if(xhttp.readyState == 4 && xhttp.status == 200) {
			let reimbursements = JSON.parse(xhttp.responseText);
			console.log(reimbursements);
		}
	}
	
	xhttp.open("GET", "http://localhost:9001/employee/users-submissions");
	xhttp.send();
}