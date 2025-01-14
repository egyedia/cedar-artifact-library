package org.metadatacenter.artifacts.model.core;

public enum NumberType
{
  DECIMAL("xsd:decimal"),
  INTEGER("xsd:integer"),
  LONG("xsd:long"),
  BYTE("xsd:byte"),
  SHORT("xsd:short"),
  INT("xsd:int"),
  FLOAT("xsd:float"),
  DOUBLE("xsd:double");

  private String text;

  NumberType(String text) {
    this.text = text;
  }

  public String getText() {
    return this.text;
  }

  public static NumberType fromString(String text) {
    for (NumberType n : NumberType.values()) {
      if (n.text.equalsIgnoreCase(text)) {
        return n;
      }
    }
    throw new IllegalArgumentException("No number type constant with text " + text + " found");
  }
}