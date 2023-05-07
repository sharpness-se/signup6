<#-- @ftlvariable name="group" type="se.accelerateit.signup6.model.Group" -->
<#-- @ftlvariable name="event" type="se.accelerateit.signup6.model.Event" -->
<#-- @ftlvariable name="user" type="se.accelerateit.signup6.model.User" -->
<#-- @ftlvariable name="applicationName" type="java.lang.String" -->  
<#-- @ftlvariable name="formattedEventStartDate" type="java.lang.String" -->  
<#-- @ftlvariable name="formattedEventStartTime" type="java.lang.String" -->  
<#-- @ftlvariable name="formattedEventEndTime" type="java.lang.String" -->  
<#-- @ftlvariable name="formattedLastSignUpDate" type="java.lang.String" -->  
<#-- @ftlvariable name="onHref" type="java.lang.String" -->  
<#-- @ftlvariable name="maybeHref" type="java.lang.String" -->  
<#-- @ftlvariable name="offHref" type="java.lang.String" -->  
<!DOCTYPE html>
<html lang="sv">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>${group.name} : ${event.name}</title>
</head>
<body style="font-family: arial, verdana, sans-serif; font-size: small; margin: 1em;">
<h3 style="font-family: expressway, arial, verdana, sans-serif; border-bottom: solid 3px #DB370B; margin-bottom: 0">${applicationName}</h3>
<div style="min-height: 10px; background-color: #00759F;"></div>
<h2 style="font-family: expressway, arial, verdana, sans-serif;">${group.name}</h2>
<p>Hej ${user.firstName}!</p>
<p>Nu är det dags att anmäla sig till nästa sammankomst.</p>
<div style="background-color: #EFEAE5; padding: 1em; border-radius: 0.3em;">
  <h3 style="font-family: expressway, arial, verdana, sans-serif;">${event.name}</h3>
  <p>${event.description?no_esc}</p>
  <ul>
    <li><strong>Tid: </strong> ${formattedEventStartDate}, kl ${formattedEventStartTime} - ${formattedEventEndTime}</li>
    <li><strong>Plats: </strong> ${event.venue}</li>
  </ul>
  <p>Sista anmälningsdag ${formattedLastSignUpDate}</p>
</div>
<p style="margin-top: 2em; margin-bottom: 2em">
  <a style="background-color: #5cb85c; color: white; padding: 1em; border-radius: 6px; border: solid 1px rgba(0, 0, 0, 0.0980392);font-weight: bold; text-decoration: none;"
     href="${onHref}">Kommer</a>
  <a style="background-color: #f0ad4e; color: white; padding: 1em; margin-left: 1em; border-radius: 6px; border: solid 1px rgba(0, 0, 0, 0.0980392);font-weight: bold; text-decoration: none;"
     href="${maybeHref}">Kanske</a>
  <a style="background-color: #d9534f; color: white; padding: 1em; margin-left: 1em; border-radius: 6px; border: solid 1px rgba(0, 0, 0, 0.0980392);font-weight: bold; text-decoration: none;"
     href="${offHref}">Kommer inte</a>
</p>
</body>
</html>
