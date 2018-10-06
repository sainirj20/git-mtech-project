function addCircle(map, congestions, circleRadius, color) {
	var circle = new google.maps.Circle({
		center : new google.maps.LatLng(congestions[0], congestions[1]),
		title : congestions[2],
		radius : circleRadius,
		strokeColor : color,
		strokeOpacity : 0.8,
		strokeWeight : 2,
		fillColor : color,
		fillOpacity : 0.4,
		map : map,
	});

	var listeners = [];
	listeners.push(google.maps.event.addListener(circle, 'mouseover',
			function() {
				map.getDiv().setAttribute('title', this.get('title'));
			}));
	listeners.push(google.maps.event.addListener(circle, 'mouseout',
			function() {
				map.getDiv().removeAttribute('title');
			}));
	listeners.push(google.maps.event.addListener(circle, 'click', function() {
		window.location.href = "history?placeId="+congestions[3];
	}));
	return {
		'circle' : circle,
		'listeners' : listeners,
	};
}

function addCircles(map, congestions, circleRadius, color) {
	var circles = [];
	for (i = 0; i < congestions.length; i++) {
		circles.push(addCircle(map, congestions[i], circleRadius, color));
	}
	return circles;
}

function removeCircles(circleList) {
	for (var i = 0; i < circleList.length; i++) {
		var listenerHandles = circleList[i]['listeners'];
		for (var j = 0; j < listenerHandles.length; j++) {
			google.maps.event.removeListener(listenerHandles[j]);
		}
		circleList[i]['circle'].setMap(null);
	}
}