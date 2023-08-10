package com.certificadoranacional.ac.core.utils;
import com.google.common.base.CharMatcher;

public abstract class CharUtils {

  private CharUtils() {
    //
  }

  public static String digit(final String str) {
    String newStr = CharMatcher.anyOf("0123456789").retainFrom(str);
    return newStr;
  }

  public static String javaLetterOrDigit(final String str) {
    String newStr = CharMatcher.anyOf("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz").retainFrom(str);
    return newStr;
  }

  public static String javaLetterOrDigitAndWhitespace(final String str) {
    String newStr = CharMatcher.anyOf("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ").retainFrom(str);
    return newStr;
  }

}
