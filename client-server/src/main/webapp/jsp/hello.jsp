<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hello World</title>

<link href="/<%=(String)application.getInitParameter("appName")%>/resources/_css/site.css" rel="stylesheet">
</head>
<body>
<div class="wrapper">
	<header>
		INSIGHT
	</header>
	<div class="content">
		<div class="main">
			<h1>Message from bookmarkService</h1>
			<p>Message is : <%=response.getHeader("xcorg")%></p>
		</div>
	</div>
	<footer>
		<p>By Philips Lighting</p>
	</footer>
</div>
</body>
    
</html>