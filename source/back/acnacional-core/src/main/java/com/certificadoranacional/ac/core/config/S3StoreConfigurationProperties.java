package com.certificadoranacional.ac.core.config;

public class S3StoreConfigurationProperties {

  private String bucket;

  private String profile;

  private String region;

  public S3StoreConfigurationProperties() {
    super();
  }

  public String getBucket() {
    return this.bucket;
  }

  public void setBucket(final String bucket) {
    this.bucket = bucket;
  }

  public String getProfile() {
    return this.profile;
  }

  public void setProfile(final String profile) {
    this.profile = profile;
  }

  public String getRegion() {
    return this.region;
  }

  public void setRegion(final String region) {
    this.region = region;
  }

}
