<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Polling Test Page</title>

        <style type="text/css">
            .inputValue {
                width:400px;
            }
        </style>
    </head>
    <body>
        <h1>Polling Test Page</h1>
        <a href="${ctx}/${testUriSecretPrefix}/test">Go to main page</a>
        <hr />
        <div>
            <b>Poll Feedback through form (on /polling/user/feedback)</b>
            <a href="${ctx}/polling/user/feedback">eeee</a>
            <form action="${ctx}/${testUriSecretPrefix}/test/polling/user/feedback" method="post">
                CompanyAccountId: <input type="text" name="companyAccountId" value="" class="inputValue" />
                <br />
                Feedback Pg: <input type="text" name="feedback_pg" value="" class="inputValue" />
                <br />
                Feedback Pg Int: <input type="text" name="feedback_pg_int" value="" class="inputValue" />
                <br />
                Feedback Txt: <input type="text" name="feedback_txt" value="" class="inputValue" />
                <br />
                <input type="submit" value="Go" />
            </form>
        </div>
        
        <hr />
        <div>
            <b>Get All Feedbacks (on /${testUriSecretPrefix}/test/polling/feedbacks)</b>
            <br />
            <a href="${ctx}/${testUriSecretPrefix}/test/polling/user/feedbacks">View all feedbacks</a>
        </div>
    </body>
</html>
