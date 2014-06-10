package oracle.jdbc.driver;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import oracle.sql.BLOB;

class T4CBlobAccessor extends BlobAccessor
{
  T4CMAREngine mare;
  final int[] meta = new int[1];

  ArrayList<LinkedList<BLOB>> registeredBLOBs = new ArrayList(10);

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  T4CBlobAccessor(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean, T4CMAREngine paramT4CMAREngine)
    throws SQLException
  {
    super(paramOracleStatement, 4000, paramShort, paramInt2, paramBoolean);

    this.mare = paramT4CMAREngine;
  }

  T4CBlobAccessor(OracleStatement paramOracleStatement, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, short paramShort, int paramInt7, int paramInt8, T4CMAREngine paramT4CMAREngine)
    throws SQLException
  {
    super(paramOracleStatement, 4000, paramBoolean, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramShort);

    this.mare = paramT4CMAREngine;
    this.definedColumnType = paramInt7;
    this.definedColumnSize = paramInt8;
  }

  void processIndicator(int paramInt)
    throws IOException, SQLException
  {
    if (((this.internalType == 1) && (this.describeType == 112)) || ((this.internalType == 23) && (this.describeType == 113)))
    {
      this.mare.unmarshalUB2();
      this.mare.unmarshalUB2();
    }
    else if (this.statement.connection.versionNumber < 9200)
    {
      this.mare.unmarshalSB2();

      if ((this.statement.sqlKind != 32) && (this.statement.sqlKind != 64))
      {
        this.mare.unmarshalSB2();
      }
    } else if ((this.statement.sqlKind == 32) || (this.statement.sqlKind == 64) || (this.isDMLReturnedParam))
    {
      this.mare.processIndicator(paramInt <= 0, paramInt);
    }
  }

  boolean unmarshalOneRow()
    throws SQLException, IOException
  {
    if (this.isUseLess)
    {
      this.lastRowProcessed += 1;

      return false;
    }

    if (this.rowSpaceIndicator == null)
    {
      i = (int)this.mare.unmarshalUB4();

      if (i == 0)
      {
        this.meta[0] = -1;

        processIndicator(0);

        this.lastRowProcessed += 1;

        return false;
      }

      byte[] arrayOfByte = new byte[16000];

      this.mare.unmarshalCLR(arrayOfByte, 0, this.meta);
      processIndicator(this.meta[0]);

      this.lastRowProcessed += 1;

      return false;
    }

    int i = this.columnIndex + this.lastRowProcessed * this.byteLength;
    int j = this.indicatorIndex + this.lastRowProcessed;
    int k = this.lengthIndex + this.lastRowProcessed;

    if (this.isNullByDescribe)
    {
      this.rowSpaceIndicator[j] = -1;
      this.rowSpaceIndicator[k] = 0;
      this.lastRowProcessed += 1;

      if (this.statement.connection.versionNumber < 9200) {
        processIndicator(0);
      }
      return false;
    }

    int m = (int)this.mare.unmarshalUB4();

    if (m == 0)
    {
      this.meta[0] = -1;

      processIndicator(0);

      this.rowSpaceIndicator[j] = -1;
      this.rowSpaceIndicator[k] = 0;

      this.lastRowProcessed += 1;

      return false;
    }
    if (this.lobPrefetchSizeForThisColumn != -1)
      handlePrefetch();
    this.mare.unmarshalCLR(this.rowSpaceByte, i, this.meta, this.byteLength);

    processIndicator(this.meta[0]);

    if (this.meta[0] == 0)
    {
      this.rowSpaceIndicator[j] = -1;
      this.rowSpaceIndicator[k] = 0;
    }
    else
    {
      this.rowSpaceIndicator[k] = ((short)this.meta[0]);
      this.rowSpaceIndicator[j] = 0;
    }

    this.lastRowProcessed += 1;

    return false;
  }

  void copyRow()
    throws SQLException, IOException
  {
    int i;
    if (this.lastRowProcessed == 0)
      i = this.statement.rowPrefetchInLastFetch - 1;
    else {
      i = this.lastRowProcessed - 1;
    }

    int j = this.columnIndex + this.lastRowProcessed * this.byteLength;
    int k = this.columnIndex + i * this.byteLength;
    int m = this.indicatorIndex + this.lastRowProcessed;
    int n = this.indicatorIndex + i;
    int i1 = this.lengthIndex + this.lastRowProcessed;
    int i2 = this.lengthIndex + i;
    int i3 = this.rowSpaceIndicator[i2];
    int i4 = this.metaDataIndex + this.lastRowProcessed * 1;

    int i5 = this.metaDataIndex + i * 1;

    this.rowSpaceIndicator[i1] = ((short)i3);
    this.rowSpaceIndicator[m] = this.rowSpaceIndicator[n];

    if (!this.isNullByDescribe)
    {
      System.arraycopy(this.rowSpaceByte, k, this.rowSpaceByte, j, i3);
    }

    System.arraycopy(this.rowSpaceMetaData, i5, this.rowSpaceMetaData, i4, 1);

    this.lastRowProcessed += 1;
  }

