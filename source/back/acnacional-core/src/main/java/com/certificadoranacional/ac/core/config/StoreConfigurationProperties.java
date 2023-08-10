package com.certificadoranacional.ac.core.config;

public class StoreConfigurationProperties {

  private StoreType type;

  private S3StoreConfigurationProperties s3;

  private FsStoreConfigurationProperties fs;

  public StoreConfigurationProperties() {
    super();
  }

  public StoreType getType() {
    return this.type;
  }

  public void setType(final StoreType type) {
    this.type = type;
  }

  public S3StoreConfigurationProperties getS3() {
    return this.s3;
  }

  public void setS3(final S3StoreConfigurationProperties s3) {
    this.s3 = s3;
  }

  public FsStoreConfigurationProperties getFs() {
    return this.fs;
  }

  public void setFs(final FsStoreConfigurationProperties fs) {
    this.fs = fs;
  }

  public static enum StoreType {
    DB, FS, S3;
  }

}
