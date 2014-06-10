package oracle.net.ns;

import java.io.IOException;

public class NetException extends IOException
{
  static final boolean DEBUG = false;
  private int errorNumber;
  private String msg;
  private String userMsg;
  private Message message = null;
  static final int FIRST_ERROR = 0;
  static final int LAST_ERROR = 299;
  public static final int GOT_MINUS_ONE = 0;
  public static final int ASSERTION_FAILED = 1;
  public static final int NT_CONNECTION_FAILED = 20;
  public static final int INVALID_NT_ADAPTER = 21;
  public static final int PROTOCOL_NOT_SPECIFIED = 100;
  public static final int CSTRING_PARSING = 101;
  public static final int INVALID_CONNECT_DATA = 102;
  public static final int HOSTNAME_NOT_SPECIFIED = 103;
  public static final int PORT_NOT_SPECIFIED = 104;
  public static final int CONNECT_DATA_MISSING = 105;
  public static final int SID_INFORMATION_MISSING = 106;
  public static final int ADDRESS_NOT_DEFINED = 107;
  public static final int JNDI_THREW_EXCEPTION = 108;
  public static final int JNDI_NOT_INITIALIZED = 109;
  public static final int JNDI_CLASSES_NOT_FOUND = 110;
  public static final int USER_PROPERTIES_NOT_DEFINED = 111;
  public static final int NAMING_FACTORY_NOT_DEFINED = 112;
  public static final int NAMING_PROVIDER_NOT_DEFINED = 113;
  public static final int PROFILE_NAME_NOT_DEFINED = 114;
  public static final int HOST_PORT_SID_EXPECTED = 115;
  public static final int PORT_NUMBER_ERROR = 116;
  public static final int EZ_CONNECT_FORMAT_EXPECTED = 117;
  public static final int EZ_CONNECT_UNKNOWN_HOST = 118;
  public static final int TNS_ADMIN_EMPTY = 119;
  public static final int CONNECT_STRING_EMPTY = 120;
  public static final int INVALID_READ_PATH = 121;
  public static final int NAMELOOKUP_FAILED = 122;
  public static final int NAMELOOKUP_FILE_ERROR = 123;
  public static final int INVALID_LDAP_URL = 124;
  public static final int NOT_CONNECTED = 200;
  public static final int CONNECTED_ALREADY = 201;
  public static final int DATA_EOF = 202;
  public static final int SDU_MISMATCH = 203;
  public static final int BAD_PKT_TYPE = 204;
  public static final int UNEXPECTED_PKT = 205;
  public static final int REFUSED_CONNECT = 206;
  public static final int INVALID_PKT_LENGTH = 207;
  public static final int CONNECTION_STRING_NULL = 208;
  public static final int FAILED_TO_TURN_ENCRYPTION_ON = 300;
  public static final int WRONG_BYTES_IN_NAPACKET = 301;
  public static final int WRONG_MAGIC_NUMBER = 302;
  public static final int UNKNOWN_ALGORITHM_12649 = 303;
  public static final int INVALID_ENCRYPTION_PARAMETER = 304;
  public static final int WRONG_SERVICE_SUBPACKETS = 305;
  public static final int SUPERVISOR_STATUS_FAILURE = 306;
  public static final int AUTHENTICATION_STATUS_FAILURE = 307;
  public static final int SERVICE_CLASSES_NOT_INSTALLED = 308;
  public static final int INVALID_DRIVER = 309;
  public static final int ARRAY_HEADER_ERROR = 310;
  public static final int RECEIVED_UNEXPECTED_LENGTH_FOR_TYPE = 311;
  public static final int INVALID_NA_PACKET_TYPE_LENGTH = 312;
  public static final int INVALID_NA_PACKET_TYPE = 313;
  public static final int UNEXPECTED_NA_PACKET_TYPE_RECEIVED = 314;
  public static final int UNKNOWN_ENC_OR_DATAINT_ALGORITHM = 315;
  public static final int INVALID_ENCRYPTION_ALGORITHM_FROM_SERVER = 316;
  public static final int ENCRYPTION_CLASS_NOT_INSTALLED = 317;
  public static final int DATAINTEGRITY_CLASS_NOT_INSTALLED = 318;
  public static final int INVALID_DATAINTEGRITY_ALGORITHM_FROM_SERVER = 319;
  public static final int INVALID_SERVICES_FROM_SERVER = 320;
  public static final int INCOMPLETE_SERVICES_FROM_SERVER = 321;
  public static final int INVALID_LEVEL = 322;
  public static final int INVALID_SERVICE = 323;
  public static final int AUTHENTICATION_KERBEROS5_NO_TGT = 324;
  public static final int AUTHENTICATION_KERBEROS5_FAILURE = 325;
  public static final int AUTHENTICATION_KERBEROS5_NO_CONTEXT = 326;
  public static final int AUTHENTICATION_KERBEROS5_MUTUAL_AUTH_FAILED = 327;
  public static final int INVALID_SSL_VERSION = 400;
  public static final int UNSUPPORTED_SSL_PROTOCOL = 401;
  public static final int INVALID_SSL_CIPHER_SUITES = 403;
  public static final int UNSUPPORTED_SSL_CIPHER_SUITE = 404;
  public static final int MISMATCH_SERVER_CERT_DN = 405;
  public static final int DOUBLE_ENCRYPTION_NOT_ALLOWED = 406;
  public static final int UNABLE_TO_PARSE_WALLET_LOCATION = 407;
  public static final int UNABLE_TO_INIT_KEY_STORE = 408;
  public static final int UNABLE_TO_INIT_TRUST_STORE = 409;
  public static final int UNABLE_TO_INIT_SSL_CONTEXT = 410;
  public static final int SSL_UNVERIFIED_PEER = 411;
  public static final int UNSUPPORTED_METHOD_IN_WALLET_LOCATION = 412;
  public static final int NS_BREAK = 500;
  public static final int NL_EXCEPTION = 501;
  public static final int SO_EXCEPTION = 502;
  public static final int SO_CONNECTTIMEDOUT = 503;
  public static final int SO_READTIMEDOUT = 504;
  public static final int INVALID_CONNECTTIMEOUT = 505;
  public static final int INVALID_READTIMEOUT = 506;

  public NetException(int paramInt)
  {
    this.errorNumber = paramInt;
    this.message = new Message11();
  }

  public NetException(int paramInt, String paramString)
  {
    this(paramInt);
    this.userMsg = paramString;
  }

  public int getErrorNumber()
  {
    return this.errorNumber;
  }

  public String getMessage()
  {
    if (this.message == null) {
      return Integer.toString(this.errorNumber);
    }

    return this.message.getMessage(this.errorNumber, this.userMsg);
  }
}