'use-strict';



document.addEventListener('DOMContentLoaded' , () => {
	var eAmount = document.getElementById('amount-table');
	var eManager = document.getElementById('manager-table');
	var eReason = document.getElementById('reason-table');
	
	fetch('http://localhost:8080/ERS2/submit-request')
		//response.json() returns a promise
		.then(respsonse => response.json())
		//then we retrieve value to process onto the page
		.then(data => {
			var dataParse = json.parse(data);
			eAmount.textContent = dataParse.amount;
			eManager.textContent = dataParse.manager;
			eReason.textContent = dataParse.reason;
		})
		.catch(err =>{
			console.log(err);
		})
		
});