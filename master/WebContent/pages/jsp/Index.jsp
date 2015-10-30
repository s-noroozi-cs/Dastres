<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>

<html dir="ltr">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<title>Master Management System</title>
</head>
<frameset cols="*,130" framespacing="0" border="1">
	<frame src="<%=request.getContextPath()%>/pages/html/MenuIntro.html"
		name="rbottom" frameborder="0" scrolling="yes" marginwidth="0"
		marginheight="0" />
	<frame src="<%=request.getContextPath()%>/pages/jsp/MainMenu.jsp" name="left" frameborder="0" scrolling="auto" />
</frameset>
<noframes>
<body>
<p>This page uses frames, but your browser doesn't support them.</p>
</body>
</noframes>
</html>