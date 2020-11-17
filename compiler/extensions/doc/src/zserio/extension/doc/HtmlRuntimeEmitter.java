package zserio.extension.doc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import zserio.extension.common.ZserioExtensionException;

/**
 * Emits external files stored in Jar file used by generated HTML during runtime.
 */
class HtmlRuntimeEmitter
{
    static void emit(DocExtensionParameters docParameters) throws ZserioExtensionException
    {
        try
        {
            final String[] jarRuntimeSubdirs = {
                    DocDirectories.CSS_DIRECTORY,
                    DocDirectories.JS_DIRECTORY,
                    DocDirectories.RESOURCES_DIRECTORY};
            for (String jarRuntimeSubdir : jarRuntimeSubdirs)
            {
                final List<String> jarResources = getJarRuntimeResources(jarRuntimeSubdir);
                for (String jarResource : jarResources)
                    extractJarResource(jarResource, docParameters.getOutputDir(), jarRuntimeSubdir);
            }
        }
        catch (IOException exception)
        {
            throw new ZserioExtensionException(exception.getMessage());
        }
        catch (URISyntaxException exception)
        {
            throw new ZserioExtensionException(exception.getMessage());
        }
    }

    private static List<String> getJarRuntimeResources(String jarRuntimeSubdir)
            throws IOException, URISyntaxException
    {
        final List<String> availableResources = new ArrayList<String>();
        final String resourceDir = JAR_RUNTIME_DIR + "/" + jarRuntimeSubdir;
        final URL jarUrl = HtmlRuntimeEmitter.class.getProtectionDomain().getCodeSource().getLocation();
        final JarFile jarFile = new JarFile(jarUrl.toURI().getPath());
        final Enumeration<JarEntry> jarEntries = jarFile.entries();
        while (jarEntries.hasMoreElements())
        {
            final JarEntry jarEntry = jarEntries.nextElement();

            if (!jarEntry.isDirectory())
            {
                final String jarEntryName = jarEntry.getName();
                if (jarEntryName.startsWith(resourceDir))
                    availableResources.add(jarEntryName);
            }
        }

        jarFile.close();

        return availableResources;
    }

    private static void extractJarResource(String jarResource, String outputDir, String outputSubDir)
            throws IOException
    {
        FileOutputStream writer = null;
        final InputStream reader = HtmlRuntimeEmitter.class.getResourceAsStream("/" + jarResource);
        try
        {
            if (reader != null)
            {
                final File outputFullDir = new File(outputDir, outputSubDir);
                if (!outputFullDir.exists() && !outputFullDir.mkdirs())
                    throw new IOException("Directory " + outputFullDir.toString() + " cannot be created!");

                final File outputFile = new File(outputFullDir, getResourceFileName(jarResource));
                writer = new FileOutputStream(outputFile);
                final byte[] buffer = new byte[16384];
                int bytesRead = 0;
                while ((bytesRead = reader.read(buffer)) != -1)
                    writer.write(buffer, 0, bytesRead);
            }
        }
        finally
        {
            try
            {
                if (writer != null)
                    writer.close();
            }
            finally
            {
                if (reader != null)
                    reader.close();
            }
        }
    }

    private static String getResourceFileName(String jarResource)
    {
        final int lastSeparatorIndex = jarResource.lastIndexOf('/');

        return (lastSeparatorIndex == -1) ? jarResource : jarResource.substring(lastSeparatorIndex);
    }

    private static String JAR_RUNTIME_DIR = "zserio/extension/doc/runtime";
}