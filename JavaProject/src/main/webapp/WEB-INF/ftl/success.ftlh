<#import "macro.ftlh" as m />
<#import "spring.ftl" as spring/>

<html lang="en">
    <head>
        <@m.htmlHeader title="Success"/>
        <link rel="stylesheet" type="text/css" href="<@spring.url '/static/css/success/success-styles.css' />">
        <script type ="text/javascript" src="<@spring.url '/static/js/success/success-main.js' />"></script>
    </head>
    <body>
        <div id="select_values"
             <#if user_background>user_background="${user_background}"</#if>></div>
        <#if login??>
            <@m.pageHeader isSigned="${is_signed}" login="${login}"/>
        <#else>
            <@m.pageHeader isSigned="${is_signed}"/>
        </#if>
        <div class="container" id="mainContainer">
            <div class="bg-dark info">
                <h2 class="text-success">Thank you for registering</h2>
                <h3 class="text-light">Your account has been successfully registered on our website</h3>
                <p>
                    <a href="<@spring.url '/sign-in' />" class="text-light">Login to your account</a>
                </p>
                <p>
                    <a href="<@spring.url '/main' />" class="text-light">Go to home page</a>
                </p>
            </div>
        </div>
        <@m.htmlFooter/>
    </body>
</html>