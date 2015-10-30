<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
     <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/pages/css/blue.css " />
    <title>Main Menu</title>
  </head>
  <body class="clsInputPage">
    <table class="clsInputPage">
     
      <tr>
        <td height="30" class="clsCellAlignDefault">
           <a target="rbottom" href="<%=request.getContextPath()%>/ImportData.do">Import Data</a>
        </td>
      </tr>
     
      <tr>
        <td height="30" class="clsCellAlignDefault">
        	<a target="rbottom" href="<%=request.getContextPath()%>/SlaveManagement.do">Slave Management</a>
        </td>
      </tr>
      
      <tr>
        <td height="30" class="clsCellAlignDefault">
        	<a target="rbottom" href="<%=request.getContextPath()%>/UserLoc.do?method=list">User Location Management</a>
        </td>
      </tr>

      <tr>
        <td height="30" class="clsCellAlignDefault">
        	<a target="rbottom" href="<%=request.getContextPath()%>/SystemSettings.do">System Setting</a>
        </td>
      </tr>
      
      <tr>
        <td height="30" class="clsCellAlignDefault">
        	<a target="rbottom" href="<%=request.getContextPath()%>/DataTransfer.do">Data Transfer</a>
        </td>
      </tr>
      
      <tr>
        <td height="30" class="clsCellAlignDefault">
        	<a target="rbottom" href="<%=request.getContextPath()%>/SearchLog.do">Search Log</a>
        </td>
      </tr>     
    
    </table>
  </body>
</html>
