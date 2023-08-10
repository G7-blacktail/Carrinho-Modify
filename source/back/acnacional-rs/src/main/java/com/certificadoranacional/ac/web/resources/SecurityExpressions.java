package com.certificadoranacional.ac.web.resources;

public abstract class SecurityExpressions {

  public static final String IS_AUTHENTICATED = "isAuthenticated()";

  public static final String ROLE_ADMIN = "hasRole('ADMIN')";

  public static final String ROLE_ADMIN_OR_GERENTE = "hasAnyRole('ADMIN', 'GERENTE')";

  private SecurityExpressions() {
    //
  }

}
