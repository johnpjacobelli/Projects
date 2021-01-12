/**
 * Load all the user's submissions
 */

window.onload = function() {
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
					`<div class="card"id="reimburse${splitTemp[0].split("=")[1]}" style="margin: 5px;"><div class="card-body"><p class="card-subtitle" style="white-space: pre;">` + 
						`<b>Reimbursement Ticket ID:  </b>${splitTemp[0].split("=")[1]}<br>` + 
						`<b>Reimbursement Amount:  </b>${splitTemp[1].split("=")[1]}<br>` + 
						`<b>Reimbursement Type:  </b>${splitTemp[8].split("=")[1]}<br>` + 
						`<b>Reimbursement Description:  </b>${splitTemp[4].split("=")[1]}<br>` + 
						`<b>Ticket Submitter:  </b>${splitTemp[5].split("=")[1]}<br>` + 
						`<b>Submission Time:  </b>${splitTemp[2].split("=")[1]}<br>` + 
						`<b>Reviewed By:  </b>${(splitTemp[6].split("=")[1] == "null") ? "" : splitTemp[6].split("=")[1]}<br>` + 
						`<b>Time Resolved:  </b>${(splitTemp[3].split("=")[1] == "null") ? "" : splitTemp[3].split("=")[1]}<br>` + 
						`<b>Ticket Status:  </b>${splitTemp[7].split("=")[1]}<br></p><br></div></div>`;
			}
			
			document.getElementById("div1").style.height = $('#div2').height() + 300 + "px";
		}
	}
	
	xhttp.open("GET", "http://localhost:9001/employee/users-submissions/reimb_author/" + userID);
	xhttp.send();
}