  void saveDataFromOldDefineBuffers(byte[] paramArrayOfByte, char[] paramArrayOfChar, short[] paramArrayOfShort, int paramInt1, int paramInt2)
    throws SQLException
  {
    int i = this.columnIndex + (paramInt2 - 1) * this.byteLength;

    int j = this.columnIndexLastRow + (paramInt1 - 1) * this.byteLength;

    int k = this.indicatorIndex + paramInt2 - 1;
    int m = this.indicatorIndexLastRow + paramInt1 - 1;
    int n = this.lengthIndex + paramInt2 - 1;
    int i1 = this.lengthIndexLastRow + paramInt1 - 1;
    int i2 = paramArrayOfShort[i1];

    this.rowSpaceIndicator[n] = ((short)i2);
    this.rowSpaceIndicator[k] = paramArrayOfShort[m];

    if (i2 != 0)
    {
      System.arraycopy(paramArrayOfByte, j, this.rowSpaceByte, i, i2);
    }
  }

  byte[][] checkAndAllocateLobPrefetchMemory(byte[][] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    Object localObject = paramArrayOfByte;
    if (localObject == null)
    {
      localObject = new byte[Math.max(paramInt1, paramInt2 + 1)][];
      localObject[paramInt2] = new byte[paramInt3];
    }
    else
    {
      if (localObject.length < paramInt2 + 1)
      {
        byte[][] arrayOfByte = new byte[(paramInt2 + 1) * 2][];
        System.arraycopy(localObject, 0, arrayOfByte, 0, localObject.length);
        localObject = arrayOfByte;
      }

      if ((localObject[paramInt2] == null) || (localObject[paramInt2].length < paramInt3))
      {
        localObject[paramInt2] = new byte[paramInt3];
      }
    }
    return localObject;
  }

  char[][] checkAndAllocateLobPrefetchMemory(char[][] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3)
  {
    Object localObject = paramArrayOfChar;
    if (localObject == null)
    {
      localObject = new char[Math.max(paramInt1, paramInt2 + 1)][];
      localObject[paramInt2] = new char[paramInt3];
    }
    else
    {
      if (localObject.length < paramInt2 + 1)
      {
        char[][] arrayOfChar = new char[(paramInt2 + 1) * 2][];
        System.arraycopy(localObject, 0, arrayOfChar, 0, localObject.length);
        localObject = arrayOfChar;
      }

      if ((localObject[paramInt2] == null) || (localObject[paramInt2].length < paramInt3))
      {
        localObject[paramInt2] = new char[paramInt3];
      }
    }
    return localObject;
  }

  long[] checkAndAllocateLobPrefetchMemory(long[] paramArrayOfLong, int paramInt1, int paramInt2)
  {
    Object localObject = paramArrayOfLong;
    if (localObject == null) {
      localObject = new long[Math.max(paramInt1, paramInt2 + 1)];
    } else if (localObject.length < paramInt2 + 1)
    {
      long[] arrayOfLong = new long[(paramInt2 + 1) * 2];
      System.arraycopy(localObject, 0, arrayOfLong, 0, localObject.length);
      localObject = arrayOfLong;
    }
    return localObject;
  }

  int[] checkAndAllocateLobPrefetchMemory(int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    Object localObject = paramArrayOfInt;
    if (localObject == null) {
      localObject = new int[Math.max(paramInt1, paramInt2 + 1)];
    } else if (localObject.length < paramInt2 + 1)
    {
      int[] arrayOfInt = new int[(paramInt2 + 1) * 2];
      System.arraycopy(localObject, 0, arrayOfInt, 0, localObject.length);
      localObject = arrayOfInt;
    }
    return localObject;
  }

  short[] checkAndAllocateLobPrefetchMemory(short[] paramArrayOfShort, int paramInt1, int paramInt2)
  {
    Object localObject = paramArrayOfShort;
    if (localObject == null) {
      localObject = new short[Math.max(paramInt1, paramInt2 + 1)];
    } else if (localObject.length < paramInt2 + 1)
    {
      short[] arrayOfShort = new short[(paramInt2 + 1) * 2];
      System.arraycopy(localObject, 0, arrayOfShort, 0, localObject.length);
      localObject = arrayOfShort;
    }
    return localObject;
  }

  byte[] checkAndAllocateLobPrefetchMemory(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    Object localObject = paramArrayOfByte;
    if (localObject == null) {
      localObject = new byte[Math.max(paramInt1, paramInt2 + 1)];
    } else if (localObject.length < paramInt2 + 1)
    {
      byte[] arrayOfByte = new byte[(paramInt2 + 1) * 2];
      System.arraycopy(localObject, 0, arrayOfByte, 0, localObject.length);
      localObject = arrayOfByte;
    }
    return localObject;
  }

