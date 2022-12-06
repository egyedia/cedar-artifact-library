package org.metadatacenter.artifact.model.tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.poi.ss.usermodel.Workbook;
import org.metadatacenter.artifact.model.core.TemplateSchemaArtifact;
import org.metadatacenter.artifact.model.reader.ArtifactReader;
import org.metadatacenter.artifact.ss.ArtifactSpreadsheetRenderer;
import org.metadatacenter.artifact.ss.SpreadsheetFactory;

import java.io.File;
import java.io.IOException;

public class TemplateSchemaArtifactReporter
{
  public static void main(String[] args) throws IOException
  {
    if (args.length != 2)
      Usage();

    ObjectMapper mapper = new ObjectMapper();
    File templateFile = new File(args[0]);
    File spreadsheetFile = new File(args[1]);

    JsonNode jsonNode = mapper.readTree(templateFile);

    if (!jsonNode.isObject())
      throw new RuntimeException("Expecting JSON object");

    ObjectNode templateObjectNode = (ObjectNode)jsonNode;
    ArtifactReader artifactReader = new ArtifactReader(mapper);
    TemplateSchemaArtifact templateSchemaArtifact = artifactReader.readTemplateSchemaArtifact(templateObjectNode);

    System.out.println("Template: " + templateSchemaArtifact);

    Workbook workbook = SpreadsheetFactory.createEmptyWorkbook();

    ArtifactSpreadsheetRenderer renderer = new ArtifactSpreadsheetRenderer(workbook);

    renderer.render(templateSchemaArtifact, 0, 0);

    SpreadsheetFactory.writeWorkbook(workbook, spreadsheetFile);
  }

  private static void Usage()
  {
    System.err.println("Usage: " + TemplateSchemaArtifactReporter.class.getName() + " [ <templateFileName> ] [ <spreadsheetFileName>]");
    System.exit(1);
  }
}
