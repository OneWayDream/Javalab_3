<#import "spring.ftl" as spring/>

<#macro htmlHeader title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${title}</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <link rel=stylesheet href="https://unpkg.com/bootstrap@4.3.1/dist/css/bootstrap.css" />
    <link rel=stylesheet href="https://unpkg.com/bootstrap-select@1.13.8/dist/css/bootstrap-select.css" />


    <link rel="stylesheet" type="text/css" href="<@spring.url '/static/css/main-style.css' />">

    <script type ="text/javascript" src="<@spring.url '/static/js/change-background.js' />"></script>
    <script type ="text/javascript" src="<@spring.url '/static/js/button_background.js' />"></script>
    <script type ="text/javascript" src="<@spring.url '/static/js/set-image.js' />"></script>
</#macro>

<#macro htmlFooter>
<#--    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>-->
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="<@spring.url '/static/js/bootstrap/bootstrap.bundle.min.js' />"></script>
    <script src="https://unpkg.com/bootstrap-select@1.13.8/dist/js/bootstrap-select.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
</#macro>

<#macro fuelOptions fuelsList fuelsLogosList>
    <option value="SELECT" data-content="<img src='<@spring.url '/static/images/fuels/SELECT.png' />' width='30' /> Empty"></option>
    <#list fuelsList as fuels>
        <option
            value="${fuelsLogosList.get(fuels ? index)}"
            data-content="<img src='<@spring.url '/static/images/fuels/${fuelsLogosList.get(fuels ? index)}.png' />' width='30' /> ${fuels}">
        </option>
    </#list>
</#macro>

<#macro minionOptions minionsList minionsLogosList>
    <#list 0..minionsList?size-1 as i>
        <option
            value=${minionsLogosList[i]}
            data-content="<img src='<@spring.url '/static/images/minions_head/${minionsLogosList[i]}_Head.png' />' width='30' /> ${minionsList[i]}">
        </option>
    </#list>
</#macro>

<#macro upgradeOptions upgradesList upgradesLogosList index>
    <option value="SELECT" data-content="<img src='<@spring.url '/static/images/upgrades/SELECT.png' />' width='30' /> Empty"></option>
    <#list upgradesList as upgrades>
        <option
                value="${upgradesLogosList.get(upgrades ? index)}"
                id='upgrade${index}${upgradesLogosList.get(upgrades ? index)}'
                data-content="<img src='<@spring.url '/static/images/upgrades/${upgradesLogosList.get(upgrades ? index)}.png' />' width='30' /> ${upgrades}">
        </option>
    </#list>
</#macro>

<#macro pageHeader isSigned login="">

    <header class="bg-dark" id="mainHeader">

        <button type="button" class="btn btn-primary" id="background-button-exchange">
            <img src="<@spring.url '/static/images/logos/exchange.png' />" width="25" height="25" alt="">
        </button>

        <div class="btn-group mr-2" role="group" aria-label="Second group">
            <button type="button" class="btn btn-secondary button-panel"  data-image="<@spring.url '/static/images/background/background_1.jpg' />">B</button>
            <button type="button" class="btn btn-secondary button-panel"  data-image="<@spring.url '/static/images/background/background_2.png' />">a</button>
            <button type="button" class="btn btn-secondary button-panel"  data-image="<@spring.url '/static/images/background/background_3.png' />">c</button>
            <button type="button" class="btn btn-secondary button-panel"  data-image="<@spring.url '/static/images/background/background_4.png' />">k</button>
            <button type="button" class="btn btn-secondary button-panel"  data-image="<@spring.url '/static/images/background/background_5.jpg' />">g</button>
            <button type="button" class="btn btn-secondary button-panel"  data-image="<@spring.url '/static/images/background/background_6.png' />">r</button>
            <button type="button" class="btn btn-secondary button-panel"  data-image="<@spring.url '/static/images/background/background_7.jpg' />">o</button>
            <button type="button" class="btn btn-secondary button-panel"  data-image="<@spring.url '/static/images/background/background_8.jpg' />">u</button>
            <button type="button" class="btn btn-secondary button-panel"  data-image="<@spring.url '/static/images/background/background_9.png' />">n</button>
            <button type="button" class="btn btn-secondary button-panel"  data-image="<@spring.url '/static/images/background/background_10.jpg' />">d</button>
        </div>
        <a href="<@spring.url '/main' />" class="site-name">
            <h2 class="text-light">Minions Valuation</h2>
        </a>
        <#if isSigned="true">
            <div class="dropdown">
                <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    ${login}
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <a class="dropdown-item" href="<@spring.url '/profile' />">Your account</a>
                    <a class="dropdown-item" href="<@spring.url '/log-out' />">Log out</a>
                </div>
            </div>
        </#if>
        <#if isSigned="false">
            <div class="btn-group sign-container" role="group" aria-label="Basic example">
                <a class="btn btn-info" href="<@spring.url '/sign-up' />" role="button">Sign Up</a>
                <a class="btn btn-info" href="<@spring.url '/sign-in' />" role="button">Sign In</a>
            </div>
        </#if>
    </header>
</#macro>