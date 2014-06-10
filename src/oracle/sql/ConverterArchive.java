package oracle.sql;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.StreamCorruptedException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ConverterArchive
{
  private String m_izipName;
  private FileOutputStream m_ifStream = null;
  private ZipOutputStream m_izStream = null;
  private InputStream m_riStream = null;
  private ZipFile m_rzipFile = null;
  private static final String TEMPFILE = "gsstemp.zip";
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  public void openArchiveforInsert(String paramString)
  {
    this.m_izipName = paramString;
    try
    {
      this.m_ifStream = new FileOutputStream(this.m_izipName);
      this.m_izStream = new ZipOutputStream(this.m_ifStream);
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
    }
    catch (IOException localIOException)
    {
    }
  }

  public void closeArchiveforInsert()
  {
    try {
      this.m_izStream.close();
      this.m_ifStream.close();
    }
    catch (IOException localIOException)
    {
    }
  }

  public void insertObj(Object paramObject, String paramString)
  {
    ZipEntry localZipEntry = null;
    ObjectOutputStream localObjectOutputStream = null;

    localZipEntry = new ZipEntry(paramString);
    try
    {
      this.m_izStream.putNextEntry(localZipEntry);

      localObjectOutputStream = new ObjectOutputStream(this.m_izStream);

      localObjectOutputStream.writeObject(paramObject);
      localObjectOutputStream.close();
      this.m_izStream.closeEntry();
    }
    catch (IOException localIOException)
    {
    }
  }

  public void insertSingleObj(String paramString1, Object paramObject, String paramString2)
    throws IOException
  {
    FileInputStream localFileInputStream = null;
    ZipInputStream localZipInputStream = null;
    FileOutputStream localFileOutputStream = null;
    ZipOutputStream localZipOutputStream = null;
    ZipEntry localZipEntry = null;

    ObjectInputStream localObjectInputStream = null;
    ObjectOutputStream localObjectOutputStream = null;

    File localFile1 = new File(paramString1);

    if (localFile1.isFile())
    {
      try
      {
        localFileInputStream = new FileInputStream(paramString1);
        localZipInputStream = new ZipInputStream(localFileInputStream);

        localFileOutputStream = new FileOutputStream("gsstemp.zip");
        localZipOutputStream = new ZipOutputStream(localFileOutputStream);

        while ((localZipEntry = localZipInputStream.getNextEntry()) != null)
        {
          if (!localZipEntry.getName().equals(paramString2))
          {
            localZipOutputStream.putNextEntry(localZipEntry);

            localObjectInputStream = new ObjectInputStream(localZipInputStream);
            localObjectOutputStream = new ObjectOutputStream(localZipOutputStream);
            Object localObject = localObjectInputStream.readObject();

            localObjectOutputStream.writeObject(localObject);
          }

        }

        localZipEntry = new ZipEntry(paramString2);

        localZipOutputStream.putNextEntry(localZipEntry);

        localObjectOutputStream = new ObjectOutputStream(localZipOutputStream);

        localObjectOutputStream.writeObject(paramObject);
        localObjectOutputStream.close();
        localZipInputStream.close();
      }
      catch (FileNotFoundException localFileNotFoundException1)
      {
        throw new IOException(localFileNotFoundException1.getMessage());
      }
      catch (StreamCorruptedException localStreamCorruptedException1)
      {
        throw new IOException(localStreamCorruptedException1.getMessage());
      }
      catch (IOException localIOException1)
      {
        throw localIOException1;
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        throw new IOException(localClassNotFoundException.getMessage());
      }

      File localFile2 = new File("gsstemp.zip");

      localFile1.delete();
      try
      {
        if (!localFile2.renameTo(localFile1))
          throw new IOException("can't write to target file " + paramString1);
      }
      catch (SecurityException localSecurityException)
      {
        throw new IOException(localSecurityException.getMessage());
      }
      catch (NullPointerException localNullPointerException)
      {
        throw new IOException(localNullPointerException.getMessage());
      }

    }
    else
    {
      try
      {
        localFileOutputStream = new FileOutputStream(paramString1);
        localZipOutputStream = new ZipOutputStream(localFileOutputStream);
        localZipEntry = new ZipEntry(paramString2);

        localZipOutputStream.putNextEntry(localZipEntry);

        localObjectOutputStream = new ObjectOutputStream(localZipOutputStream);

        localObjectOutputStream.writeObject(paramObject);
        localObjectOutputStream.close();
      }
      catch (FileNotFoundException localFileNotFoundException2)
      {
        throw new IOException(localFileNotFoundException2.getMessage());
      }
      catch (StreamCorruptedException localStreamCorruptedException2)
      {
        throw new IOException(localStreamCorruptedException2.getMessage());
      }
      catch (IOException localIOException2)
      {
        throw localIOException2;
      }
    }

    System.out.print(paramString2 + " has been successfully stored in ");
    System.out.println(paramString1);
  }

  public void insertObjtoFile(String paramString1, String paramString2, Object paramObject)
    throws IOException
  {
    File localFile1 = new File(paramString1);
    File localFile2 = new File(paramString1 + paramString2);

    if (!localFile1.isDirectory())
    {
      throw new IOException("directory " + paramString1 + " doesn't exist");
    }

    if (localFile2.exists())
    {
      try
      {
        localFile2.delete();
      }
      catch (SecurityException localSecurityException)
      {
        throw new IOException("file exist,can't overwrite file.");
      }
    }

    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(localFile2);
      ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(localFileOutputStream);

      localObjectOutputStream.writeObject(paramObject);
      localObjectOutputStream.close();
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      throw new IOException("file can't be created.");
    }

    System.out.print(paramString2 + " has been successfully stored in ");
    System.out.println(paramString1);
  }

  public void openArchiveforRead()
  {
    try
    {
      this.m_rzipFile = new ZipFile(this.m_izipName);
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      System.exit(0);
    }
  }

  public void closeArchiveforRead()
  {
    try
    {
      this.m_rzipFile.close();
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      System.exit(0);
    }
  }

  public Object readObj(String paramString)
  {
    URL localURL = getClass().getResource(paramString);
    Object localObject1 = null;
    ObjectInputStream localObjectInputStream = null;
    InputStream localInputStream = null;

    if (localURL == null) {
      return null;
    }
    try
    {
      localInputStream = localURL.openStream();

      localObjectInputStream = new ObjectInputStream(localInputStream);
      localObject1 = localObjectInputStream.readObject();

      return localObject1;
    }
    catch (IOException localIOException1)
    {
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
    }
    finally
    {
      try
      {
        if (localObjectInputStream != null)
          localObjectInputStream.close();
        if (localInputStream != null) {
          localInputStream.close();
        }
      }
      catch (IOException localIOException5)
      {
      }
    }
    return null;
  }

  public Object readObj(String paramString1, String paramString2)
  {
    try
    {
      FileInputStream localFileInputStream = new FileInputStream(paramString1);
      ZipInputStream localZipInputStream = new ZipInputStream(localFileInputStream);
      ZipEntry localZipEntry = null;
      ObjectInputStream localObjectInputStream = null;
      Object localObject = null;

      while (localZipInputStream.available() != 0)
      {
        localZipEntry = localZipInputStream.getNextEntry();

        if ((localZipEntry != null) && (localZipEntry.getName().equals(paramString2)))
        {
          localObjectInputStream = new ObjectInputStream(localZipInputStream);
          localObject = localObjectInputStream.readObject();
        }

      }

      localZipInputStream.close();

      return localObject;
    } catch (IOException localIOException) {
    }
    catch (ClassNotFoundException localClassNotFoundException) {
    }
    return null;
  }
}