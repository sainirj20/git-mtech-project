<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.Iterator"%>
<%@ page import="com.traffic.model.Place"%>
<!DOCTYPE html>

<html>

<head>
<meta charset="ISO-8859-1">
<title>Hello World</title>
<jsp:useBean id="marker" class="com.traffic.map.Marker" />

<script
	src="http://maps.google.com/maps/api/js?sensor=false&key=AIzaSyCd3gQRLIhHL7RPXWuMp2xwv3qlv662h7k"
	type="text/javascript"></script>
</head>

<body>
	<div id="map" style="width: 1200px; height: 600px;"></div>
	<script type="text/javascript">
	var locations = [
	<%Iterator<Place> itr = marker.get().iterator();
	while (itr.hasNext()) {
		Place p = itr.next();%>
		["<%=p.getAddress()%>",<%=p.getLat()%>,<%=p.getLng()%> ],
	<%}%>
    ];

	var map = new google.maps.Map(document.getElementById('map'), {
	      zoom: 12,
	      center: new google.maps.LatLng(12.972442, 77.580643),
	      mapTypeId: google.maps.MapTypeId.ROADMAP
	    });

	    var infowindow = new google.maps.InfoWindow();

	    var marker, i;
	    
	    console.log(locations.length);
	    
	    for (i = 0; i < locations.length; i++) {  
	      marker = new google.maps.Circle({
	    	  center: new google.maps.LatLng(locations[i][1], locations[i][2]),
	    	  radius: 50,
	    	  strokeColor: "#0000FF",
	    	  strokeOpacity: 0.8,
	    	  strokeWeight: 2,
	    	  fillColor: "#0000FF",
	    	  fillOpacity: 0.4,
	          map: map,
	      });

	      google.maps.event.addListener(marker, 'click', (function(marker, i) {
	        return function() {
	          infowindow.setContent(locations[i][0]);
	          infowindow.open(map, marker);
	        }
	      })(marker, i));
	    }
 </script>

</body>

</html>