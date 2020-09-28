<#ftl output_format="HTML">
<#include "doc_comment.inc.ftl">
<#include "compound.inc.ftl">
<#include "linkedtype.inc.ftl">
<#include "usedby.inc.ftl">
<#include "collaboration_diagram.inc.ftl">
<#macro choice_field anchorName field>
            <tr class="codeMember">
              <td id="tabIndent"></td>
              <td id="tabIndent">
                <#if field.isArrayImplicit>implicit </#if><#t>
                  <#lt><@linkedtype field.linkedType/><@compound_field_arguments field.arguments/>
              </td>
              <td>
                <a href="#${anchorName}_${field.name}" class="fieldLink">${field.name}</a><#rt>
                  ${field.arrayRange}<#t>
    <#if field.initializerExpression?has_content>
                  <#lt> = ${field.initializerExpression}<#rt>
    </#if>
    <#if field.optionalExpression?has_content>
                  <#lt> if ${field.optionalClauseExpression}<#rt>
    </#if>
    <#if field.constraintExpression?has_content>
                  <#lt> : ${field.constraintExpression}<#rt>
    </#if>
    <#if field.sqlConstraintExpression?has_content>
                  <#lt> sql ${field.sqlConstraintExpression}<#rt>
    </#if>
                  <#lt>;
              </td>
            </tr>
</#macro>

    <div class="msgdetail" id="${anchorName}">
<#if docComment.isDeprecated>
      <span class="deprecated">(deprecated) </span>
      <del>
</#if>
        <i>Choice</i> ${name}
<#if docComment.isDeprecated>
      </del>
</#if>
    </div>

    <@doc_comment docComment false/>

    <#if defaultMember??>
        <#assign rowspanNumber=((caseMemberList?size+1)*2)+1/>
    <#else>
        <#assign rowspanNumber=(caseMemberList?size*2)+1/>
    </#if>
    <table>
      <tr><td class="docuCode">
        <table>
          <tbody id="tabIndent">
            <tr>
              <td colspan=3>choice ${name}<@compound_parameters parameters/> on ${selectorExpression}</td>
            </tr>
            <tr>
              <td colspan=3>{</td>
            </tr>
<#list caseMemberList as caseMember>
            <tr>
              <td id="tabIndent"></td>
              <td colspan=2>
    <#list caseMember.caseList as case>
                <a name="${anchorName}_casedef_${case.expression}" href="#${anchorName}_case_${case.expression}" class="fieldLink">case ${case.expression}</a>:<#rt>
                  <#lt><#if case?has_next><br/></#if>
    </#list>
              </td>
            </tr>
    <#if caseMember.field??>
            <@choice_field anchorName caseMember.field/>
    <#else>
            <tr>
              <td colspan=2 id="tabIndent"></td>
              <td>;</td>
            </tr>
    </#if>
</#list>
<#if defaultMember??>
            <tr>
              <td id="tabIndent"></td>
              <td colspan=2>
                <a href="#${anchorName}_case_default" class="fieldLink">default</a>:
              </td>
            </tr>
    <#if defaultMember.field??>
            <@choice_field anchorName defaultMember.field/>
    <#else>
            <tr>
              <td colspan=2 id="tabIndent"></td>
              <td>;</td>
            </tr>
    </#if>
</#if>
<#if functions?has_content>
            <tr><td colspan=3 id="tabIndent">&nbsp;</td></tr>
            <@compound_functions functions/>
</#if>
            <tr><td colspan=3>};</td></tr>
          </tbody>
        </table>
      </td></tr>
    </table>

    <h3>Case and Member Details</h3>

    <dl>
<#list caseMemberList as caseMember>
        <dt class="memberItem">
            Case(s):
        </dt>
        <dd>
            <dl>
    <#list caseMember.caseList as case>
                <dt class="memberItem">
                    <a name="${anchorName}_case_${case.expression}">${case.expression}</a>
                </dt>
                <dd class="memberDetail">
                    <@doc_comment case.docComment/>
                    <#if case.seeLink??>
                      <div class="docuTag"><span>see: </span><a href="${case.seeLink.link}">${case.seeLink.text}</a></div>
                    </#if>
                </dd>
    </#list>
            </dl>
        </dd>
      <dt class="memberItem">
          Member:
      </dt>
      <dd>
          <dl>
  <#if caseMember.field??>
              <dt class="memberItem">
                  <a name="${anchorName}_${caseMember.field.name}">${caseMember.field.name}:</a>
              </dt>
              <dd class="memberDetail">
                  <@doc_comment caseMember.field.docComment/>
              </dd>
  <#else>
              <dt class="memberItem">
                  <a name="${anchorName}_no_field">no member data</a>
              </dt>
              <dd class="memberDetail">
                  <br />
              </dd>
  </#if>
          </dl>
      </dd>
      <dd>
        <br />
      </dd>
</#list>
<#if defaultMember??>
        <dt class="memberItem">
            Case:
        </dt>
        <dd>
            <dl>
                <dt class="memberItem">
                    <a name="${anchorName}_case_default">default</a>
                </dt>
                <dd class="memberDetail">
                    <@doc_comment defaultMember.docComment/>
                </dd>
            </dl>
        </dd>
      <dt class="memberItem">
          Member:
      </dt>
      <dd>
          <dl>
  <#if defaultMember.field??>
              <dt class="memberItem">
                  <a name="${anchorName}_${defaultMember.field.name}">${defaultMember.field.name}:</a>
              </dt>
              <dd class="memberDetail">
                  <@doc_comment defaultMember.field.docComment/>
              </dd>
  <#else>
              <dt class="memberItem">
                  <a name="${anchorName}_no_field">no member data</a>
              </dt>
              <dd class="memberDetail">
                  <br />
              </dd>
  </#if>
          </dl>
      </dd>
      <dd>
        <br />
      </dd>
</#if>
    </dl>

<@used_by usedByList/>
<#if collaborationDiagramSvgFileName??>

    <@collaboration_diagram collaborationDiagramSvgFileName/>
</#if>
