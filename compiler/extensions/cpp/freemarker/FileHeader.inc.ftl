<#macro file_header generatorDescription>
${generatorDescription}
</#macro>

<#macro camel_case_to_underscores value>
    <#t>${value?replace("(?<=[a-z0-9])[A-Z]", "_$0", "r")?upper_case}
</#macro>

<#macro include_guard_name packagePath typeName>
    <#list packagePath as namespace>
        <#t><@camel_case_to_underscores namespace/>_<#rt>
    </#list>
    <#t><@camel_case_to_underscores typeName/>_H
</#macro>

<#macro user_include packagePath typeName>
    #include <<#t>
    <#list packagePath as namespace>
        <#t>${namespace}/<#rt>
    </#list>
    <#lt>${typeName}>
</#macro>

<#macro system_includes includeFiles>
    <#list includeFiles as include>
#include <${include}>
    </#list>
</#macro>

<#macro user_includes includeFiles autoNewLine=true>
    <#if includeFiles?has_content && autoNewLine>

    </#if>
    <#list includeFiles as include>
#include <${include}>
    </#list>
</#macro>

<#macro include_guard_begin packagePath typeName>
#ifndef <@include_guard_name packagePath, typeName/>
#define <@include_guard_name packagePath, typeName/>
</#macro>

<#macro include_guard_end packagePath typeName>
#endif // <@include_guard_name packagePath, typeName/>
</#macro>

<#macro namespace_begin packagePath>
    <#if packagePath?has_content>

        <#list packagePath as namespace>
namespace ${namespace}
{
        </#list>
    </#if>
</#macro>

<#macro namespace_end packagePath>
    <#if packagePath?has_content>

        <#list packagePath?reverse as namespace>
} // namespace ${namespace}
        </#list>
    </#if>
</#macro>
