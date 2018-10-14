<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Map" %>
<%@ page import="com.traffic.model.Congestion" %>

<body>
<footer id="footer">
	<div class="container">
		<div class="row">
			<div class="col-md-5" style="width: 38%">
				<div class="footer-widget">
					<div class="footer-logo">
						<a href="home.jsp" class="logo"><img src="./img/jam-words.png" alt="" height="150px"></a>
					</div>
				</div>
			</div>

			<div class="col-md-4">
				<div class="row">
					<div class="col-md-6">
						<div class="footer-widget">
							<h3 class="footer-title">Company</h3>
							<ul class="footer-links">
								<li><a href="#">About Us</a></li>
								<li><a href="#">Contacts</a></li>
							</ul>
						</div>
					</div>
					<div class="col-md-6">
						<div class="footer-widget">
							<h3 class="footer-title">Explore</h3>
							<ul class="footer-links">
								<li><a href="home.jsp">Home</a></li>
								<li><a href="#">Statistics</a></li>
								<li><a href="admin.jsp">Admin</a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>

			<div class="col-md-3">
			<jsp:useBean id="congestionSize" class="com.traffic.map.CongestionSize" />
			<% Map<Congestion.CongestionType, Integer> countMap = congestionSize.getCongestionsCount(); %>
				<div class="footer-widget">
					<h3 class="footer-title">Current Congestions</h3>
					<ul class="footer-links">
						<li>Small : <%= countMap.get(Congestion.CongestionType.SMALL)%> </li>
						<li>Large : <%= countMap.get(Congestion.CongestionType.LARGE)%> </li>
						<li>Unusual : <%= countMap.get(Congestion.CongestionType.UNUSUAL)%></li>
					</ul>
				</div>
			</div>

		</div>
	</div>
</footer>
</body>

<body>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/main.js"></script>
</body>
</html>