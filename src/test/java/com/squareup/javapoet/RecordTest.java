package com.squareup.javapoet;

import static com.google.common.truth.Truth.assertThat;

import java.util.List;

import org.junit.Test;

public class RecordTest {

  @Test
  public void singleFieldRecord() {
    TypeSpec spec = TypeSpec.recordBuilder("Person")
        .addRecordComponent(TypeName.get(String.class), "name").build();
    JavaFile file = JavaFile.builder("example.person", spec).build();

    assertThat(file.toString()).isEqualTo(""
        + "package example.person;\n"
        + "\n"
        + "import java.lang.String;\n"
        + "\n"
        + "record Person(String name) {\n"
        + "}\n");
  }

  @Test
  public void emptyRecord() {
    TypeSpec spec = TypeSpec.recordBuilder("Person").build();
    JavaFile file = JavaFile.builder("example.person", spec).build();

    assertThat(file.toString()).isEqualTo(""
        + "package example.person;\n"
        + "\n"
        + "record Person() {\n"
        + "}\n");
  }

  @Test
  public void multipleFieldsRecord() {
    TypeSpec spec = TypeSpec.recordBuilder("Person")
        .addRecordComponent(TypeName.get(String.class), "firstName")
        .addRecordComponent(TypeName.get(String.class), "lastName")
        .addRecordComponent(ParameterizedTypeName.get(List.class, String.class), "aliases")
        .build();
    JavaFile file = JavaFile.builder("example.person", spec).build();
    assertThat(file.toString()).isEqualTo(""
        + "package example.person;\n"
        + "\n"
        + "import java.lang.String;\n"
        + "import java.util.List;\n"
        + "\n"
        + "record Person(String firstName, String lastName, List<String> aliases) {\n"
        + "}\n");
  }

  @Test
  public void fieldsWithJavaDocs() {
    TypeSpec spec = TypeSpec.recordBuilder("Person")
        .addRecordComponent(ParameterSpec.builder(TypeName.get(String.class), "firstName")
            .addJavadoc("the first name\n").build())
        .addRecordComponent(TypeName.get(String.class), "lastName")
        .addRecordComponent(ParameterSpec.builder(TypeName.INT, "age")
            .addJavadoc("the age\n").build())
        .build();
    JavaFile file = JavaFile.builder("example.person", spec).build();

    assertThat(file.toString()).isEqualTo(""
        + "package example.person;\n"
        + "\n"
        + "import java.lang.String;\n"
        + "\n"
        + "/**\n"
        + " * @param firstName the first name\n"
        + " * @param age the age\n"
        + " */\n"
        + "record Person(String firstName, String lastName, int age) {\n"
        + "}\n");
  }

  @Test
  public void fieldsWithJavaDocsAndClassDoc() {
    TypeSpec spec = TypeSpec.recordBuilder("Person")
        .addJavadoc("This is my record\n\nIt does things\n")//
        .addRecordComponent(ParameterSpec.builder(TypeName.get(String.class), "firstName")
            .addJavadoc("the first name\n").build())
        .addRecordComponent(TypeName.get(String.class), "lastName")
        .addRecordComponent(ParameterSpec.builder(TypeName.INT, "age")
            .addJavadoc("the age\n").build())
        .build();
    JavaFile file = JavaFile.builder("example.person", spec).build();

    assertThat(file.toString()).isEqualTo(""
        + "package example.person;\n"
        + "\n"
        + "import java.lang.String;\n"
        + "\n"
        + "/**\n"
        + " * This is my record\n"
        + " *\n"
        + " * It does things\n"
        + " *\n"
        + " * @param firstName the first name\n"
        + " * @param age the age\n"
        + " */\n"
        + "record Person(String firstName, String lastName, int age) {\n"
        + "}\n");
  }

  @Test
  public void singleFieldRecordVarArgs() {
    TypeSpec spec = TypeSpec.recordBuilder("Person")
        .addRecordComponent(TypeName.get(String[].class), "names")
        .recordVarargs()
        .build();
    JavaFile file = JavaFile.builder("example.person", spec).build();

    assertThat(file.toString()).isEqualTo(""
        + "package example.person;\n"
        + "\n"
        + "import java.lang.String;\n"
        + "\n"
        + "record Person(String... names) {\n"
        + "}\n");
  }

  @Test
  public void mutlipeFieldsVarArgs() {
    TypeSpec spec = TypeSpec.recordBuilder("Person")
        .addRecordComponent(TypeName.get(String.class), "name")
        .addRecordComponent(TypeName.get(String[].class), "aliases")
        .recordVarargs()
        .build();
    JavaFile file = JavaFile.builder("example.person", spec).build();

    assertThat(file.toString()).isEqualTo(""
        + "package example.person;\n"
        + "\n"
        + "import java.lang.String;\n"
        + "\n"
        + "record Person(String name, String... aliases) {\n"
        + "}\n");
  }
}
