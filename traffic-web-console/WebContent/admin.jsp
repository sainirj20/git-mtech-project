<%@page import="com.traffic.dao.UsersDao"%>
<%@page import="com.traffic.dao.GeoLocationDao"%>
<%@page import="com.traffic.utils.GeoLoactionUtil"%>
<%@page import="com.traffic.model.GeoLocation"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<% session.setAttribute("pageName", "ADMIN"); %>
<%@ include file = "header.jsp" %>
<jsp:useBean id="congestionsOnMap" class="com.traffic.map.CongestionsOnMap" />

<script>
	function loadMap() {}
</script>

<body>
<%
	GeoLocationDao geoLocationDao = new GeoLocationDao();
	GeoLocation geoLocation = geoLocationDao.getDetails();
	String setLocation = request.getParameter("setLocation");
	if (null != setLocation) {
		setLocation = null;
		try {
			geoLocation.setName(request.getParameter("cityName"));
			geoLocation.setZoom(Integer.parseInt(request.getParameter("zoomLevel")));
			geoLocation.setLatitude(Double.parseDouble(request.getParameter("latitude")));
			geoLocation.setLongitude(Double.parseDouble(request.getParameter("longitude")));
			Integer [] tile =  GeoLoactionUtil.getGeoTile(geoLocation.getLatitude(), geoLocation.getLongitude(), geoLocation.getZoom());
			geoLocation.setTileX(tile[0]);
			geoLocation.setTileY(tile[1]);
			geoLocationDao.addOrUpdate(geoLocation);
			congestionsOnMap.resetGeoLocation();
		}catch(Exception e) {}
	}
	
	String addUser = request.getParameter("addUser");
	if (null != addUser) {
		try {
			UsersDao usersDao = new UsersDao();
			usersDao.addAll(
					request.getParameter("userName1"),
					request.getParameter("userName2"),
					request.getParameter("userName3"),
					request.getParameter("userName4"),
					request.getParameter("userName5"));
		}catch(Exception e) {}
	}
%>

<div style="margin: 2% 8% 0 8%; height: 310px; border: 1px solid #3872ac;">
	<div class="flex-container">
		<div>
		<form action="admin.jsp" method="post">
			<table style="margin: 3%">
				<tr> <td colspan="3" style="text-align: center;"> <h2> Add Users </h2></td><tr>
				<tr> <td>User Name 1</td> <td>&nbsp;&nbsp;:&nbsp;&nbsp;</td> <td><input type="text" name="userName1"></td> </tr>
				<tr> <td>User Name 2</td> <td>&nbsp;&nbsp;:&nbsp;&nbsp;</td> <td><input type="text" name="userName2"></td> </tr>
				<tr> <td>User Name 3</td> <td>&nbsp;&nbsp;:&nbsp;&nbsp;</td> <td><input type="text" name="userName3"></td> </tr>
				<tr> <td>User Name 4</td> <td>&nbsp;&nbsp;:&nbsp;&nbsp;</td> <td><input type="text" name="userName4"></td> </tr>
				<tr> <td>User Name 5</td> <td>&nbsp;&nbsp;:&nbsp;&nbsp;</td> <td><input type="text" name="userName5"></td> </tr>
				<tr> 
					<td colspan="2"></td> 
					<td style="text-align: end;" > 
						<input type="submit" name="addUser" class="applyButton" value="Add Users">
					</td>
				</tr>
			</table>
		</form>
		</div>
		
		<div>
		<form name="location-form" action="admin.jsp" method="post">
			<table style="margin: 3%">
				<tr> <td colspan="3" style="text-align: center;"> <h2> Set Location </h2></td><tr>
				<tr> <td>City Name</td> <td>&nbsp;&nbsp;:&nbsp;&nbsp;</td> 
					<td><input type="text" value="<%=geoLocation.getName() %>" name="cityName"></td> 
				</tr>
				<tr> <td>Zoom Level</td> <td>&nbsp;&nbsp;:&nbsp;&nbsp;</td> 
					<td><input type="text" value="<%=geoLocation.getZoom() %>" name="zoomLevel"></td> 
				</tr>
				<tr> <td>Latitude </td> <td>&nbsp;&nbsp;:&nbsp;&nbsp;</td> 
					<td><input type="text" value="<%=geoLocation.getLatitude() %>" name="latitude"></td> 
				</tr>
				<tr> <td>Longitude</td> <td>&nbsp;&nbsp;:&nbsp;&nbsp;</td> 
					<td><input type="text" value="<%=geoLocation.getLongitude() %>" name="longitude"></td> 
				</tr>
				<tr> 
					<td colspan="2"></td> 
					<td style="text-align: end;" > 
						<input type="submit" name="setLocation" class="applyButton" value="Set Location">
					</td>
				</tr>
			</table>
		</form>
		</div>
		
	</div>
</div>
</body>

<%@ include file = "footer.jsp" %>
</html>