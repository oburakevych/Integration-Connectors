<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test Page</title>

        <style type="text/css">
            .inputValue {
                width:400px;
            }
        </style>
    </head>
    <body>
        <h1>EEE Test Tradeshft APIIntegration Page</h1>

        <hr />
        <div>
            <b>Imitate getappsettings</b>
            <form action="https://localhost:3080/sandbox-payments-server/test/tradeshift/appsettings" method="get">
                CompanyAccountId: <input type="text" name="companyAccountId" value="" class="inputValue" />
                <br />
                <input type="submit" value="Go" />
            </form>
        </div>
        
        <hr />
        <div>
            <b>Imitate callback (on http://localhost:3443/sandbox-payments-server/callback/tradeshift/oauth)</b>
            <form action="https://localhost:3080/sandbox-payments-server/callback/tradeshift/oauth" method="post">
                CompanyAccountId: <input type="text" name="companyAccountId" value="" class="inputValue" />
                <br />
                AccessToken: <input type="text" name="oauth_token" value="" class="inputValue" />
                <br />
                AccessTokenSecret: <input type="text" name="oauth_token_secret" value="" class="inputValue" />
                <br />
                <input type="submit" value="Go" />
            </form>
        </div>

        <hr />
        <div>
            <b>Get Auth Access Credentials</b>
            <form action="https://localhost:3080/sandbox-payments-server/test/tradeshift/auth/accessCredentials" method="get">
                CompanyAccountId: <input type="text" name="companyAccountId" value="" class="inputValue" />
                <br />
                <input type="submit" value="Go" />
            </form>
        </div>
    </body>
</html>
