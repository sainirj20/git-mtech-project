<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Road Traffic</title>
	<link href="https://fonts.googleapis.com/css?family=Nunito+Sans:700%7CNunito:300,600" rel="stylesheet"> 
	<link type="text/css" rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="css/font-awesome.min.css">
	<link type="text/css" rel="stylesheet" href="css/style.css"/>
	
	<%	response.setHeader("Cache-Control","no-cache"); 
		response.setHeader("Pragma","no-cache"); 
		response.setDateHeader("Expires", -1); 
		response.setHeader("Cache-Control","no-store");
	%>

</head>

<script src="js/onload.js"></script>

<script>
	function loadClock(){
		var dt = new Date();
		var dateString = dt.toString().replace(' GMT+0530 (India Standard Time)', '');
		var lastUpdates = dateString.slice(dateString.length - 8, dateString.length - 4) + '0';
		document.getElementById("currentDate").innerHTML = dateString;
		document.getElementById("lastUpdated").innerHTML = 'Map Updated at ' + lastUpdates;
		setTimeout(loadClock, 500);
	}
</script>

<body onload="onLoad()">

<div id="header">
	<div id="nav">
		<div id="nav-fixed">
			<div class="container">
				<div class="nav-logo"><a href="home.jsp" class="logo"><img src="./img/trafic_jam2.jpg" alt=""></a></div>
				<ul class="nav-menu nav navbar-nav">
					<li class="cat-1">
						<a href="home.jsp">
						<font <% if(session.getAttribute("pageName").equals("HOME")) {%> color="#4BB92F"<%}%> >	Home </font>
						</a>
					</li>
					<li class="cat-2">
						<a href="#">
						<font <% if(session.getAttribute("pageName").equals("HISTORY")) {%> color="#ff8700"<%}%> >History</font>
						</a>
					</li>
					<li class="cat-4">
						<a href="#">
						<font <% if(session.getAttribute("pageName").equals("CHARTS")) {%> color="#8d00ff"<%}%> >Charts</font>
						</a>
					</li>
					<li><div style="margin-right: 140px" class="nav-menu-time-display"><div>&nbsp;&nbsp;&nbsp;</div></div></li>
					<li><div class="nav-menu-time-display"><img src="./img/refresh.png" alt="" style="height: 32px; margin-left: 62px;"><div id="lastUpdated"></div></div></li>
					<li><div class="nav-menu-time-display"><img src="./img/time.png" alt="" style="height: 32px; margin-left: 90px;"><div id="currentDate"></div></div></li>
				</ul>
				<% if(session.getAttribute("pageName").equals("HOME")) {%> 
					<div class="nav-btns"><button class="aside-btn"><i class="fa fa-bars"></i></button></div>
				<%}%>
			</div>
		</div>

		<div id="nav-aside">
			<div class="section-row">
				<h3>Apply Filter</h3>
				<h4>Set Congestion Type</h4>
				<ul class="nav-aside-menu">
					<li>
						<label class="switch">
							<input type="checkbox" id="smallCongestions"checked>
							<span class="slider"></span> <span style="margin-left:45px">Small</span>
						</label>
					</li>
					<li>
						<label class="switch">
							<input type="checkbox" id="largeCongestions" checked>
							<span class="slider"></span> <span style="margin-left:45px">Large</span>
						</label>
					</li>
					<li>
						<label class="switch">
							<input type="checkbox" id="unusualCongestions" checked>
							<span class="slider"></span> <span style="margin-left:45px">Unusual</span>
						</label>
					</li>
				</ul>
				<button class="applyButton" onclick="applyFilter()">apply</button>
				<button class="applyButton">close</button>
			</div>
			<button class="nav-aside-close"><i class="fa fa-times"></i></button>
		</div>
	
	</div>
</div>
</body>