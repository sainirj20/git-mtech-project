<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<% session.setAttribute("pageName", "HOME"); %>
<%@ include file = "header.jsp" %>
<jsp:useBean id="congestionsOnMap" class="com.traffic.map.CongestionsOnMap" />

<body>

	<script>
		if(<%=congestionsOnMap.hasNewCongestion() %>) { location.reload(); 	}
	</script>

	<%congestionsOnMap.reload();%>
	<div style="margin: 2% 8% 0 8%; height: 500px; border: 2px solid #3872ac;">
		<div id="map" style="width: 100%; height: 100%"></div> 
	</div>
	<script type="text/javascript" src="http://maps.google.com/maps/api/js?key=AIzaSyCd3gQRLIhHL7RPXWuMp2xwv3qlv662h7k"> </script>
	<script src="js/maps.js"></script>
	<script>
		function applyFilter() {
			const map = new google.maps.Map(document.getElementById('map'), {
			  zoom: 13,
			  center: new google.maps.LatLng(12.972442, 77.580643),
			  mapTypeId: google.maps.MapTypeId.ROADMAP
			});
		
			var smallCircle = [];
			if (document.getElementById("smallCongestions").checked) {
				smallCircle = addCircles(map, <%=congestionsOnMap.getSmallCongestions() %>, 30, "#FF3399");
			} 
			
			var largeCircle = [];
			if(document.getElementById("largeCongestions").checked) {
				largeCircle = addCircles(map, <%=congestionsOnMap.getLargeCongestions() %>, 40, "#0000FF");
			} 
			
			var unUsualCircle = [];			
			if(document.getElementById("unusualCongestions").checked) {
				largeCircle = addCircles(map, <%=congestionsOnMap.getUnUnsualCongestions() %>, 50, "#FF0000");
			}
		}
		google.maps.event.addDomListener(window, 'load', applyFilter);
	</script>
</body>

<%@ include file = "footer.jsp" %>
</html>