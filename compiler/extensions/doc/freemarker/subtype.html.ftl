<#include "doc_comment.inc.ftl">
<#include "linkedtype.inc.ftl">
<#include "usedby.inc.ftl">
<#include "collaboration_diagram.inc.ftl">

    <div class="msgdetail" id="${anchorName}">
<#if docComment.isDeprecated>
      <span class="deprecated">(deprecated) </span>
      <del>
</#if>
        <i>Subtype</i> ${name}
<#if docComment.isDeprecated>
      </del>
</#if>
    </div>
    <p/>
    <@doc_comment docComment/>

    <table>
    <tr><td class="docuCode">
      <table>
        <tr>
          <td colspan=3>subtype <@linkedtype linkedType/> ${name};</td>
        </tr>
      </table>
    </td></tr>
    </table>

<@used_by usedByList/>

    <h4>Const instances</h4>
    <table>
    <tr><td class="docuCode">
      <table>
      <tbody id="tabIndent">
<#assign numOfConstInstances = constInstances?size>
<#if (numOfConstInstances > 0)>
    <#list constInstances as constInstance>
        <tr><td><@linkedtype constInstance/></td></tr>
    </#list>
<#else>
        <tr><td><div class="docuTag">&lt;<i>no const values for this subtype exists.</i>&gt;</div></td></tr>
</#if>
      </tbody>
      </table>
    </td></tr>
    </table>
<#if collaborationDiagramSvgFileName??>

    <@collaboration_diagram collaborationDiagramSvgFileName/>
</#if>
