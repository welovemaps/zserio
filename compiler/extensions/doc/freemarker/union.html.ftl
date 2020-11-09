<#ftl output_format="HTML">
<#include "doc_comment.inc.ftl">
<#include "compound.inc.ftl">
<#include "code.inc.ftl">
<#include "usedby.inc.ftl">
<#include "svg_diagram.inc.ftl">
<#assign indent = 5>
<#assign I>${""?left_pad(indent * 2)}</#assign>
<#assign unionHeading>
    <i>Union</i><#if templateParameters?has_content> template</#if> ${symbol.name}<#t>
</#assign>

${I}<h2 class="anchor" id="${symbol.htmlLink.htmlAnchor}">
<#if docComments.isDeprecated>
${I}  <span class="deprecated">(deprecated) </span>
${I}  <del>${unionHeading}</del>
<#else>
${I}  ${unionHeading}
</#if>
${I}</h2>
    <@doc_comments docComments, indent, false/>

    <@code_table_begin indent/>
${I}  <tr><td colspan=3>union ${symbol.name}<@compound_template_parameters templateParameters/><#rt>
        <#lt><@compound_parameters parameters/></td></tr>
${I}  <tr><td colspan=3>{</td></tr>
      <@compound_fields fields, indent+1/>
<#if functions?has_content>
${I}  <tr><td colspan=3>&nbsp;</td></tr>
      <@compound_functions functions, indent+1/>
</#if>
${I}  <tr><td colspan=3>};</td></tr>
    <@code_table_end indent/>
    <@compound_member_details fields, indent/>
    <@compound_function_details functions, indent/>
    <@used_by usedByList, indent/>
<#if collaborationDiagramSvg??>

    <@collaboration_diagram collaborationDiagramSvg, indent/>
</#if>
