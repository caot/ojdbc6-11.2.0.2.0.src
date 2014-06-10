package oracle.net.ano;

public abstract interface AnoServices
{
  public static final int AUTHENTICATION = 1;
  public static final int ENCRYPTION = 2;
  public static final int DATAINTEGRITY = 3;
  public static final int SUPERVISOR = 4;
  public static final int ACCEPTED = 0;
  public static final int REJECTED = 1;
  public static final int REQUESTED = 2;
  public static final int REQUIRED = 3;
  public static final String ENCRYPTION_RC4_40 = "RC4_40";
  public static final String ENCRYPTION_RC4_56 = "RC4_56";
  public static final String ENCRYPTION_RC4_128 = "RC4_128";
  public static final String ENCRYPTION_RC4_256 = "RC4_256";
  public static final String ENCRYPTION_DES40C = "DES40C";
  public static final String ENCRYPTION_DES56C = "DES56C";
  public static final String ENCRYPTION_3DES112 = "3DES112";
  public static final String ENCRYPTION_3DES168 = "3DES168";
  public static final String ENCRYPTION_AES128 = "AES128";
  public static final String ENCRYPTION_AES192 = "AES192";
  public static final String ENCRYPTION_AES256 = "AES256";
  public static final String CHECKSUM_MD5 = "MD5";
  public static final String CHECKSUM_SHA1 = "SHA1";
  public static final String AUTHENTICATION_RADIUS = "RADIUS";
  public static final String AUTHENTICATION_KERBEROS5 = "KERBEROS5";
  public static final String AUTHENTICATION_TCPS = "TCPS";
  public static final String ANO_ACCEPTED = "ACCEPTED";
  public static final String ANO_REJECTED = "REJECTED";
  public static final String ANO_REQUESTED = "REQUESTED";
  public static final String ANO_REQUIRED = "REQUIRED";
  public static final String ENCRYPTION_PROPERTY_TYPES = "oracle.net.encryption_types_client";
  public static final String ENCRYPTION_PROPERTY_LEVEL = "oracle.net.encryption_client";
  public static final String CHECKSUM_PROPERTY_TYPES = "oracle.net.crypto_checksum_types_client";
  public static final String CHECKSUM_PROPERTY_LEVEL = "oracle.net.crypto_checksum_client";
  public static final String AUTHENTICATION_PROPERTY_SERVICES = "oracle.net.authentication_services";
  public static final String AUTHENTICATION_PROPERTY_KRB5_MUTUAL_AUTH = "oracle.net.kerberos5_mutual_authentication";
  public static final String AUTHENTICATION_PROPERTY_KRB5_CC_NAME = "oracle.net.kerberos5_cc_name";
}