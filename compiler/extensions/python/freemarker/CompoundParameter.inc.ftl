<#macro compound_constructor_annotated_parameters compoundParametersData indent>
    <#local I>${""?left_pad(indent * 4)}</#local>
    <#list compoundParametersData.list as parameter>
${I}<@parameter_argument_name parameter/>: ${parameter.pythonTypeName}<#rt>
        <#if parameter_has_next>
            <#lt>,
        </#if>
    </#list>
</#macro>

<#macro compound_constructor_parameters compoundParametersData>
    <#list compoundParametersData.list as parameter>
<@parameter_argument_name parameter/><#if parameter_has_next>, </#if><#rt>
    </#list>
</#macro>

<#macro compound_constructor_parameter_assignments compoundParametersData>
    <#list compoundParametersData.list as parameter>
        self.<@parameter_member_name parameter/>: ${parameter.pythonTypeName} = <@parameter_argument_name parameter/>
    </#list>
</#macro>

<#macro compound_compare_parameters compoundParametersData indent>
    <#local I>${""?left_pad(indent * 4)}</#local>
    <#list compoundParametersData.list as parameter>
self.<@parameter_member_name parameter/> == other.<@parameter_member_name parameter/><#rt>
        <#if parameter_has_next>
 and
${I}<#rt>
        </#if>
    </#list>
</#macro>

<#macro compound_hashcode_parameters compoundParametersData>
    <#list compoundParametersData.list as parameter>
        result = zserio.hashcode.calc_hashcode(result, hash(self.<@parameter_member_name parameter/>))
    </#list>
</#macro>

<#macro compound_parameter_accessor parameter>
        return self.<@parameter_member_name parameter/>
</#macro>

<#macro parameter_member_name parameter>
_${parameter.name}_<#rt>
</#macro>

<#macro parameter_argument_name parameter>
${parameter.name}_<#rt>
</#macro>
