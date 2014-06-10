package oracle.jdbc.connector;

import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.transaction.xa.XAResource;

public class OracleResourceAdapter
  implements ResourceAdapter
{
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public void start(BootstrapContext paramBootstrapContext)
    throws ResourceAdapterInternalException
  {
  }

  public void stop()
  {
  }

  public void endpointActivation(MessageEndpointFactory paramMessageEndpointFactory, ActivationSpec paramActivationSpec)
    throws NotSupportedException
  {
  }

  public void endpointDeactivation(MessageEndpointFactory paramMessageEndpointFactory, ActivationSpec paramActivationSpec)
  {
  }

  public XAResource[] getXAResources(ActivationSpec[] paramArrayOfActivationSpec)
    throws ResourceException
  {
    return new XAResource[0];
  }
}