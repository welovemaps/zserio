<#include "FileHeader.inc.ftl">
<@file_header generatorDescription/>

<@include_guard_begin package.path, name/>

<@system_includes headerSystemIncludes/>
<@user_includes headerUserIncludes/>
<@namespace_begin package.path/>

typedef ${targetCppTypeName} ${name};
<@namespace_end package.path/>

<@include_guard_end package.path, name/>
