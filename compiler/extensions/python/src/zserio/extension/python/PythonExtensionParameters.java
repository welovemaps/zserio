package zserio.extension.python;

import java.util.StringJoiner;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import zserio.extension.common.ZserioExtensionException;
import zserio.tools.ExtensionParameters;

/**
 * Command line parameters for python extension.
 *
 * The class holds all command line parameters passed by core to the python extension, which are really
 * used by python emitters.
 */
class PythonExtensionParameters
{
    public PythonExtensionParameters(ExtensionParameters parameters) throws ZserioExtensionException
    {
        outputDir = parameters.getCommandLineArg(OptionPython);
        withWriterCode = parameters.getWithWriterCode();
        withPubsubCode = parameters.getWithPubsubCode();
        withServiceCode = parameters.getWithServiceCode();
        withSqlCode = parameters.getWithSqlCode();
        withRangeCheckCode = parameters.getWithRangeCheckCode();

        final StringJoiner description = new StringJoiner(", ");
        if (withWriterCode)
            description.add("writerCode");
        if (withPubsubCode)
            description.add("pubsubCode");
        if (withServiceCode)
            description.add("serviceCode");
        if (withSqlCode)
            description.add("sqlCode");
        if (withRangeCheckCode)
            description.add("rangeCheckCode");
        parametersDescription = description.toString();
    }

    public String getOutputDir()
    {
        return outputDir;
    }

    public boolean getWithWriterCode()
    {
        return withWriterCode;
    }

    public boolean getWithPubsubCode()
    {
        return withPubsubCode;
    }

    public boolean getWithServiceCode()
    {
        return withServiceCode;
    }

    public boolean getWithSqlCode()
    {
        return withSqlCode;
    }

    public boolean getWithRangeCheckCode()
    {
        return withRangeCheckCode;
    }

    public String getParametersDescription()
    {
        return parametersDescription;
    }

    static void registerOptions(Options options)
    {
        Option optionPython = new Option(OptionPython, true, "generate Python sources");
        optionPython.setArgName("outputDir");
        optionPython.setRequired(false);
        options.addOption(optionPython);
    }

    static boolean hasOptionPython(ExtensionParameters parameters)
    {
        return parameters.argumentExists(OptionPython);
    }

    final static String OptionPython = "python";

    private final String outputDir;
    private final boolean withWriterCode;
    private final boolean withPubsubCode;
    private final boolean withServiceCode;
    private final boolean withSqlCode;
    private final boolean withRangeCheckCode;
    private final String parametersDescription;
}
