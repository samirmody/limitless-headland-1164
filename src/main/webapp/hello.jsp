<html>
    <head>
        <title>Hello Servlet Page</title>
    </head>
    <body>
        <p>Get</p>
        <dl>
            <dt>test_var:</dt>
            <dd><%= request.getAttribute("test_var") %></dd>
            <dt>SQLException:</dt>
            <dd><%= request.getAttribute("SQLException") %></dd>
            <dt>URISyntaxException:<dt>
            <dd><%= request.getAttribute("URI Syntax Exception") %></dd>
            <dt>test_var2:</dt>
            <dd><%= request.getAttribute("test_var2") %></dt>
        </dl>
        <p>Post</p>
        <dl>
            <dt>name1:</dt>
            <dd><%= request.getAttribute("name1") %></dd>
        </dl>
    </body>
</html>
