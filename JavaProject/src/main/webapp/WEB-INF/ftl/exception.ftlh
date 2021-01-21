<#import "macro.ftlh" as m />
<#import "spring.ftl" as spring/>

<html lang="en">
    <head>
        <@m.htmlHeader title="Exception"/>
        <link rel="stylesheet" type="text/css" href="<@spring.url '/static/css/exception/exception-styles.css' />">
        <script type ="text/javascript" src="<@spring.url '/static/js/exception/exception-main.js' />"></script>
    </head>
    <body>
        <div id="select_values" <#if user_background??>user_background="${user_background}"</#if>></div>
        <#if login??>
            <@m.pageHeader isSigned="${is_signed}" login="${login}"/>
        <#else>
            <@m.pageHeader isSigned="${is_signed}"/>
        </#if>
        <div class="container" id="mainContainer">
            <div class="bg-dark info">
                <#if message??>
                    <h3 class="text-light">${message}</h3>
                </#if>
            </div>
            <div class="bg-dark info">
                <a href="<@spring.url '/main' />">Return to the main page.</a>
            </div>
        </div>
        <@m.htmlFooter/>
    </body>
</html>