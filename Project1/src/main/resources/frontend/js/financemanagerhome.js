/**
 * Getting a manager's' session and adjusting the page based on filters
 */

window.onload = function() {
	console.log("js linked");
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
	
	xhttp.open("GET", "http://localhost:9001/employee/session");
	xhttp.send();
}


function viewReimbursement(filter){
	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if(xhttp.readyState == 4 && xhttp.status == 200) {
			let status = JSON.parse(xhttp.responseText);
			getFilteredReimbursements(status);
		}
	}
	
	xhttp.open("GET", "http://localhost:9001/employee/reimbursement-column-id/" + filter);
	xhttp.send();
}

function getFilteredReimbursements(status){
	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if(xhttp.readyState == 4 && xhttp.status == 200) {
			reimbursements = JSON.parse(xhttp.responseText);
			document.getElementById("reims").innerHTML = "";
			for(temp of reimbursements){
				let splitTemp = temp.split(",");
				if(splitTemp[7].split("=")[1] == "Pending"){
					document.getElementById("reims").innerHTML += 
						`<div class="card" style="margin: 5px;"><div class="card-body"><p class="card-subtitle" style="white-space: pre;">` + 
						`<b>Reimbursement Ticket ID:  </b>${splitTemp[0].split("=")[1]}<br>` + 
						`<b>Reimbursement Amount:  </b>${splitTemp[1].split("=")[1]}<br>` + 
						`<b>Reimbursement Type:  </b>${splitTemp[8].split("=")[1]}<br>` + 
						`<b>Reimbursement Description:  </b>${splitTemp[4].split("=")[1]}<br>` + 
						`<b>Ticket Submitter:  </b>${splitTemp[5].split("=")[1]}<br>` + 
						`<b>Submission Time:  </b>${splitTemp[2].split("=")[1]}<br>` + 
						`<b>Reviewed By:  </b>${(splitTemp[6].split("=")[1] == "null") ? "" : splitTemp[6].split("=")[1]}<br>` + 
						`<b>Time Resolved:  </b>${(splitTemp[3].split("=")[1] == "null") ? "" : splitTemp[3].split("=")[1]}<br>` + 
						`<b>Ticket Status:  </b>${splitTemp[7].split("=")[1]}<br></p><br>` + 
						`<div id="buttonDiv${splitTemp[0].split("=")[1]}">` + 
						`<button id="approveBtn${splitTemp[0].split("=")[1]}" class="btn btn-primary" onclick="approve(${splitTemp[0].split("=")[1]})") style="background-color:green">Approve</button>&emsp;` +
						`<button id="declineBtn${splitTemp[0].split("=")[1]}" class="btn btn-primary" onclick="decline(${splitTemp[0].split("=")[1]})") style="background-color:red">Decline</button></div>` +
						`</div></div>`;
				}
				else {
					document.getElementById("reims").innerHTML += 
						`<div class="card" style="margin: 5px;"><div class="card-body"><p class="card-subtitle" style="white-space: pre;">` + 
						`<b>Reimbursement Ticket ID:  </b>${splitTemp[0].split("=")[1]}<br>` + 
						`<b>Reimbursement Amount:  </b>${splitTemp[1].split("=")[1]}<br>` + 
						`<b>Reimbursement Type:  </b>${splitTemp[8].split("=")[1]}<br>` + 
						`<b>Reimbursement Description:  </b>${splitTemp[4].split("=")[1]}<br>` + 
						`<b>Ticket Submitter:  </b>${splitTemp[5].split("=")[1]}<br>` + 
						`<b>Submission Time:  </b>${splitTemp[2].split("=")[1]}<br>` + 
						`<b>Reviewed By:  </b>${splitTemp[6].split("=")[1]}<br>` + 
						`<b>Time Resolved:  </b>${splitTemp[3].split("=")[1]}<br>` + 
						`<b>Ticket Status:  </b>${splitTemp[7].split("=")[1]}<br></p><br></div></div>`;
						
				}
			}
			document.getElementById("div1").style.height = $('#div2').height() + 300 + "px";
		}
	}
	
	xhttp.open("GET", "http://localhost:9001/employee/users-submissions/reimb_status_id/" + status.reimStatusID);
	xhttp.send();
}

function hideUpperText(){
	
	if(document.getElementById("allApproved").getAttribute("aria-expanded") == "true"){
		console.log("hey");
		
	}

    document.getElementById("upperText").style.display = "none";
}

function hideReims() {
	document.getElementById("upperText").style.display = "block";
	document.getElementById("reims").innerHTML = "";
	document.getElementById("div1").style.height = $('#div2').height() + 300 + "px";
}

function approve(id){
	document.getElementById(`approveBtn${id}`).disabled = true;
	document.getElementById(`declineBtn${id}`).disabled = true;
	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if(xhttp.readyState == 4 && xhttp.status == 200) {
			document.getElementById(`buttonDiv${id}`).innerHTML = '<p style="color:green">Approved!</p>';
		}
	}
	
	xhttp.open("PUT", "http://localhost:9001/manager/approve/" + id);
	xhttp.send();
}

function decline(id){
	document.getElementById(`approveBtn${id}`).disabled = true;
	document.getElementById(`declineBtn${id}`).disabled = true;
	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if(xhttp.readyState == 4 && xhttp.status == 200) {
			document.getElementById(`buttonDiv${id}`).innerHTML = '<p style="color:red">Declined!</p>';
		}
	}
	
	xhttp.open("PUT", "http://localhost:9001/manager/decline/" + id);
	xhttp.send();
}