  boolean[] checkAndAllocateLobPrefetchMemory(boolean[] paramArrayOfBoolean, int paramInt1, int paramInt2)
  {
    Object localObject = paramArrayOfBoolean;
    if (localObject == null) {
      localObject = new boolean[Math.max(paramInt1, paramInt2 + 1)];
    } else if (localObject.length < paramInt2 + 1)
    {
      boolean[] arrayOfBoolean = new boolean[(paramInt2 + 1) * 2];
      System.arraycopy(localObject, 0, arrayOfBoolean, 0, localObject.length);
      localObject = arrayOfBoolean;
    }
    return localObject;
  }

  byte[] getBytes(int paramInt)
    throws SQLException
  {
    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] == -1) {
      return null;
    }

    if ((this.lobPrefetchSizeForThisColumn != -1) && (this.prefetchedLobSize != null))
    {
      if (this.prefetchedLobSize[paramInt] > 2147483647L)
      {
        localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 151);
        ((SQLException)localObject).fillInStackTrace();
        throw ((Throwable)localObject);
      }

      if ((this.prefetchedLobData != null) && (this.lobPrefetchSizeForThisColumn >= this.prefetchedLobSize[paramInt]))
      {
        localObject = new byte[(int)this.prefetchedLobSize[paramInt]];
        System.arraycopy(this.prefetchedLobData[paramInt], 0, localObject, 0, (int)this.prefetchedLobSize[paramInt]);
        return localObject;
      }

      Object localObject = getBLOB(paramInt);
      if (localObject == null)
        return null;
      return ((BLOB)localObject).getBytes(1L, (int)this.prefetchedLobSize[paramInt]);
    }

    return super.getBytes(paramInt);
  }

  void handlePrefetch()
    throws SQLException, IOException
  {
    this.prefetchedLobSize = checkAndAllocateLobPrefetchMemory(this.prefetchedLobSize, this.statement.rowPrefetchInLastFetch, this.lastRowProcessed);

    this.prefetchedLobSize[this.lastRowProcessed] = this.mare.unmarshalSB8();

    this.prefetchedLobChunkSize = checkAndAllocateLobPrefetchMemory(this.prefetchedLobChunkSize, this.statement.rowPrefetchInLastFetch, this.lastRowProcessed);

    this.prefetchedLobChunkSize[this.lastRowProcessed] = ((int)this.mare.unmarshalUB4());

    if (this.lobPrefetchSizeForThisColumn > 0)
    {
      this.prefetchedLobDataL = checkAndAllocateLobPrefetchMemory(this.prefetchedLobDataL, this.statement.rowPrefetchInLastFetch, this.lastRowProcessed);

      int i = this.lobPrefetchSizeForThisColumn;

      this.prefetchedLobData = checkAndAllocateLobPrefetchMemory(this.prefetchedLobData, this.statement.rowPrefetchInLastFetch, this.lastRowProcessed, i);

      disablePrefetchBufferForPreviousBLOBs(this.lastRowProcessed);
      this.mare.unmarshalCLR(this.prefetchedLobData[this.lastRowProcessed], 0, this.meta);
      this.prefetchedLobDataL[this.lastRowProcessed] = this.meta[0];
    }
  }

  void initializeBlobForPrefetch(int paramInt, BLOB paramBLOB)
    throws SQLException
  {
    if (paramInt >= 0)
    {
      saveBLOBReference(paramInt, paramBLOB);
      paramBLOB.setPrefetchedData(this.prefetchedLobData[paramInt], this.prefetchedLobDataL[paramInt]);
    }
  }

  private void saveBLOBReference(int paramInt, BLOB paramBLOB)
  {
    LinkedList localLinkedList = null;
    if (this.registeredBLOBs.size() > paramInt)
    {
      localLinkedList = (LinkedList)this.registeredBLOBs.get(paramInt);
    }
    else
    {
      localLinkedList = new LinkedList();
      while (this.registeredBLOBs.size() < paramInt)
        this.registeredBLOBs.add(new LinkedList());
      this.registeredBLOBs.add(paramInt, localLinkedList);
    }

    if (localLinkedList == null) localLinkedList = new LinkedList();
    localLinkedList.add(paramBLOB);
  }

  private void disablePrefetchBufferForPreviousBLOBs(int paramInt)
  {
    if (this.registeredBLOBs.size() > paramInt)
    {
      LinkedList localLinkedList = (LinkedList)this.registeredBLOBs.get(paramInt);
      if ((localLinkedList != null) && (!localLinkedList.isEmpty()))
      {
        ListIterator localListIterator = localLinkedList.listIterator();
        while (localListIterator.hasNext()) ((BLOB)localListIterator.next()).setPrefetchedData(null);
        localLinkedList.clear();
      }
    }
  }

  Object getObject(int paramInt)
    throws SQLException
  {
    if (this.definedColumnType == 0) {
      return super.getObject(paramInt);
    }

    Object localObject = null;
    SQLException localSQLException;
    if (this.rowSpaceIndicator == null)
    {
      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
    {
      switch (this.definedColumnType)
      {
      case 2004:
        return getBLOB(paramInt);
      }

      localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    return localObject;
  }
}