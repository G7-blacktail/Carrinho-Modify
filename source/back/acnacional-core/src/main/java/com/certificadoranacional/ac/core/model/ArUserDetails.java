package com.certificadoranacional.ac.core.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.certificadoranacional.ac.core.Constants;

public class ArUserDetails implements UserDetails {

  private static final long serialVersionUID = Constants.VERSION;

  private String id;

  private String username;

  private Collection<? extends GrantedAuthority> authorities;

  public ArUserDetails(final String id, final String username, Collection<? extends GrantedAuthority> authorities) {
    super();
    this.id = id;
    this.username = username;
    this.authorities = authorities;
  }

  // Spring
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  public String getId() {
    return this.id;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public String getPassword() {
    return "";
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  // Aux
  public boolean isUserInRole(final String role) {
    if (this.authorities != null) {
      for (GrantedAuthority authority : this.authorities) {
        if (authority.getAuthority().equalsIgnoreCase(role)) {
          return true;
        }
      }
    }
    return false;
  }

}
