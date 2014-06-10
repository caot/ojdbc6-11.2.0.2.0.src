package oracle.jdbc.driver;

class T2CError
{
  int m_errorNumber;
  byte[] m_errorMessage = new byte[1024];
  byte[] m_sqlState = new byte[6];
}