package oracle.sql;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.TimeZone;

public class TIMEZONETAB
{
  private static HashMap<Integer, TIMEZONETAB> instanceCache = null;
  private int instanceCount = 0;
  private int versionNumber = 0;

  private Hashtable zonetab = new Hashtable();

  private static int OFFSET_HOUR = 20;
  private static int OFFSET_MINUTE = 60;

  private static int HOUR_MILLISECOND = 3600000;

  private static int MINUTE_MILLISECOND = 60000;

  private static int BYTE_SIZE = 10;

  private TIMEZONETAB(int paramInt)
    throws SQLException
  {
    this.versionNumber = paramInt;
  }

  public static TIMEZONETAB getInstance(int paramInt)
    throws SQLException
  {
    if (instanceCache == null)
    {
      synchronized (TIMEZONETAB.class) {
        if (instanceCache == null) {
          instanceCache = new HashMap(5);
        }
      }
    }

    ??? = (TIMEZONETAB)instanceCache.get(Integer.valueOf(paramInt));
    if (??? == null)
    {
      synchronized (TIMEZONETAB.class) {
        ??? = (TIMEZONETAB)instanceCache.get(Integer.valueOf(paramInt));
        if (??? == null)
        {
          ??? = new TIMEZONETAB(paramInt);
        }
      }
    }

    return ((TIMEZONETAB)???).returnInstance();
  }

  private synchronized TIMEZONETAB returnInstance()
  {
    this.instanceCount += 1;
    instanceCache.put(Integer.valueOf(this.versionNumber), this);
    return this;
  }

  public synchronized void freeInstance()
    throws SQLException
  {
    this.instanceCount -= 1;
    if (this.instanceCount < 1)
      instanceCache.remove(Integer.valueOf(this.versionNumber));
  }

  public void addTrans(byte[] paramArrayOfByte, int paramInt)
  {
    int[] arrayOfInt = new int[BYTE_SIZE];

    int i = paramArrayOfByte[0] & 0xFF;

    OffsetDST[] arrayOfOffsetDST = new OffsetDST[i];
    int j = 0;

    for (int k = 1; k < i * BYTE_SIZE; k += BYTE_SIZE)
    {
      for (int m = 0; m < BYTE_SIZE; m++) {
        arrayOfInt[m] = (paramArrayOfByte[(m + k)] & 0xFF);
      }

      m = (arrayOfInt[0] - 100) * 100 + (arrayOfInt[1] - 100);

      Calendar localCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.US);

      localCalendar.set(1, m);
      localCalendar.set(2, arrayOfInt[2] - 1);
      localCalendar.set(5, arrayOfInt[3]);
      localCalendar.set(11, arrayOfInt[4] - 1);
      localCalendar.set(12, arrayOfInt[5] - 1);
      localCalendar.set(13, arrayOfInt[6] - 1);
      localCalendar.set(14, 0);

      long l = localCalendar.getTime().getTime();

      int n = (arrayOfInt[7] - OFFSET_HOUR) * HOUR_MILLISECOND + (arrayOfInt[8] - OFFSET_MINUTE) * MINUTE_MILLISECOND;

      byte b = (byte)arrayOfInt[9];

      arrayOfOffsetDST[(j++)] = new OffsetDST(new Timestamp(l), n, b);
    }

