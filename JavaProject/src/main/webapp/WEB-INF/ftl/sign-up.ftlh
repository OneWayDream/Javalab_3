<#import "macro.ftlh" as m />
<#import "spring.ftl" as spring/>

<html lang="en">
    <head>
        <@m.htmlHeader title="Sign Up"/>
        <!-- Font Icon -->
        <link rel="stylesheet" type="text/css" href="<@spring.url '/static/css/sign/material-design-iconic-font.min.css' />">
        <!-- Main css -->
        <link rel="stylesheet" type="text/css" href="<@spring.url '/static/css/sign/style.css' />">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/static/css/sign/sign-forms.css' />">
        <script type ="text/javascript" src="<@spring.url '/static/js/sign/sign-main.js' />"></script>
    </head>
    <body>
        <div id="select_values"
             <#if user_background??>user_background="${user_background}"</#if>></div>
        <#if login??>
            <@m.pageHeader isSigned="${is_signed}" login="${login}"/>
        <#else>
            <@m.pageHeader isSigned="${is_signed}"/>
        </#if>
        <div >
            <section >
                <div class="container bg-dark">
                    <div class="signup-content">
                        <div class="signup-form">
                            <h2 class="form-title text-light">Sign up</h2>
                            <#if message??>
                                <h3 class="text-danger">${message}</h3>
                            </#if>
                            <form method="POST" class="register-form" id="register-form">
                                <div class="form-group">
                                    <label for="login"><i class="zmdi zmdi-account material-icons-name"></i></label>
                                    <input type="text" name="login" id="login" placeholder="Your Login"
                                           <#if user_login??>value="${user_login}"</#if> required minlength="3" maxlength="30"/>
                                </div>
                                <div class="form-group">
                                    <label for="email"><i class="zmdi zmdi-email"></i></label>
                                    <input type="email" name="email" id="email" placeholder="Your Email (example - example@exam.ple)"
                                           <#if user_email??>value="${user_email}"</#if> required pattern="[\w\-]+@([\w\-]+\.)+([\w\-]+)$" maxlength="30"/>
                                </div>
                                <div class="form-group">
                                    <label for="pass"><i class="zmdi zmdi-lock"></i></label>
                                    <input type="password" name="pass" id="pass" placeholder="Password" required minlength="8" maxlength="40"/>
                                </div>
                                <div class="form-group">
                                    <label for="re_pass"><i class="zmdi zmdi-lock-outline"></i></label>
                                    <input type="password" name="re_pass" id="re_pass" placeholder="Repeat your password" required minlength="8" maxlength="40"/>
                                </div>
                                <div class="form-group">
                                    <input type="checkbox" name="agree-term" id="agree-term" class="agree-term" required/>
                                    <label for="agree-term" class="label-agree-term text-light"><span><span></span></span>I agree all statements in  <a href="#" class="term-service">Terms of service</a></label>
                                </div>
                                <input type="hidden" name="user_background" id="user_background"
                                       <#if user_background??>value="${user_background}"</#if>>
                                <div class="form-group form-button">
                                    <input type="submit" name="signup" id="signup" class="form-submit" value="Register"/>
                                </div>
                            </form>
                            <a href="<@spring.url '/sign-in' />" class="signup-image-link text-light">I am already member</a>
                        </div>
                    </div>
                </div>
            </section>
        </div>

        <!-- JS -->
        <script type ="text/javascript" src="<@spring.url '/static/js/sign/jquery-ui.min.js' />"></script>
        <@m.htmlFooter/>
    </body>
</html>