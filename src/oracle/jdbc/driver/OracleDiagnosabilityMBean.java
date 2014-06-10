package oracle.jdbc.driver;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanInfo;
import javax.management.StandardMBean;

public class OracleDiagnosabilityMBean extends StandardMBean
  implements DiagnosabilityMXBean
{
  OracleDiagnosabilityMBean()
  {
    super(DiagnosabilityMXBean.class, true);
  }

  public boolean getLoggingEnabled()
  {
    return OracleLog.isEnabled();
  }

  public void setLoggingEnabled(boolean paramBoolean)
  {
    OracleLog.setTrace(paramBoolean);
  }

  public boolean stateManageable()
  {
    return false;
  }

  public boolean statisticsProvider()
  {
    return false;
  }

  protected String getDescription(MBeanInfo paramMBeanInfo)
  {
    return DatabaseError.findMessage("DiagnosabilityMBeanDescription", this);
  }

  protected String getDescription(MBeanConstructorInfo paramMBeanConstructorInfo)
  {
    return DatabaseError.findMessage("DiagnosabilityMBeanConstructor()", this);
  }

  protected String getDescription(MBeanAttributeInfo paramMBeanAttributeInfo)
  {
    String str = paramMBeanAttributeInfo.getName();
    if (str.equals("LoggingEnabled")) {
      return DatabaseError.findMessage("DiagnosabilityMBeanLoggingEnabledDescription", this);
    }
    if (str.equals("stateManageable")) {
      return DatabaseError.findMessage("DiagnosabilityMBeanStateManageableDescription", this);
    }
    if (str.equals("statisticsProvider")) {
      return DatabaseError.findMessage("DiagnosabilityMBeanStatisticsProviderDescription", this);
    }

    Logger.getLogger("oracle.jdbc.driver").log(Level.SEVERE, "Got a request to describe an unexpected  Attribute: " + str);

    return super.getDescription(paramMBeanAttributeInfo);
  }
}