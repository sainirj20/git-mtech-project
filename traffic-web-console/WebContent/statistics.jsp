<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Map" %>
<%@ page import="com.traffic.model.Congestion" %>
<!DOCTYPE html>
<html>
<% session.setAttribute("pageName", "STATISTICS"); %>
<%@ include file = "header.jsp" %>
<jsp:useBean id="chartsData" class="com.traffic.map.Charts" />
<jsp:useBean id="congestionSizePie" class="com.traffic.map.CongestionSize" />

<script> function loadMap() {} </script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

<%	String placeId = request.getParameter("placeId");
	Integer speed = (null != placeId) ? chartsData.getSpeedLimit(placeId) : 0;
%>

<script type="text/javascript"> google.charts.load('current', {'packages':['gauge', 'corechart', 'bar']}); </script>

<script type="text/javascript">
	function drawSpeedGaugeChart() {
		var data = google.visualization.arrayToDataTable([['Label', 'Value'],['Speed', <%=speed%>],]);
		var options = {
			title: 'The decline of \'The 39 Steps\'',
			width: 240, height: 240,
			redFrom: 90, redTo: 100,
			yellowFrom:75, yellowTo: 90,
			minorTicks: 5
		};
		var chart = new google.visualization.Gauge(document.getElementById('speedGauge'));
		chart.draw(data, options);
	}
</script>

<script type="text/javascript">
	function drawTodaysHistoryChart() {
		var data = google.visualization.arrayToDataTable(<%=chartsData.getTodaysHistory(placeId) %> );
		var options = {
			chartArea: { left: 50, bottom: 50, top: 20, width: "100%", height: "100%" },
			width: 400, height: 300,
			vAxis: {title: 'Duration in Minutes'},
			hAxis: {title: 'Time'},
			legend: { position: 'none' },
		};
		var chart = new google.visualization.SteppedAreaChart(document.getElementById('todaysHistoryChart'));
		chart.draw(data, options);
	}
</script>

<script type="text/javascript">
	function drawWeeksHistoryChart() {
		var data = google.visualization.arrayToDataTable(<%=chartsData.getWeeksHistory(placeId) %> );
		var options = { chartArea: { left: 50, bottom: 50, top: 20, width: "100%", height: "100%"},
			series: { 0: { color: '#FF0000' }},
			width: 400, height: 300,
			vAxis: {title: 'Duration in Minutes'},
			hAxis: {title: 'Time'},
			legend: { position: 'none' },
		};
		var chart = new google.visualization.SteppedAreaChart(document.getElementById('WeeksHistoryChart'));
		chart.draw(data, options);
	}
</script>

<script type="text/javascript">
	function drawCongestionsBarChart() {
		var data = google.visualization.arrayToDataTable(<%=chartsData.congestionsHistory() %> );
		var options = { 
			chartArea: { left: 50, bottom: 50, top: 20, width: "100%", height: "100%"},
			series: { 0: { color: '#FF3399'}, 1: { color: '#0000FF'}, 2: { color: '#FF0000'}},
			width: 600, height: 400,
			vAxis: {title: 'Congestions'},
			hAxis: {title: 'Time'},
			legend: { position: 'none' },
		};
		var chart = new google.charts.Bar(document.getElementById('congestionsBarChart'));
		chart.draw(data, options);
	}
</script>

<script type="text/javascript">
	function drawCongestionsPieChart() {
		<% Map<Congestion.CongestionType, Integer> pieCount = congestionSizePie.getCongestionsCount(); %>
		 var data = google.visualization.arrayToDataTable([
	          ['Task', 'Hours per Day'],
	          ['Small', <%= pieCount.get(Congestion.CongestionType.SMALL)%>],
	          ['Large', <%= pieCount.get(Congestion.CongestionType.LARGE)%>],
	          ['Unusual', <%= pieCount.get(Congestion.CongestionType.UNUSUAL)%>],
	        ]);
		var options = { 
			chartArea: {width: "90%", height: "90%"},
			height: 350,
			legend: { position: 'none' },
		};
		var chart = new google.visualization.PieChart(document.getElementById('congestionsPieChart'));
		chart.draw(data, options);
	}
</script>

<script type="text/javascript">
	google.charts.load('current', {'packages':['gauge', 'corechart', 'bar']});
	google.charts.setOnLoadCallback(drawSpeedGaugeChart);
	google.charts.setOnLoadCallback(drawTodaysHistoryChart);
	google.charts.setOnLoadCallback(drawWeeksHistoryChart);
	google.charts.setOnLoadCallback(drawCongestionsPieChart);
	google.charts.setOnLoadCallback(drawCongestionsBarChart);
</script>

<body>

<div style="margin: 2% 8% 0 8%; height: 100%; border: 1px solid #3872ac;">
<%if (null != placeId) { %>
	<div class="flex-container">
		<div style="width: 240px; height: 240px; background-color: unset;">
			<div>Speed Limit</div>
			<div id="speedGauge"></div>
		</div>
		<div style="width: 400px; background-color: unset;">
			<div>Today's History</div>
			<div id="todaysHistoryChart"></div>
		</div>
		<div style="width: 500px; height: 350px; background-color: unset;">
			<div>Week's History</div>
			<div id="WeeksHistoryChart"></div>
		</div>
	</div>
	<hr />
<%} %>
	<div class="flex-container">
		<div style="width: 40%; background-color: unset;">
			<div>Congestions Count</div>
			<div id="congestionsPieChart"></div>
		</div>
		<div style="width: 50%; background-color: unset;">
			<div>All Congestions</div>
			<div id="congestionsBarChart"></div>
		</div>
	</div>
</div>

</body>

<%@ include file = "footer.jsp" %>
</html>