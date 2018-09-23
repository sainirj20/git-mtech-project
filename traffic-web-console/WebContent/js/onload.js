function onLoad(){
	showDateTime();
}

function showDateTime() {
	var dt = new Date();
	var dateString = dt.toString().replace(' GMT+0530 (India Standard Time)',
			'');
	var lastUpdates = dateString.slice(dateString.length - 8,
			dateString.length - 4)
			+ '0';
	document.getElementById("currentDate").innerHTML = dateString;
	document.getElementById("lastUpdated").innerHTML = 'Map Updated: '
			+ lastUpdates;
	setTimeout(showDateTime, 500);
}