    this.zonetab.put(Integer.valueOf(paramInt & 0x1FF), arrayOfOffsetDST);
  }

  public byte getLocalOffset(Calendar paramCalendar, int paramInt, OffsetDST paramOffsetDST)
    throws SQLException
  {
    int k = 0;
    int m = 0;

    byte b = 0;

    Calendar localCalendar1 = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.US);

    Calendar localCalendar2 = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.US);

    Calendar localCalendar3 = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.US);

    localCalendar3.set(1, paramCalendar.get(1));
    localCalendar3.set(2, paramCalendar.get(2));
    localCalendar3.set(5, paramCalendar.get(5));
    localCalendar3.set(11, paramCalendar.get(11));
    localCalendar3.set(12, paramCalendar.get(12));
    localCalendar3.set(13, paramCalendar.get(13));
    localCalendar3.set(14, paramCalendar.get(14));

    Calendar localCalendar4 = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.US);

    localCalendar4.set(1, localCalendar3.get(1));
    localCalendar4.set(2, localCalendar3.get(2));
    localCalendar4.set(5, 1);
    localCalendar4.set(11, 0);
    localCalendar4.set(12, 0);
    localCalendar4.set(13, 0);
    localCalendar4.set(14, 0);

    OffsetDST[] arrayOfOffsetDST = (OffsetDST[])this.zonetab.get(Integer.valueOf(paramInt & 0x1FF));

    int i2 = findCloseMatch(arrayOfOffsetDST, localCalendar4.getTimeInMillis());
    int n;
    int i1;
    while (true)
    {
      localCalendar1.setTime(arrayOfOffsetDST[i2].getTimestamp());

      int i = arrayOfOffsetDST[i2].getOFFSET();

      localCalendar1.add(10, i / HOUR_MILLISECOND);

      localCalendar1.add(12, i % HOUR_MILLISECOND / MINUTE_MILLISECOND);

      n = arrayOfOffsetDST[i2].getDSTFLAG();

      if (localCalendar3.equals(localCalendar1))
      {
        paramOffsetDST.setOFFSET(arrayOfOffsetDST[i2].getOFFSET());
        paramOffsetDST.setDSTFLAG(arrayOfOffsetDST[i2].getDSTFLAG());

        b = 0;
        if (i2 <= 0) {
          break label719;
        }
        i1 = arrayOfOffsetDST[(i2 - 1)].getDSTFLAG();

        if ((n != 0) || (i1 != 1)) {
          break label719;
        }
        b = 1; break label719;
      }

      if (localCalendar3.before(localCalendar1))
      {
        if (i2 == 0)
        {
          paramOffsetDST.setOFFSET(0);
          paramOffsetDST.setDSTFLAG((byte)0);
          b = 0;

          if (n != 1)
          {
            break label719;
          }

          localCalendar1.add(10, -1);
          if (localCalendar3.before(localCalendar1))
            break label719;
          throw new SQLException("Illegal local time.");
        }

        i2 -= 1;

        if (i2 >= 0)
        {
          i1 = arrayOfOffsetDST[i2].getDSTFLAG();

          if ((n == 1) && (i1 == 0))
          {
            localCalendar1.add(10, -1);
            if (!localCalendar3.before(localCalendar1))
            {
              throw new SQLException("Illegal local time.");
            }

          }

        }

      }
      else
      {
        if (i2 == arrayOfOffsetDST.length - 1)
        {
          break;
        }

        localCalendar2.setTime(arrayOfOffsetDST[(i2 + 1)].getTimestamp());

        int j = arrayOfOffsetDST[(i2 + 1)].getOFFSET();

        localCalendar2.add(10, j / HOUR_MILLISECOND);

        localCalendar2.add(12, j % HOUR_MILLISECOND / MINUTE_MILLISECOND);

        if (localCalendar3.before(localCalendar2)) {
          break;
        }
        i2 += 1;
      }

    }

    paramOffsetDST.setOFFSET(arrayOfOffsetDST[i2].getOFFSET());
    paramOffsetDST.setDSTFLAG(arrayOfOffsetDST[i2].getDSTFLAG());

    b = 0;
    if (n == 0)
    {
      if (i2 > 0)
      {
        i1 = arrayOfOffsetDST[(i2 - 1)].getDSTFLAG();

        if (i1 == 1)
        {
          localCalendar1.add(10, 1);
          if (localCalendar3.before(localCalendar1))
          {
            b = 1;
          }
        }
      }

      if (i2 != arrayOfOffsetDST.length - 1)
      {
        i1 = arrayOfOffsetDST[(i2 + 1)].getDSTFLAG();

        if (i1 == 1)
        {
          localCalendar2.add(10, -1);
          if (!localCalendar3.before(localCalendar2))
          {
            throw new SQLException("Illegal local time.");
          }

        }

      }

    }

    label719: return b;
  }

  public int getOffset(Calendar paramCalendar, int paramInt)
    throws SQLException
  {
    OffsetDST[] arrayOfOffsetDST = (OffsetDST[])this.zonetab.get(Integer.valueOf(paramInt & 0x1FF));

    return getOffset(paramCalendar, arrayOfOffsetDST);
  }

  public int getOffset(Calendar paramCalendar, OffsetDST[] paramArrayOfOffsetDST)
    throws SQLException
  {
    int i = 0;

    Timestamp localTimestamp = new Timestamp(paramCalendar.getTime().getTime());

    int j = findCloseMatch(paramArrayOfOffsetDST, localTimestamp.getTime());

    return paramArrayOfOffsetDST[j].getOFFSET();
  }

  public boolean isDST(Calendar paramCalendar, OffsetDST[] paramArrayOfOffsetDST)
    throws SQLException
  {
    int i = 0;

    Timestamp localTimestamp = new Timestamp(paramCalendar.getTime().getTime());

    int j = findCloseMatch(paramArrayOfOffsetDST, localTimestamp.getTime());

    return paramArrayOfOffsetDST[j].getDSTFLAG() == 1;
  }

  public OffsetDST[] getOffsetDST(int paramInt)
  {
    OffsetDST[] arrayOfOffsetDST = (OffsetDST[])this.zonetab.get(Integer.valueOf(paramInt & 0x1FF));

    return arrayOfOffsetDST;
  }

  final int findCloseMatch(OffsetDST[] paramArrayOfOffsetDST, long paramLong)
  {
    int i = paramArrayOfOffsetDST.length;
    int j = 0;
    int k = i / 2;
    int m = k;

    if (paramLong < paramArrayOfOffsetDST[j].getTime())
    {
      int n = 0;

      while ((paramArrayOfOffsetDST[n].getDSTFLAG() == 1) && (n < paramArrayOfOffsetDST.length))
      {
        n++;
      }

      return n < paramArrayOfOffsetDST.length ? n : 0;
    }

    while (k > 0)
    {
      if (paramLong > paramArrayOfOffsetDST[k].getTime())
        j = k;
      else if (paramLong < paramArrayOfOffsetDST[k].getTime())
        i = k;
      else if (k == j) {
          break;
        }
      k = j + (i - j) / 2;

      if (m == k) {
        break;
      }
      m = k;
    }

    return k;
  }

  public void displayTable(int paramInt)
  {
    OffsetDST[] arrayOfOffsetDST = (OffsetDST[])this.zonetab.get(Integer.valueOf(paramInt));

    for (int i = 0; i < arrayOfOffsetDST.length; i++)
    {
      System.out.print(arrayOfOffsetDST[i].getTimestamp().toString());
      System.out.print("    " + arrayOfOffsetDST[i].getOFFSET());
      System.out.println("    " + arrayOfOffsetDST[i].getDSTFLAG());
    }
  }

  public boolean checkID(int paramInt)
  {
    return this.zonetab.get(Integer.valueOf(paramInt & 0x1FF)) == null;
  }

  public void updateTable(Connection paramConnection, int paramInt)
    throws SQLException, NullPointerException
  {
    byte[] arrayOfByte = TRANSDUMP.getTransitions(paramConnection, paramInt);

    if (arrayOfByte == null) {
      throw new NullPointerException();
    }

    addTrans(arrayOfByte, paramInt);
  }
}