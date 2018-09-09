<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.Iterator"%>
<%@ page import="com.traffic.model.Place"%>
<!DOCTYPE html>

<html>

<head>
	<meta charset="ISO-8859-1">
	<title>Hello World</title>
	<script 
		src="http://maps.google.com/maps/api/js?sensor=false&key=AIzaSyCd3gQRLIhHL7RPXWuMp2xwv3qlv662h7k"
		type="text/javascript">
	</script>
	<jsp:useBean id="groups" class="com.traffic.map.Groups" />
</head>

<body>
	<div id="map" style="width: 1200px; height: 600px;"></div>
	<script type="text/javascript">
	<%groups.init();%>
	var smallCongestions = [
		<%Iterator<Place> itr = groups.getSmallCongestions().iterator();
		while (itr.hasNext()) {
			Place p = itr.next();%> [<%=p.getLat()%>, <%=p.getLng()%>, "<%=p.getAddress()%>" ],
		<%}%>
    ];
	
	var largeCongestions = [
		<%Iterator<Place> itr2 = groups.getLargeCongestions().iterator(); 
		while (itr2.hasNext()) {
			Place p = itr2.next();%> [<%=p.getLat()%>, <%=p.getLng()%>, "<%=p.getAddress()%>" ],
		<%}%>
    ];
	
	var unUnsualCongestions = [
		<%Iterator<Place> itr3 = groups.getUnUnsualCongestions().iterator(); 
		while (itr3.hasNext()) {
			Place p = itr3.next();%> [<%=p.getLat()%>, <%=p.getLng()%>, "<%=p.getAddress()%>" ],
		<%}%>
    ];

	var map = new google.maps.Map(document.getElementById('map'), {
      zoom: 12,
      center: new google.maps.LatLng(12.972442, 77.580643),
      mapTypeId: google.maps.MapTypeId.ROADMAP
    });
	
	for (var i = 0; i < largeCongestions.length; i++) {
		var marker = new google.maps.Circle({
			center: new google.maps.LatLng(largeCongestions[i][0], largeCongestions[i][1]),
			radius: 40,
			strokeColor: "#0000FF",
			strokeOpacity: 0.8,
			strokeWeight: 2,
			fillColor: "#0000FF",
			fillOpacity: 0.4,
			clickable: true,
			map: map,
		});

		marker.addListener('click', function(){
			var infowindow =  new google.maps.InfoWindow({
				content: largeCongestions[i][2],
				position: marker.getCenter(),
			});
			infowindow.open(map);
		});

	}

	for (i = 0; i < smallCongestions.length; i++) {
		var marker = new google.maps.Circle({
			center: new google.maps.LatLng(smallCongestions[i][0], smallCongestions[i][1]),
			radius: 30,
			strokeColor: "#FF3399",
			strokeOpacity: 0.8,
			strokeWeight: 2,
			fillColor: "#FF3399",
			fillOpacity: 0.4,
			clickable:true,
			map: map,
		});
	}

	for (var i = 0; i < unUnsualCongestions.length; i++) {
		var marker = new google.maps.Circle({
			center: new google.maps.LatLng(unUnsualCongestions[i][0], unUnsualCongestions[i][1]),
			radius: 50,
			strokeColor: "#FF0000",
			strokeOpacity: 0.8,
			strokeWeight: 2,
			fillColor: "#FF0000",
			fillOpacity: 0.4,
			clickable:true,
			map: map,
		});
	}
 </script>

</body>

</html>