<#-- $This file is distributed under the terms of the license in /doc/license.txt$ -->

<#import "lib-vivo-properties.ftl" as p>

<a href="${individual.profileUrl}" title="individual name">${individual.name}</a>

<@p.displayTitle individual />

<#if (details[0].deptName)?? >
    <span class="display-title">Member of ${details[0].deptName}</span>
</#if>

