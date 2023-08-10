package com.certificadoranacional.ac.jpa.util;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitForeignKeyNameSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyComponentPathImpl;
import org.hibernate.boot.model.naming.ImplicitUniqueKeyNameSource;

import com.certificadoranacional.ac.core.Constants;

public class CustomNamingStrategy extends ImplicitNamingStrategyComponentPathImpl {

  private static final long serialVersionUID = Constants.VERSION;

  private Map<String, Integer> map = new HashMap<>();

  public CustomNamingStrategy() {
    super();
  }

  @Override
  public Identifier determineForeignKeyName(final ImplicitForeignKeyNameSource source) {
    String from = this.getTableName(source.getTableName());
    String to = this.getTableName(source.getReferencedTableName());
    String name = this.getQualified("fk_" + from + "_" + to);
    return toIdentifier(name, source.getBuildingContext());
  }

  @Override
  public Identifier determineUniqueKeyName(ImplicitUniqueKeyNameSource source) {
    String from = this.getTableName(source.getTableName());
    String name = this.getQualified("uk_" + from);
    return toIdentifier(name, source.getBuildingContext());
  }

  private String getQualified(final String name) {
    int i = 1;
    if (this.map.containsKey(name)) {
      Integer tmp = this.map.get(name);
      i = tmp.intValue() + 1;
    }
    String str = name + "_" + i;
    this.map.put(name, Integer.valueOf(i));
    return str;
  }

  private String getTableName(final Identifier table) {
    String name = table.getCanonicalName();
    if (name.startsWith("tb_")) {
      name = name.substring(3);
    }
    return name;
  }
}
