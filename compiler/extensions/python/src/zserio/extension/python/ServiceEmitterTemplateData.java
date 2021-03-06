package zserio.extension.python;

import java.util.ArrayList;
import java.util.List;

import zserio.ast.CompoundType;
import zserio.ast.ServiceMethod;
import zserio.ast.ServiceType;
import zserio.extension.common.ZserioExtensionException;
import zserio.extension.python.types.PythonNativeType;

/**
 * FreeMarker template data for ServiceEmitter.
 */
public final class ServiceEmitterTemplateData extends UserTypeTemplateData
{
    public ServiceEmitterTemplateData(TemplateDataContext context, ServiceType serviceType)
            throws ZserioExtensionException
    {
        super(context, serviceType);

        importPackage("typing");
        importPackage("zserio");

        final PythonNativeMapper pythonTypeMapper = context.getPythonNativeMapper();

        final PythonNativeType nativeServiceType = pythonTypeMapper.getPythonType(serviceType);
        // keep Zserio default formatting to ensure that all languages have same name of service methods
        servicePackageName = nativeServiceType.getPackageName().toString();

        final Iterable<ServiceMethod> methodList = serviceType.getMethodList();
        for (ServiceMethod method : methodList)
        {
            final MethodTemplateData templateData = new MethodTemplateData(pythonTypeMapper, method, this);
            this.methodList.add(templateData);
        }
    }

    public String getServiceFullName()
    {
        return servicePackageName.isEmpty() ? getName() : servicePackageName + "." + getName();
    }

    public Iterable<MethodTemplateData> getMethodList()
    {
        return methodList;
    }

    public static class MethodTemplateData
    {
        public MethodTemplateData(PythonNativeMapper typeMapper, ServiceMethod serviceMethod,
                ImportCollector importCollector) throws ZserioExtensionException
        {
            name = serviceMethod.getName();
            snakeCaseName = PythonSymbolConverter.toLowerSnakeCase(name);
            clientMethodName = AccessorNameFormatter.getServiceClientMethodName(serviceMethod);

            final CompoundType responseType = serviceMethod.getResponseType();
            final PythonNativeType pythonResponseType = typeMapper.getPythonType(responseType);
            importCollector.importType(pythonResponseType);
            responseTypeInfo = new TypeInfoTemplateData(responseType, pythonResponseType);
            responseTypeFullName = PythonFullNameFormatter.getFullName(pythonResponseType);

            final CompoundType requestType = serviceMethod.getRequestType();
            final PythonNativeType pythonRequestType = typeMapper.getPythonType(requestType);
            importCollector.importType(pythonRequestType);
            requestTypeInfo = new TypeInfoTemplateData(requestType, pythonRequestType);
            requestTypeFullName = PythonFullNameFormatter.getFullName(pythonRequestType);
        }

        public String getName()
        {
            return name;
        }

        public String getSnakeCaseName()
        {
            return snakeCaseName;
        }

        public String getClientMethodName()
        {
            return clientMethodName;
        }

        public TypeInfoTemplateData getResponseTypeInfo()
        {
            return responseTypeInfo;
        }

        public String getResponseTypeFullName()
        {
            return responseTypeFullName;
        }

        public TypeInfoTemplateData getRequestTypeInfo()
        {
            return requestTypeInfo;
        }

        public String getRequestTypeFullName()
        {
            return requestTypeFullName;
        }

        private final String name;
        private final String snakeCaseName;
        private final String clientMethodName;
        private final TypeInfoTemplateData responseTypeInfo;
        private final String responseTypeFullName;
        private final TypeInfoTemplateData requestTypeInfo;
        private final String requestTypeFullName;
    }

    private final String servicePackageName;
    private final List<MethodTemplateData> methodList = new ArrayList<MethodTemplateData>();
}
