package com.vertex.quality.common.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.Map;

/**
 * A template editor object to use with free marker templates
 *
 * @author hho
 */
public class TemplateEditor
{
	private Configuration configuration;

	/**
	 * Sets up the configuration
	 *
	 * @param templateDirectory the base directory for the templates
	 */
	public TemplateEditor( Path templateDirectory )
	{
		try
		{
			String dir = templateDirectory.toString();
			configuration = new Configuration(Configuration.VERSION_2_3_28);
			configuration.setDirectoryForTemplateLoading(new File(dir));
			configuration.setDefaultEncoding("UTF-8");
		}
		catch ( Exception e )
		{
			VertexLogger.log(e.toString());
		}
	}

	/**
	 * Processes the template, replacing variables in the template with the data in the map
	 *
	 * @param templateName the template's name
	 * @param templateData the template data
	 *
	 * @return the file contents of the processed template as a string
	 */
	public String processTemplate( String templateName, Map templateData )
	{
		String editedTemplateString = null;
		try ( StringWriter sw = new StringWriter() )
		{
			Template template = configuration.getTemplate(templateName);

			template.process(templateData, sw);
			editedTemplateString = sw.toString();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		return editedTemplateString;
	}
}
