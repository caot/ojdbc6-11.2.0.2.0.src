package oracle.jdbc;

import java.security.BasicPermission;

public final class OracleSQLPermission extends BasicPermission
{
  private static final String[] allowedTargets = { "callAbort" };

  private final void checkTarget(String paramString) {
    for (int i = 0; i < allowedTargets.length; i++) {
      if (paramString.equals(allowedTargets[i])) return;
    }
    throw new IllegalArgumentException(paramString);
  }

  public OracleSQLPermission(String paramString) {
    super(paramString);
    checkTarget(paramString);
  }

  public OracleSQLPermission(String paramString1, String paramString2) {
    super(paramString1, paramString2);
    checkTarget(paramString1);
  }
}