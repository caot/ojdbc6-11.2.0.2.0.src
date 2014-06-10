package oracle.net.ns;

import java.io.Serializable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Message11
  implements Message, Serializable
{
  private static final boolean DEBUG = false;
  private String msg;
  private transient ResourceBundle rBundle;
  private static final String messageFile = "oracle.net.mesg.Message";

  public String getMessage(int paramInt, String paramString)
  {
    try
    {
      this.rBundle = ResourceBundle.getBundle("oracle.net.mesg.Message");
    } catch (Exception localException) {
      return "Message file 'oracle.net.mesg.Message' is missing.";
    }

    try
    {
      this.msg = number2string(paramInt, paramString);
    } catch (MissingResourceException localMissingResourceException) {
      this.msg = "Undefined Error";
    }

    return this.msg;
  }

  private String number2string(int paramInt, String paramString)
    throws MissingResourceException
  {
    String str1 = null;

    String str2 = " " + paramString;

    if (paramInt > 12000)
    {
      if ((paramInt >= 12500) && (paramInt <= 12530)) {
        str1 = this.rBundle.getString("LISTENER_REFUSES_CONNECTION") + ":\n" + "ORA-" + paramInt + ", " + this.rBundle.getString(String.valueOf(paramInt)) + "\n" + str2;
      }
      else
      {
        str1 = this.rBundle.getString("ORACLE_ERROR") + " ORA-" + paramInt;
      }
      return str1;
    }

    switch (paramInt)
    {
    case 0:
      str1 = this.rBundle.getString("GOT_MINUS_ONE") + str2;
      break;
    case 1:
      str1 = this.rBundle.getString("ASSERTION_FAILED") + str2;
      break;
    case 20:
      str1 = this.rBundle.getString("NT_CONNECTION_FAILED") + str2;
      break;
    case 21:
      str1 = this.rBundle.getString("INVALID_NT_ADAPTER") + str2;
      break;
    case 100:
      str1 = this.rBundle.getString("PROTOCOL_NOT_SPECIFIED") + str2;
      break;
    case 101:
      str1 = this.rBundle.getString("CSTRING_PARSING") + str2;
      break;
    case 102:
      str1 = this.rBundle.getString("INVALID_CONNECT_DATA") + str2;
      break;
    case 103:
      str1 = this.rBundle.getString("HOSTNAME_NOT_SPECIFIED") + str2;
      break;
    case 104:
      str1 = this.rBundle.getString("PORT_NOT_SPECIFIED") + str2;
      break;
    case 105:
      str1 = this.rBundle.getString("CONNECT_DATA_MISSING") + str2;
      break;
    case 106:
      str1 = this.rBundle.getString("SID_INFORMATION_MISSING") + str2;
      break;
    case 107:
      str1 = this.rBundle.getString("ADDRESS_NOT_DEFINED") + str2;
      break;
    case 108:
      str1 = this.rBundle.getString("JNDI_THREW_EXCEPTION") + str2;
      break;
    case 109:
      str1 = this.rBundle.getString("JNDI_NOT_INITIALIZED") + str2;
      break;
    case 110:
      str1 = this.rBundle.getString("JNDI_CLASSES_NOT_FOUND") + str2;
      break;
    case 111:
      str1 = this.rBundle.getString("USER_PROPERTIES_NOT_DEFINED") + str2;
      break;
    case 112:
      str1 = this.rBundle.getString("NAMING_FACTORY_NOT_DEFINED") + str2;
      break;
    case 113:
      str1 = this.rBundle.getString("NAMING_PROVIDER_NOT_DEFINED") + str2;
      break;
    case 114:
      str1 = this.rBundle.getString("PROFILE_NAME_NOT_DEFINED") + str2;
      break;
    case 115:
      str1 = this.rBundle.getString("HOST_PORT_SID_EXPECTED") + str2;
      break;
    case 116:
      str1 = this.rBundle.getString("PORT_NUMBER_ERROR") + str2;
      break;
    case 117:
      str1 = this.rBundle.getString("EZ_CONNECT_FORMAT_EXPECTED") + str2;
      break;
    case 118:
      str1 = this.rBundle.getString("EZ_CONNECT_UNKNOWN_HOST") + str2;
      break;
    case 121:
      str1 = this.rBundle.getString("INVALID_READ_PATH") + str2;
      break;
    case 119:
      str1 = this.rBundle.getString("TNS_ADMIN_EMPTY") + str2;
      break;
    case 120:
      str1 = this.rBundle.getString("CONNECT_STRING_EMPTY") + str2;
      break;
    case 122:
      str1 = this.rBundle.getString("NAMELOOKUP_FAILED") + str2;
      break;
    case 123:
      str1 = this.rBundle.getString("NAMELOOKUP_FILE_ERROR") + str2;
      break;
    case 124:
      str1 = this.rBundle.getString("INVALID_LDAP_URL") + str2;
      break;
    case 200:
      str1 = this.rBundle.getString("NOT_CONNECTED") + str2;
      break;
    case 201:
      str1 = this.rBundle.getString("CONNECTED_ALREADY") + str2;
      break;
    case 202:
      str1 = this.rBundle.getString("DATA_EOF") + str2;
      break;
    case 203:
      str1 = this.rBundle.getString("SDU_MISMATCH") + str2;
      break;
    case 204:
      str1 = this.rBundle.getString("BAD_PKT_TYPE") + str2;
      break;
    case 205:
      str1 = this.rBundle.getString("UNEXPECTED_PKT") + str2;
      break;
    case 206:
      str1 = this.rBundle.getString("REFUSED_CONNECT") + str2;
      break;
    case 207:
      str1 = this.rBundle.getString("INVALID_PKT_LENGTH") + str2;
      break;
    case 208:
      str1 = this.rBundle.getString("CONNECTION_STRING_NULL") + str2;
      break;
    case 300:
      str1 = this.rBundle.getString("FAILED_TO_TURN_ENCRYPTION_ON") + str2;
      break;
    case 301:
      str1 = this.rBundle.getString("WRONG_BYTES_IN_NAPACKET") + str2;
      break;
    case 302:
      str1 = this.rBundle.getString("WRONG_MAGIC_NUMBER") + str2;
      break;
    case 303:
      str1 = this.rBundle.getString("UNKNOWN_ALGORITHM_12649") + str2;
      break;
    case 304:
      str1 = this.rBundle.getString("INVALID_ENCRYPTION_PARAMETER") + str2;
      break;
    case 305:
      str1 = this.rBundle.getString("WRONG_SERVICE_SUBPACKETS") + str2;
      break;
    case 306:
      str1 = this.rBundle.getString("SUPERVISOR_STATUS_FAILURE") + str2;
      break;
    case 307:
      str1 = this.rBundle.getString("AUTHENTICATION_STATUS_FAILURE") + str2;
      break;
    case 308:
      str1 = this.rBundle.getString("SERVICE_CLASSES_NOT_INSTALLED") + str2;
      break;
    case 309:
      str1 = this.rBundle.getString("INVALID_DRIVER") + str2;
      break;
    case 310:
      str1 = this.rBundle.getString("ARRAY_HEADER_ERROR") + str2;
      break;
    case 311:
      str1 = this.rBundle.getString("RECEIVED_UNEXPECTED_LENGTH_FOR_TYPE") + str2;
      break;
    case 312:
      str1 = this.rBundle.getString("INVALID_NA_PACKET_TYPE_LENGTH") + str2;
      break;
    case 313:
      str1 = this.rBundle.getString("INVALID_NA_PACKET_TYPE") + str2;
      break;
    case 314:
      str1 = this.rBundle.getString("UNEXPECTED_NA_PACKET_TYPE_RECEIVED") + str2;
      break;
    case 315:
      str1 = this.rBundle.getString("UNKNOWN_ENC_OR_DATAINT_ALGORITHM") + str2;
      break;
    case 316:
      str1 = this.rBundle.getString("INVALID_ENCRYPTION_ALGORITHM_FROM_SERVER") + str2;
      break;
    case 317:
      str1 = this.rBundle.getString("ENCRYPTION_CLASS_NOT_INSTALLED") + str2;
      break;
    case 318:
      str1 = this.rBundle.getString("DATAINTEGRITY_CLASS_NOT_INSTALLED") + str2;
      break;
    case 319:
      str1 = this.rBundle.getString("INVALID_DATAINTEGRITY_ALGORITHM_FROM_SERVER") + str2;
      break;
    case 320:
      str1 = this.rBundle.getString("INVALID_SERVICES_FROM_SERVER") + str2;
      break;
    case 321:
      str1 = this.rBundle.getString("INCOMPLETE_SERVICES_FROM_SERVER") + str2;
      break;
    case 322:
      str1 = this.rBundle.getString("INVALID_LEVEL") + str2;
      break;
    case 323:
      str1 = this.rBundle.getString("INVALID_SERVICE") + str2;
      break;
    case 324:
      str1 = this.rBundle.getString("AUTHENTICATION_KERBEROS5_NO_TGT") + str2;
      break;
    case 325:
      str1 = this.rBundle.getString("AUTHENTICATION_KERBEROS5_FAILURE") + str2;
      break;
    case 326:
      str1 = this.rBundle.getString("AUTHENTICATION_KERBEROS5_NO_CONTEXT") + str2;
      break;
    case 327:
      str1 = this.rBundle.getString("AUTHENTICATION_KERBEROS5_MUTUAL_AUTH_FAILED") + str2;
      break;
    case 400:
      str1 = this.rBundle.getString("INVALID_SSL_VERSION") + str2;
      break;
    case 401:
      str1 = this.rBundle.getString("UNSUPPORTED_SSL_PROTOCOL") + str2;
      break;
    case 403:
      str1 = this.rBundle.getString("INVALID_SSL_CIPHER_SUITES") + str2;
      break;
    case 404:
      str1 = this.rBundle.getString("UNSUPPORTED_CIPHER_SUITE") + str2;
      break;
    case 405:
      str1 = this.rBundle.getString("MISMATCH_SERVER_CERT_DN") + str2;
      break;
    case 406:
      str1 = this.rBundle.getString("DOUBLE_ENCRYPTION_NOT_ALLOWED") + str2;
      break;
    case 407:
      str1 = this.rBundle.getString("UNABLE_TO_PARSE_WALLET_LOCATION") + str2;
      break;
    case 408:
      str1 = this.rBundle.getString("UNABLE_TO_INIT_KEY_STORE") + str2;
      break;
    case 409:
      str1 = this.rBundle.getString("UNABLE_TO_INIT_TRUST_STORE") + str2;
      break;
    case 410:
      str1 = this.rBundle.getString("UNABLE_TO_INIT_SSL_CONTEXT") + str2;
      break;
    case 411:
      str1 = this.rBundle.getString("SSL_UNVERIFIED_PEER") + str2;
      break;
    case 412:
      str1 = this.rBundle.getString("UNSUPPORTED_METHOD_IN_WALLET_LOCATION") + str2;

      break;
    case 500:
      str1 = this.rBundle.getString("NS_BREAK") + str2;
      break;
    case 501:
      str1 = this.rBundle.getString("NL_EXCEPTION") + str2;
      break;
    case 502:
      str1 = this.rBundle.getString("SO_EXCEPTION") + str2;
      break;
    case 503:
      str1 = this.rBundle.getString("SO_CONNECTTIMEDOUT") + str2;
      break;
    case 504:
      str1 = this.rBundle.getString("SO_READTIMEDOUT") + str2;
      break;
    case 505:
      str1 = this.rBundle.getString("INVALID_CONNECTTIMEOUT") + str2;
      break;
    case 506:
      str1 = this.rBundle.getString("INVALID_READTIMEOUT") + str2;
      break;
    default:
      str1 = this.rBundle.getString("UNDEFINED_ERROR") + str2;
    }

    return str1;
  }
}