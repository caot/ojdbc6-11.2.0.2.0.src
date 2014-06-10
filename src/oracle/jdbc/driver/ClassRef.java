package oracle.jdbc.driver;

class ClassRef
{
  private final ThreadLocal<Class> ref = new ThreadLocal();
  private final String className;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  static final ClassRef newInstance(String paramString)
    throws ClassNotFoundException
  {
    return new ClassRef(paramString);
  }

  private ClassRef(String paramString)
    throws ClassNotFoundException
  {
    this.className = paramString;

    this.ref.set(Class.forName(this.className, true, Thread.currentThread().getContextClassLoader()));
  }

  Class get()
  {
    Class localClass = (Class)this.ref.get();
    if (localClass == null) {
      try {
        localClass = Class.forName(this.className, true, Thread.currentThread().getContextClassLoader());
      }
      catch (ClassNotFoundException localClassNotFoundException) {
        NoClassDefFoundError localNoClassDefFoundError = new NoClassDefFoundError(localClassNotFoundException.getMessage());
        localNoClassDefFoundError.initCause(localClassNotFoundException);
        throw localNoClassDefFoundError;
      }
      this.ref.set(localClass);
    }
    return localClass;
  }
}