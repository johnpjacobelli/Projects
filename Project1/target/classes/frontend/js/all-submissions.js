/**
 * Load all the user's submissions
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
			getReimbursementInfo(user.userID);		
		}
	}
	
	// figure out how to validate if someone is logged in
	// then, invalidate that when logged out
	
	xhttp.open("GET", "http://localhost:9001/employee/session");
	
	xhttp.send();

}

function getReimbursementInfo(userID){
	let xhttp = new XMLHttpRequest();
	
	xhttp.onreadystatechange = function() {
		if(xhttp.readyState == 4 && xhttp.status == 200) {
			reimbursements = JSON.parse(xhttp.responseText);
			document.getElementById("reims").innerHTML = "";
			for(temp of reimbursements){
				let splitTemp = temp.split(",");
				document.getElementById("reims").innerHTML += 
					`<div class="card" style="margin: 5px;"><div class="card-body"><p class="card-subtitle">` + 
					`Reimbursement Ticket ID: ${splitTemp[0].split("=")[1]}<br>` + 
					`Reimbursement Amount: ${splitTemp[1].split("=")[1]}<br>` + 
					`Reimbursement Type: ${splitTemp[8].split("=")[1]}<br>` + 
					`Reimbursement Description: ${splitTemp[4].split("=")[1]}<br>` + 
					`Ticket Submitter: ${splitTemp[5].split("=")[1]}<br>` + 
					`Submission Time: ${splitTemp[2].split("=")[1]}<br>` + 
					`Reviewed By: ${splitTemp[6].split("=")[1]}<br>` + 
					`Time Resolved: ${splitTemp[3].split("=")[1]}<br>` + 
					`Ticket Status: ${splitTemp[7].split("=")[1]}<br></p></div></div><br><br>`;
			}
			
			console.log(document.getElementById("div1").style.height = $('#div2').height() + 300 + "px");
		}
	}
	
	xhttp.open("GET", "http://localhost:9001/employee/users-submissions/reimb_author/" + userID);
	xhttp.send();
}