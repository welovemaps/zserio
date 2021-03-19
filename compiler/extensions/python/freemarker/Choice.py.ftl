<#include "FileHeader.inc.ftl"/>
<#include "CompoundParameter.inc.ftl">
<#include "CompoundField.inc.ftl"/>
<@file_header generatorDescription/>
<@future_annotations/>
<@all_imports packageImports symbolImports typeImports/>
<#macro choice_selector_condition expressionList>
    <#if expressionList?size == 1>
selector == (${expressionList?first})<#rt>
    <#else>
selector in (<#list expressionList as expression>${expression}<#if expression?has_next>, </#if></#list>)<#rt>
    </#if>
</#macro>
<#macro choice_if memberActionMacroName>
        selector = ${selector}
    <#list caseMemberList as caseMember>
        <#if caseMember?has_next || !isDefaultUnreachable>
        <#if caseMember?is_first>if <#else>elif </#if><@choice_selector_condition caseMember.expressionList/>:
        <#else>
        else:
        </#if>
            <@.vars[memberActionMacroName] caseMember, 3/>
    </#list>
    <#if !isDefaultUnreachable>
        else:
        <#if defaultMember??>
            <@.vars[memberActionMacroName] defaultMember, 3/>
        <#else>
            raise zserio.PythonRuntimeException("No match in choice ${name}!")
        </#if>
    </#if>
</#macro>

class ${name}:
<#assign constructorAnnotatedParamList><@compound_constructor_annotated_parameters compoundParametersData, 3/></#assign>
    def __init__(
            self<#if constructorAnnotatedParamList?has_content>,
            <#lt>${constructorAnnotatedParamList}</#if><#rt>
<#if fieldList?has_content>
            <#lt>,
            *,
    <#list fieldList as field>
            <@field_argument_name field/>: <@field_annotation_argument_type_choice_name field, name/> = None<#rt>
        <#if field?has_next>
            <#lt>,
        </#if>
    </#list>
</#if>
            <#lt>) -> None:
        <@compound_constructor_parameter_assignments compoundParametersData/>
        self._choice: typing.Any = None
<#if fieldList?has_content>
    <#list fieldList as field>
        if <@field_argument_name field/> is not None:
        <#if !field?is_first>
            if self._choice != None:
                raise zserio.PythonRuntimeException("Calling constructor of choice ${name} is ambiguous!")
        </#if>
            <@compound_setter_field field, withWriterCode, 3/>
     </#list>
</#if>

<#assign constructorParamList><@compound_constructor_parameters compoundParametersData/></#assign>
    @classmethod
    def from_reader(
            cls: typing.Type['${name}'],
            reader: zserio.BitStreamReader<#if constructorAnnotatedParamList?has_content>,
            <#lt>${constructorAnnotatedParamList}</#if>) -> '${name}':
        instance = cls(${constructorParamList})
        instance.read(reader)

        return instance

    def __eq__(self, other: object) -> bool:
        if isinstance(other, ${name}):
            return (<@compound_compare_parameters compoundParametersData, 5/> and
                    self._choice == other._choice)

        return False

    def __hash__(self) -> int:
        result = zserio.hashcode.HASH_SEED
        <@compound_hashcode_parameters compoundParametersData/>
        result = zserio.hashcode.calc_hashcode(result, hash(self._choice))

        return result
<#list compoundParametersData.list as parameter>

    @property
    def ${parameter.propertyName}(self) -> ${parameter.pythonTypeName}:
        <@compound_parameter_accessor parameter/>
</#list>
<#list fieldList as field>

    @property
    def ${field.propertyName}(self) -> <@field_annotation_argument_type_name field, name/>:
        <@compound_getter_field field/>
    <#if withWriterCode>

    @${field.propertyName}.setter
    def ${field.propertyName}(self, <#rt>
            <#lt><@field_argument_name field/>: <@field_annotation_argument_type_name field, name/>) -> None:
        <@compound_setter_field field, withWriterCode, 2/>
    </#if>
</#list>
<#list compoundFunctionsData.list as function>

    def ${function.name}(self) -> ${function.returnPythonTypeName}:
        return ${function.resultExpression}
</#list>

<#macro choice_bitsizeof_member member indent>
    <#if member.compoundField??>
        <@compound_bitsizeof_field member.compoundField, indent/>
    <#else>
        <#local I>${""?left_pad(indent * 4)}</#local>
        <#lt>${I}pass
    </#if>
</#macro>
    def bitsizeof(self, bitposition: int = 0) -> int:
<#if fieldList?has_content>
        end_bitposition = bitposition

        <@choice_if "choice_bitsizeof_member"/>

        return end_bitposition - bitposition
<#else>
        del bitposition

        return 0
</#if>
<#if withWriterCode>

    <#macro choice_initialize_offsets_member member indent>
        <#local I>${""?left_pad(indent * 4)}</#local>
        <#if member.compoundField??>
            <@compound_initialize_offsets_field member.compoundField, indent/>
        <#else>
            <#lt>${I}pass
        </#if>
    </#macro>
    def initialize_offsets(self, bitposition: int) -> int:
    <#if fieldList?has_content>
        end_bitposition = bitposition

        <@choice_if "choice_initialize_offsets_member"/>

        return end_bitposition
    <#else>
        return bitposition
    </#if>
</#if>

<#macro choice_read_member member indent>
    <#if member.compoundField??>
        <@compound_read_field member.compoundField, name, withWriterCode, indent/>
    <#else>
        <#local I>${""?left_pad(indent * 4)}</#local>
        <#lt>${I}pass
    </#if>
</#macro>
    def read(self, zserio_reader: zserio.BitStreamReader) -> None:
<#if fieldList?has_content>
        <@choice_if "choice_read_member"/>
<#else>
        del zserio_reader
</#if>
<#if withWriterCode>

    <#macro choice_write_member member indent>
        <#if member.compoundField??>
            <@compound_write_field member.compoundField, name, indent/>
        <#else>
            <#local I>${""?left_pad(indent * 4)}</#local>
            <#lt>${I}pass
        </#if>
    </#macro>
    def write(self, zserio_writer: zserio.BitStreamWriter, *,
              zserio_call_initialize_offsets: bool = True) -> None:
    <#if fieldList?has_content>
        <#if hasFieldWithOffset>
        if zserio_call_initialize_offsets:
            self.initialize_offsets(zserio_writer.bitposition)
        <#else>
        del zserio_call_initialize_offsets
        </#if>

        <@choice_if "choice_write_member"/>
    <#else>
        del zserio_writer
        del zserio_call_initialize_offsets
    </#if>
</#if>
<#list fieldList as field>
    <@define_element_creator field, name/>
</#list>
