package zserio.extension.python;

import java.util.ArrayList;
import java.util.List;

import zserio.ast.CompoundType;
import zserio.ast.Function;
import zserio.ast.TypeReference;
import zserio.extension.common.ExpressionFormatter;
import zserio.extension.common.ZserioExtensionException;
import zserio.extension.python.types.PythonNativeType;

/**
 * FreeMarker template data for compound functions, used from various template data.
 */
public final class CompoundFunctionTemplateData
{
    public CompoundFunctionTemplateData(TemplateDataContext context, CompoundType compoundType,
            ImportCollector importCollector) throws ZserioExtensionException
    {
        compoundFunctionList = new ArrayList<CompoundFunction>();
        final Iterable<Function> functionList = compoundType.getFunctions();
        for (Function function : functionList)
        {
            compoundFunctionList.add(new CompoundFunction(context, function, importCollector));
        }
    }

    public Iterable<CompoundFunction> getList()
    {
        return compoundFunctionList;
    }

    public static class CompoundFunction
    {
        public CompoundFunction(TemplateDataContext context, Function function, ImportCollector importCollector)
                throws ZserioExtensionException
        {
            final TypeReference returnTypeReference = function.getReturnTypeReference();
            final PythonNativeType nativeType =
                    context.getPythonNativeMapper().getPythonType(returnTypeReference);
            importCollector.importType(nativeType);

            schemaName = function.getName();
            functionName = AccessorNameFormatter.getFunctionName(function);
            returnPythonTypeName = PythonFullNameFormatter.getFullName(nativeType);
            returnTypeInfo = new TypeInfoTemplateData(returnTypeReference, nativeType);
            final ExpressionFormatter pythonExpressionFormatter =
                    context.getPythonExpressionFormatter(importCollector);
            resultExpression = pythonExpressionFormatter.formatGetter(function.getResultExpression());
        }

        public String getSchemaName()
        {
            return schemaName;
        }

        public String getFunctionName()
        {
            return functionName;
        }

        public String getReturnPythonTypeName()
        {
            return returnPythonTypeName;
        }

        public TypeInfoTemplateData getReturnTypeInfo()
        {
            return returnTypeInfo;
        }

        public String getResultExpression()
        {
            return resultExpression;
        }

        private final String schemaName;
        private final String functionName;
        private final String returnPythonTypeName;
        private final TypeInfoTemplateData returnTypeInfo;
        private final String resultExpression;
    }

    private final List<CompoundFunction> compoundFunctionList;
}
