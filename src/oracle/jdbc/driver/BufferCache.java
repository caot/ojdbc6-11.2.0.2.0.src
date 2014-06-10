package oracle.jdbc.driver;

import java.lang.ref.SoftReference;
import java.lang.reflect.Array;
import oracle.jdbc.internal.OracleConnection.BufferCacheStatistics;

class BufferCache<T>
{
  private static final double ln2 = Math.log(2.0D);
  private static final int BUFFERS_PER_BUCKET = 8;
  private static final int MIN_INDEX = 12;
  private final InternalStatistics stats;
  private final int[] bufferSize;
  private final SoftReference<T>[][] buckets;
  private final int[] top;
  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  BufferCache(int paramInt)
  {
    int i;
    if (paramInt < 31) {
      i = paramInt;
    }
    else
    {
      i = (int)Math.ceil(Math.log(paramInt) / ln2);
    }

    int j = Math.max(0, i - 12 + 1);

    this.buckets = ((SoftReference[][])new SoftReference[j][8]);
    this.top = new int[j];

    this.bufferSize = new int[j];
    int k = 4096;
    for (int m = 0; m < this.bufferSize.length; m++) {
      this.bufferSize[m] = k;
      k <<= 1;
    }
    this.stats = new InternalStatistics(this.bufferSize);
  }

  T get(Class<?> paramClass, int paramInt)
  {
    int i = bufferIndex(paramInt);

    if (i >= this.buckets.length) {
      this.stats.requestTooBig();
      return Array.newInstance(paramClass, paramInt);
    }

    while (this.top[i] > 0) {
      SoftReference localSoftReference = this.buckets[i][(this.top[i] -= 1)];
      this.buckets[i][this.top[i]] = null;
      Object localObject = localSoftReference.get();
      if (localObject != null) {
        this.stats.cacheHit(i);
        return localObject;
      }
    }

    this.stats.cacheMiss(i);
    return Array.newInstance(paramClass, this.bufferSize[i]);
  }

  void put(T paramT)
  {
    int i = Array.getLength(paramT);

    int j = bufferIndex(i);

    if ((j >= this.buckets.length) || (i != this.bufferSize[j])) {
      this.stats.cacheTooBig();
      return;
    }

    if (this.top[j] < 8) {
      this.stats.bufferCached(j);
      int tmp68_67 = j;
      int[] tmp68_64 = this.top;
      int tmp70_69 = tmp68_64[tmp68_67]; tmp68_64[tmp68_67] = (tmp70_69 + 1); this.buckets[j][tmp70_69] = new SoftReference(paramT);
    }
    else
    {
      for (int k = this.top[j]; k > 0; ) {
        if (this.buckets[j][(--k)].get() == null)
        {
          this.stats.refCleared(j);
          this.buckets[j][k] = new SoftReference(paramT);
          return;
        }
      }
      this.stats.bucketFull(j);
    }
  }

  OracleConnection.BufferCacheStatistics getStatistics()
  {
    return this.stats;
  }

  private int bufferIndex(int paramInt)
  {
    for (int i = 0; i < this.bufferSize.length; i++) {
      if (paramInt <= this.bufferSize[i]) return i;
    }
    return 2147483647; } 
  private static final class InternalStatistics implements OracleConnection.BufferCacheStatistics { private static int CACHE_COUNT = 0;

    private final int cacheId = ++CACHE_COUNT;
    private final int[] sizes;
    private final int[] nCacheHit;
    private final int[] nCacheMiss;
    private int nRequestTooBig;
    private final int[] nBufferCached;
    private final int[] nBucketFull;
    private final int[] nRefCleared;
    private int nCacheTooBig;

    InternalStatistics(int[] paramArrayOfInt) { this.sizes = paramArrayOfInt;
      int i = paramArrayOfInt.length;
      this.nCacheHit = new int[i];
      this.nCacheMiss = new int[i];
      this.nRequestTooBig = 0;
      this.nBufferCached = new int[i];
      this.nBucketFull = new int[i];
      this.nRefCleared = new int[i];
      this.nCacheTooBig = 0; }

    void cacheHit(int paramInt) {
      this.nCacheHit[paramInt] += 1; } 
    void cacheMiss(int paramInt) { this.nCacheMiss[paramInt] += 1; } 
    void requestTooBig() { this.nRequestTooBig += 1; } 
    void bufferCached(int paramInt) { this.nBufferCached[paramInt] += 1; } 
    void bucketFull(int paramInt) { this.nBucketFull[paramInt] += 1; } 
    void refCleared(int paramInt) { this.nRefCleared[paramInt] += 1; } 
    void cacheTooBig() { this.nCacheTooBig += 1; }

    public int getId() {
      return this.cacheId;
    }
    public int[] getBufferSizes() { int[] arrayOfInt = new int[this.sizes.length];
      System.arraycopy(this.sizes, 0, arrayOfInt, 0, this.sizes.length);
      return arrayOfInt; } 
    public int getCacheHits(int paramInt) {
      return this.nCacheHit[paramInt]; } 
    public int getCacheMisses(int paramInt) { return this.nCacheMiss[paramInt]; } 
    public int getRequestsTooBig() { return this.nRequestTooBig; } 
    public int getBuffersCached(int paramInt) { return this.nBufferCached[paramInt]; } 
    public int getBucketsFull(int paramInt) { return this.nBucketFull[paramInt]; } 
    public int getReferencesCleared(int paramInt) { return this.nRefCleared[paramInt]; } 
    public int getTooBigToCache() { return this.nCacheTooBig; }

    public String toString() {
      int i = 0;
      int j = 0;
      int k = 0;
      int m = 0;
      int n = 0;
      for (int i1 = 0; i1 < this.sizes.length; i1++) {
        i += this.nCacheHit[i1];
        j += this.nCacheMiss[i1];
        k += this.nBufferCached[i1];
        m += this.nBucketFull[i1];
        n += this.nRefCleared[i1];
      }
      String str = "oracle.jdbc.driver.BufferCache<" + this.cacheId + ">\n" + "\tTotal Hits   :\t" + i + "\n" + "\tTotal Misses :\t" + (j + this.nRequestTooBig) + "\n" + "\tTotal Cached :\t" + k + "\n" + "\tTotal Dropped:\t" + (m + this.nCacheTooBig) + "\n" + "\tTotal Cleared:\t" + n + "\n";

      return str;
    }
  }
}