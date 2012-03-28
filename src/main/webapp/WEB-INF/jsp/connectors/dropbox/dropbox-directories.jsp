<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Integrails Dropbox - Directories</title>
        <%@include file="/WEB-INF/jsp/common/include_tags.jsp"%>
    </head>
	<body>
		<h1>Directories</h1>
		<p>Found ${size} directories</p>
		
		<hr />
		
		<c:if test="${size > 0}">
	        <div style="margin-top: 40px;">
	            <table width="80%" border="1px;" style="font-size: 12px; margin-left:auto; margin-right:auto;">
	                <tr>
	                    <th>ID</th>
	                    <th width="228px;">Account ID</th>
	                    <th>Directory</th>
	                    <th>Hash</th>
	                    <th>Modified</th>
	                    <th>Last Checked</th>
	                    <th>Last Processed</th>
	                    <th>Is Updated</th>
	                    <th>Locked By</th>
	                </tr>
			
					<c:forEach var="directory" items="${directories}" >
						<tr>
							<td>${directory.id}</td>
							<td>${directory.accountId}</td>
							<td>${directory.directory}</td>
							<td>${directory.hash}</td>
							<td>${directory.modified}</td>
							<td>${directory.lastCheck}</td>
							<td>${directory.lastProcessed}</td>
							<td>${directory.isUpdated}</td>
							<td>${directory.lockedBy}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</c:if>			
	</body>
</html>