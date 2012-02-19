<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Polling Test Page - Feedbacks</title>
        <%@include file="/WEB-INF/jsp/common/include_tags.jsp"%>
    </head>
    <body>
        <h1>Polling Test Page - Feedbacks</h1>
        <a href="${ctx}/${testUriSecretPrefix}/test/polling">Go to previous page</a>
        <hr />
        <div>
            <table width="80%" border="1" style="font-size: 12px;">
                <tr>
                    <th>Company Acc Id</th>
                    <th>Feedback Pg</th>
                    <th>Feedback Pg Int</th>
                    <th>Feedback Txt</th>
                </tr>
                <c:forEach var="userFeedback" items="${userFeedbacks}">
	                <tr>
	                    <td>${userFeedback.companyAccountId}</td>
	                    <td>${userFeedback.feedbackPg}</td>
	                    <td>${userFeedback.feedbackPgInt}</td>
	                    <td>${userFeedback.feedbackTxt}</td>
	                </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>
