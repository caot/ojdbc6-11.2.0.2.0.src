package oracle.sql;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import oracle.jdbc.driver.DatabaseError;
import oracle.jdbc.internal.OracleConnection;
import oracle.sql.converter.CharacterConverterFactoryOGS;

// Referenced classes of package oracle.sql:
//            CharacterSetFactoryDefault, CharacterSetFactory, ReadWriteCharacterSetNamesMap, CharacterBuffer,
//            CharacterSetWithConverter, CharacterWalker

public abstract class CharacterSet
{
    static abstract class CharacterConverterBehavior
    {

        public abstract void onFailConversion(char c)
            throws SQLException;

        public abstract void onFailConversion()
            throws SQLException;

        public static final char NULL_CHARS[] = new char[1];
        public static final char UTF16_REPLACEMENT_CHAR = 65533;
        public static final CharacterConverterBehavior REPORT_ERROR = new CharacterConverterBehavior("Report Error") {

            public void onFailConversion()
                throws SQLException
            {
                SQLException sqlexception = DatabaseError.createSqlException(null, 55);
                sqlexception.fillInStackTrace();
                throw sqlexception;
            }

            public void onFailConversion(char c)
                throws SQLException
            {
                if(c == '\uFFFD')
                {
                    SQLException sqlexception = DatabaseError.createSqlException(null, 55);
                    sqlexception.fillInStackTrace();
                    throw sqlexception;
                } else
                {
                    return;
                }
            }

        }
;
        public static final CharacterConverterBehavior REPLACEMENT = new CharacterConverterBehavior("Replacement") {

            public void onFailConversion()
                throws SQLException
            {
            }

            public void onFailConversion(char c)
                throws SQLException
            {
            }

        }
;
        private final String m_name;


        public CharacterConverterBehavior(String s)
        {
            m_name = s;
        }
    }


    CharacterSet(int i)
    {
        oracleId = i;
    }

    public static CharacterSet make(int i)
    {
        return factory.make(i);
    }

    public String toString()
    {
        if(ReadWriteCharacterSetNamesMap.cache == null)
            buildCharacterSetNames();
        return (String)ReadWriteCharacterSetNamesMap.cache.get(new Short((short)oracleId));
    }

    synchronized void buildCharacterSetNames()
    {
        if(ReadWriteCharacterSetNamesMap.cache == null)
        {
            /*<invalid signature>*/java.lang.Object local = oracle/sql/CharacterSet;
            Field afield[] = local.getFields();
            HashMap hashmap = new HashMap();
            for(int i = 0; i < afield.length; i++)
                try
                {
                    String s = afield[i].getName();
                    int j = s.lastIndexOf("_CHARSET");
                    if(j == -1)
                        continue;
                    s = s.substring(0, j);
                    if(s.equals("ASCII") || s.equals("ISO_LATIN_1") || s.equals("AR8MSAWIN") || s.equals("UNICODE_1") || s.equals("UNICODE_2"))
                        continue;
                    short word0 = afield[i].getShort(oracle/sql/CharacterSet);
                    int k = afield[i].getModifiers();
                    if(!Modifier.isStatic(k) || !Modifier.isFinal(k))
                        continue;
                    String s1 = (String)hashmap.get(new Short(word0));
                    if(s1 != null)
                        throw new RuntimeException((new StringBuilder()).append("duplicate field name: ").append(s).append(" for id: ").append(word0).toString());
                    hashmap.put(new Short(word0), s);
                }
                catch(Exception exception)
                {
                    throw new RuntimeException((new StringBuilder()).append("Failed for field: ").append(afield[i]).toString(), exception);
                }

            ReadWriteCharacterSetNamesMap.cache = hashmap;
        }
    }

    public abstract boolean isLossyFrom(CharacterSet characterset);

    public abstract boolean isConvertibleFrom(CharacterSet characterset);

    public boolean isUnicode()
    {
        return false;
    }

    boolean isWellFormed(byte abyte0[], int i, int j)
    {
        return true;
    }

    public int getOracleId()
    {
        return oracleId;
    }

    int getRep()
    {
        return rep;
    }

    public int getRatioTo(CharacterSet characterset)
    {
        throw new Error("oracle.sql.CharacterSet.getRationTo Not Implemented");
    }

    public boolean equals(Object obj)
    {
        return (obj instanceof CharacterSet) && oracleId == ((CharacterSet)obj).oracleId;
    }

    public int hashCode()
    {
        return oracleId;
    }

    public abstract String toStringWithReplacement(byte abyte0[], int i, int j);

    public String toString(byte abyte0[], int i, int j)
        throws SQLException
    {
        String s = toStringWithReplacement(abyte0, i, j);
        byte abyte1[] = convert(s);
        if(j != abyte1.length)
            failCharacterConversion(this);
        for(int k = 0; k < j; k++)
            if(abyte1[k] != abyte0[i + k])
                failCharacterConversion(this);

        return null;
    }

    public abstract byte[] convert(String s)
        throws SQLException;

    public abstract byte[] convertWithReplacement(String s);

    public abstract byte[] convert(CharacterSet characterset, byte abyte0[], int i, int j)
        throws SQLException;

    public byte[] convertUnshared(CharacterSet characterset, byte abyte0[], int i, int j)
        throws SQLException
    {
        byte abyte1[] = convert(characterset, abyte0, i, j);
        if(abyte1 == abyte0)
        {
            abyte1 = new byte[abyte0.length];
            System.arraycopy(abyte0, 0, abyte1, 0, j);
        }
        return abyte1;
    }

    abstract int decode(CharacterWalker characterwalker)
        throws SQLException;

    abstract void encode(CharacterBuffer characterbuffer, int i)
        throws SQLException;

    static final void failCharacterConversion(CharacterSet characterset)
        throws SQLException
    {
        SQLException sqlexception = DatabaseError.createSqlException(null, 55, characterset);
        sqlexception.fillInStackTrace();
        throw sqlexception;
    }

    static final byte[] useOrCopy(byte abyte0[], int i, int j)
    {
        byte abyte1[];
        if(abyte0.length == j && i == 0)
        {
            abyte1 = abyte0;
        } else
        {
            abyte1 = new byte[j];
            System.arraycopy(abyte0, i, abyte1, 0, j);
        }
        return abyte1;
    }

    static final void need(CharacterBuffer characterbuffer, int i)
    {
        int j = characterbuffer.bytes.length;
        int k = i + characterbuffer.next;
        if(k <= j)
            return;
        for(; k > j; j = 2 * j);
        byte abyte0[] = characterbuffer.bytes;
        characterbuffer.bytes = new byte[j];
        System.arraycopy(abyte0, 0, characterbuffer.bytes, 0, characterbuffer.next);
    }

    public static final String UTFToString(byte abyte0[], int i, int j, boolean flag)
        throws SQLException
    {
        return new String(UTFToJavaChar(abyte0, i, j, flag));
    }

    public static final String UTFToString(byte abyte0[], int i, int j)
        throws SQLException
    {
        return UTFToString(abyte0, i, j, false);
    }

    public static final char[] UTFToJavaChar(byte abyte0[], int i, int j)
        throws SQLException
    {
        return UTFToJavaChar(abyte0, i, j, false);
    }

    public static final char[] UTFToJavaChar(byte abyte0[], int i, int j, boolean flag)
        throws SQLException
    {
        char ac[] = null;
        ac = new char[j];
        int ai[] = new int[1];
        ai[0] = j;
        int k = convertUTFBytesToJavaChars(abyte0, i, ac, 0, ai, flag);
        char ac1[] = new char[k];
        System.arraycopy(ac, 0, ac1, 0, k);
        ac = null;
        return ac1;
    }

    public static final char[] UTFToJavaCharWithReplacement(byte abyte0[], int i, int j)
    {
        Object obj = null;
        try
        {
            char ac[] = new char[j];
            int ai[] = new int[1];
            ai[0] = j;
            int k = convertUTFBytesToJavaChars(abyte0, i, ac, 0, ai, true);
            char ac1[] = new char[k];
            System.arraycopy(ac, 0, ac1, 0, k);
            ac = null;
            return ac1;
        }
        catch(SQLException sqlexception)
        {
            throw new IllegalStateException(sqlexception.getMessage());
        }
    }

    public static final int convertUTFBytesToJavaChars(byte abyte0[], int i, char ac[], int j, int ai[], boolean flag)
        throws SQLException
    {
        return convertUTFBytesToJavaChars(abyte0, i, ac, j, ai, flag, ac.length - j);
    }

    public static final int convertUTFBytesToJavaChars(byte abyte0[], int i, char ac[], int j, int ai[], boolean flag, int k)
        throws SQLException
    {
        CharacterConverterBehavior characterconverterbehavior = flag ? CharacterConverterBehavior.REPLACEMENT : CharacterConverterBehavior.REPORT_ERROR;
        int l = ai[0];
        ai[0] = 0;
        int i1 = i;
        int j1 = i + l;
        int k1 = j;
        int l1 = j + k;
label0:
        do
        {
            if(i1 >= j1)
                break;
            byte byte0 = abyte0[i1++];
            int i2 = byte0 & 0xf0;
            switch(i2 / 16)
            {
            case 0: // '\0'
            case 1: // '\001'
            case 2: // '\002'
            case 3: // '\003'
            case 4: // '\004'
            case 5: // '\005'
            case 6: // '\006'
            case 7: // '\007'
                if(k1 < l1)
                {
                    ac[k1++] = (char)(byte0 & -1);
                    continue;
                }
                ai[0] = (j1 - i1) + 2;
                break label0;

            case 12: // '\f'
            case 13: // '\r'
                if(i1 >= j1)
                {
                    ai[0] = 1;
                    characterconverterbehavior.onFailConversion();
                    break label0;
                }
                char c = conv2ByteUTFtoUTF16(byte0, abyte0[i1++]);
                if(k1 < l1)
                {
                    ac[k1++] = c;
                } else
                {
                    ai[0] = (j1 - i1) + 3;
                    break label0;
                }
                characterconverterbehavior.onFailConversion(c);
                continue;

            case 14: // '\016'
                if(i1 + 1 >= j1)
                {
                    ai[0] = (j1 - i1) + 1;
                    characterconverterbehavior.onFailConversion();
                    break label0;
                }
                char c2 = conv3ByteUTFtoUTF16(byte0, abyte0[i1++], abyte0[i1++]);
                if(i2 != 244 && abyte0[i1 - 2] != -65 && abyte0[i1 - 1] != -67)
                    characterconverterbehavior.onFailConversion(c2);
                if(isHiSurrogate(c2))
                {
                    if(k1 > l1 - 2)
                    {
                        ai[0] = (j1 - i1) + 4;
                        break label0;
                    }
                    if(i1 >= j1)
                        continue;
                    byte byte1 = abyte0[i1];
                    if((byte)(byte1 & 0xf0) != -32)
                    {
                        ac[k1++] = '\uFFFD';
                        characterconverterbehavior.onFailConversion();
                        continue;
                    }
                    if(++i1 + 1 >= j1)
                    {
                        ai[0] = (j1 - i1) + 1;
                        characterconverterbehavior.onFailConversion();
                        break label0;
                    }
                    char c1 = conv3ByteUTFtoUTF16(byte1, abyte0[i1++], abyte0[i1++]);
                    if(isLoSurrogate(c1))
                    {
                        ac[k1++] = c2;
                    } else
                    {
                        ac[k1++] = '\uFFFD';
                        characterconverterbehavior.onFailConversion();
                    }
                    ac[k1++] = c1;
                    continue;
                }
                if(k1 < l1)
                {
                    ac[k1++] = c2;
                    continue;
                }
                ai[0] = (j1 - i1) + 4;
                break label0;
            }
            if(k1 < l1)
            {
                ac[k1++] = '\uFFFD';
            } else
            {
                ai[0] = (j1 - i1) + 2;
                break;
            }
            characterconverterbehavior.onFailConversion();
        } while(true);
        int j2 = k1 - j;
        return j2;
    }

    public static final byte[] stringToUTF(String s)
    {
        char ac[] = s.toCharArray();
        int i = ac.length * 3;
        byte abyte0[] = null;
        byte abyte1[] = null;
        abyte0 = new byte[i];
        int j = convertJavaCharsToUTFBytes(ac, 0, abyte0, 0, ac.length);
        abyte1 = new byte[j];
        System.arraycopy(abyte0, 0, abyte1, 0, j);
        abyte0 = null;
        return abyte1;
    }

    public static final int convertJavaCharsToUTFBytes(char ac[], int i, byte abyte0[], int j, int k)
    {
        int l = i;
        int i1 = i + k;
        int j1 = j;
        for(int l1 = l; l1 < i1; l1++)
        {
            char c = ac[l1];
            if(c >= 0 && c <= '\177')
            {
                abyte0[j1++] = (byte)c;
                continue;
            }
            if(c > '\u07FF')
            {
                abyte0[j1++] = (byte)(0xe0 | c >>> 12 & 0xf);
                abyte0[j1++] = (byte)(0x80 | c >>> 6 & 0x3f);
                abyte0[j1++] = (byte)(0x80 | c >>> 0 & 0x3f);
            } else
            {
                abyte0[j1++] = (byte)(0xc0 | c >>> 6 & 0x1f);
                abyte0[j1++] = (byte)(0x80 | c >>> 0 & 0x3f);
            }
        }

        int k1 = j1 - j;
        return k1;
    }

    public static final int UTFStringLength(byte abyte0[], int i, int j)
    {
        int k = 0;
        int l = i;
        int i1 = i + j;
        do
        {
            if(l >= i1)
                break;
            switch((abyte0[l] & 0xf0) >>> 4)
            {
            case 0: // '\0'
            case 1: // '\001'
            case 2: // '\002'
            case 3: // '\003'
            case 4: // '\004'
            case 5: // '\005'
            case 6: // '\006'
            case 7: // '\007'
                l++;
                k++;
                break;

            case 12: // '\f'
            case 13: // '\r'
                if(l + 1 >= i1)
                {
                    l = i1;
                } else
                {
                    k++;
                    l += 2;
                }
                break;

            case 14: // '\016'
                if(l + 2 >= i1)
                {
                    l = i1;
                } else
                {
                    k++;
                    l += 3;
                }
                break;

            case 15: // '\017'
                if(l + 3 >= i1)
                {
                    l = i1;
                } else
                {
                    k += 2;
                    l += 4;
                }
                break;

            case 8: // '\b'
            case 9: // '\t'
            case 10: // '\n'
            case 11: // '\013'
            default:
                l++;
                k++;
                break;
            }
        } while(true);
        return k;
    }

    public static final int stringUTFLength(String s)
    {
        char ac[] = s.toCharArray();
        return charArrayUTF8Length(ac);
    }

    static final int charArrayUTF8Length(char ac[])
    {
        int i = 0;
        int j = ac.length;
        for(int k = 0; k < j; k++)
        {
            char c = ac[k];
            if(c >= 0 && c <= '\177')
            {
                i++;
                continue;
            }
            if(c > '\u07FF')
                i += 3;
            else
                i += 2;
        }

        return i;
    }

    public static final String AL32UTF8ToString(byte abyte0[], int i, int j)
    {
        return AL32UTF8ToString(abyte0, i, j, false);
    }

    public static final String AL32UTF8ToString(byte abyte0[], int i, int j, boolean flag)
    {
        char ac[] = null;
        try
        {
            ac = AL32UTF8ToJavaChar(abyte0, i, j, flag);
        }
        catch(SQLException sqlexception) { }
        return new String(ac);
    }

    public static final char[] AL32UTF8ToJavaChar(byte abyte0[], int i, int j, boolean flag)
        throws SQLException
    {
        Object obj = null;
        try
        {
            char ac[] = new char[j];
            int ai[] = new int[1];
            ai[0] = j;
            int k = convertAL32UTF8BytesToJavaChars(abyte0, i, ac, 0, ai, flag);
            char ac1[] = new char[k];
            System.arraycopy(ac, 0, ac1, 0, k);
            ac = null;
            return ac1;
        }
        catch(SQLException sqlexception)
        {
            failUTFConversion();
        }
        return new char[0];
    }

    public static final int convertAL32UTF8BytesToJavaChars(byte abyte0[], int i, char ac[], int j, int ai[], boolean flag)
        throws SQLException
    {
        return convertAL32UTF8BytesToJavaChars(abyte0, i, ac, j, ai, flag, ac.length - j);
    }

    public static final int convertAL32UTF8BytesToJavaChars(byte abyte0[], int i, char ac[], int j, int ai[], boolean flag, int k)
        throws SQLException
    {
        CharacterConverterBehavior characterconverterbehavior = flag ? CharacterConverterBehavior.REPLACEMENT : CharacterConverterBehavior.REPORT_ERROR;
        int l = ai[0];
        ai[0] = 0;
        int i1 = i;
        int j1 = i + l;
        int k1 = j;
        int l1 = j + k;
label0:
        do
        {
            if(i1 >= j1)
                break;
            byte byte0 = abyte0[i1++];
            int i2 = byte0 & 0xf0;
            switch(i2 / 16)
            {
            case 0: // '\0'
            case 1: // '\001'
            case 2: // '\002'
            case 3: // '\003'
            case 4: // '\004'
            case 5: // '\005'
            case 6: // '\006'
            case 7: // '\007'
                if(k1 < l1)
                {
                    ac[k1++] = (char)(byte0 & -1);
                    continue;
                }
                ai[0] = (j1 - i1) + 2;
                break label0;

            case 12: // '\f'
            case 13: // '\r'
                if(i1 >= j1)
                {
                    ai[0] = 1;
                    characterconverterbehavior.onFailConversion();
                    break label0;
                }
                char c = conv2ByteUTFtoUTF16(byte0, abyte0[i1++]);
                if(k1 < l1)
                {
                    ac[k1++] = c;
                } else
                {
                    ai[0] = (j1 - i1) + 3;
                    break label0;
                }
                characterconverterbehavior.onFailConversion(c);
                continue;

            case 14: // '\016'
                if(i1 + 1 >= j1)
                {
                    ai[0] = (j1 - i1) + 1;
                    characterconverterbehavior.onFailConversion();
                    break label0;
                }
                char c1 = conv3ByteAL32UTF8toUTF16(byte0, abyte0[i1++], abyte0[i1++]);
                if(k1 < l1)
                {
                    ac[k1++] = c1;
                } else
                {
                    ai[0] = (j1 - i1) + 4;
                    break label0;
                }
                characterconverterbehavior.onFailConversion(c1);
                continue;

            case 15: // '\017'
                if(i1 + 2 >= j1)
                {
                    ai[0] = (j1 - i1) + 1;
                    characterconverterbehavior.onFailConversion();
                    break label0;
                }
                if(k1 > l1 - 2)
                {
                    ai[0] = (j1 - i1) + 2;
                    break label0;
                }
                int j2 = conv4ByteAL32UTF8toUTF16(byte0, abyte0[i1++], abyte0[i1++], abyte0[i1++], ac, k1);
                if(j2 == 1)
                {
                    characterconverterbehavior.onFailConversion();
                    k1++;
                } else
                {
                    k1 += 2;
                }
                continue;
            }
            if(k1 < l1)
            {
                ac[k1++] = '\uFFFD';
            } else
            {
                ai[0] = (j1 - i1) + 2;
                break;
            }
            characterconverterbehavior.onFailConversion();
        } while(true);
        int k2 = k1 - j;
        return k2;
    }

    public static final byte[] stringToAL32UTF8(String s)
    {
        char ac[] = s.toCharArray();
        int i = ac.length * 3;
        byte abyte0[] = null;
        byte abyte1[] = null;
        abyte0 = new byte[i];
        int j = convertJavaCharsToAL32UTF8Bytes(ac, 0, abyte0, 0, ac.length);
        abyte1 = new byte[j];
        System.arraycopy(abyte0, 0, abyte1, 0, j);
        abyte0 = null;
        return abyte1;
    }

    public static final int convertJavaCharsToAL32UTF8Bytes(char ac[], int i, byte abyte0[], int j, int k)
    {
        int l = i;
        int i1 = i + k;
        int j1 = j;
        for(int k1 = l; k1 < i1; k1++)
        {
            int l1 = ac[k1];
            boolean flag = false;
            if(l1 >= 0 && l1 <= 127)
            {
                abyte0[j1++] = (byte)l1;
                continue;
            }
            if(isHiSurrogate((char)l1))
            {
                char c;
                if(k1 + 1 < i1 && isLoSurrogate(c = ac[k1 + 1]))
                {
                    int i2 = (l1 >>> 6 & 0xf) + 1;
                    abyte0[j1++] = (byte)(i2 >>> 2 | 0xf0);
                    abyte0[j1++] = (byte)((i2 & 3) << 4 | l1 >>> 2 & 0xf | 0x80);
                    abyte0[j1++] = (byte)((l1 & 3) << 4 | c >>> 6 & 0xf | 0x80);
                    abyte0[j1++] = (byte)(c & 0x3f | 0x80);
                    k1++;
                } else
                {
                    abyte0[j1++] = -17;
                    abyte0[j1++] = -65;
                    abyte0[j1++] = -67;
                }
                continue;
            }
            if(l1 > 2047)
            {
                abyte0[j1++] = (byte)(0xe0 | l1 >>> 12 & 0xf);
                abyte0[j1++] = (byte)(0x80 | l1 >>> 6 & 0x3f);
                abyte0[j1++] = (byte)(0x80 | l1 >>> 0 & 0x3f);
            } else
            {
                abyte0[j1++] = (byte)(0xc0 | l1 >>> 6 & 0x1f);
                abyte0[j1++] = (byte)(0x80 | l1 >>> 0 & 0x3f);
            }
        }

        int j2 = j1 - j;
        return j2;
    }

    public static final int string32UTF8Length(String s)
    {
        return charArray32UTF8Length(s.toCharArray());
    }

    static final int charArray32UTF8Length(char ac[])
    {
        int i = 0;
        int j = ac.length;
        for(int k = 0; k < j; k++)
        {
            int l = ac[k];
            if(l >= 0 && l <= 127)
            {
                i++;
                continue;
            }
            if(l > 2047)
            {
                if(isHiSurrogate((char)l))
                {
                    if(k + 1 < j)
                    {
                        i += 4;
                        k++;
                    }
                } else
                {
                    i += 3;
                }
            } else
            {
                i += 2;
            }
        }

        return i;
    }

    public static final String AL16UTF16BytesToString(byte abyte0[], int i)
    {
        char ac[] = new char[i >>> 1];
        AL16UTF16BytesToJavaChars(abyte0, i, ac);
        return new String(ac);
    }

    public static final int AL16UTF16BytesToJavaChars(byte abyte0[], int i, char ac[])
    {
        int l = i >>> 1;
        int j = 0;
        int k = 0;
        for(; j < l; j++)
        {
            int i1 = abyte0[k] << 8;
            ac[j] = (char)(i1 | abyte0[k + 1] & 0xff);
            k += 2;
        }

        return j;
    }

    public static final int convertAL16UTF16BytesToJavaChars(byte abyte0[], int i, char ac[], int j, int k, boolean flag)
        throws SQLException
    {
        CharacterConverterBehavior characterconverterbehavior = flag ? CharacterConverterBehavior.REPLACEMENT : CharacterConverterBehavior.REPORT_ERROR;
        int l = j;
        int i1 = i;
        for(int j1 = i + k; i1 + 1 < j1; i1 += 2)
        {
            int k1 = abyte0[i1] << 8;
            char c = (char)(k1 | abyte0[i1 + 1] & 0xff);
            ac[l++] = c;
        }

        i1 = l - j;
        return i1;
    }

    public static final int convertAL16UTF16LEBytesToJavaChars(byte abyte0[], int i, char ac[], int j, int k, boolean flag)
        throws SQLException
    {
        CharacterConverterBehavior characterconverterbehavior = flag ? CharacterConverterBehavior.REPLACEMENT : CharacterConverterBehavior.REPORT_ERROR;
        int l = j;
        int i1 = i;
        for(int j1 = i + k; i1 + 1 < j1; i1 += 2)
        {
            int k1 = abyte0[i1 + 1] << 8;
            char c = (char)(k1 | abyte0[i1] & 0xff);
            if(isHiSurrogate(c))
            {
                if((i1 += 2) + 1 >= j1)
                    continue;
                char c1 = (char)((abyte0[i1 + 1] << 8) + (abyte0[i1] & 0xff));
                if(isLoSurrogate(c1))
                    ac[l++] = c;
                else
                    ac[l++] = '\uFFFD';
                ac[l++] = c1;
            } else
            {
                ac[l++] = c;
            }
        }

        i1 = l - j;
        return i1;
    }

    public static final byte[] stringToAL16UTF16Bytes(String s)
    {
        char ac[] = s.toCharArray();
        int i = ac.length;
        byte abyte0[] = new byte[i * 2];
        javaCharsToAL16UTF16Bytes(ac, i, abyte0);
        return abyte0;
    }

    public static final int javaCharsToAL16UTF16Bytes(char ac[], int i, byte abyte0[])
    {
        int j = Math.min(i, abyte0.length >>> 1);
        return convertJavaCharsToAL16UTF16Bytes(ac, 0, abyte0, 0, j);
    }

    public static final int convertJavaCharsToAL16UTF16Bytes(char ac[], int i, byte abyte0[], int j, int k)
    {
        int l = i;
        int i1 = j;
        for(int j1 = i + k; l < j1;)
        {
            abyte0[i1] = (byte)(ac[l] >>> 8 & 0xff);
            abyte0[i1 + 1] = (byte)(ac[l] & 0xff);
            l++;
            i1 += 2;
        }

        return i1 - j;
    }

    public static final byte[] stringToAL16UTF16LEBytes(String s)
    {
        char ac[] = s.toCharArray();
        byte abyte0[] = new byte[ac.length * 2];
        javaCharsToAL16UTF16LEBytes(ac, ac.length, abyte0);
        return abyte0;
    }

    public static final int javaCharsToAL16UTF16LEBytes(char ac[], int i, byte abyte0[])
    {
        return convertJavaCharsToAL16UTF16LEBytes(ac, 0, abyte0, 0, i);
    }

    public static final int convertJavaCharsToAL16UTF16LEBytes(char ac[], int i, byte abyte0[], int j, int k)
    {
        int l = i;
        int i1 = j;
        for(int j1 = i + k; l < j1;)
        {
            abyte0[i1] = (byte)(ac[l] & 0xff);
            abyte0[i1 + 1] = (byte)(ac[l] >>> 8);
            l++;
            i1 += 2;
        }

        return i1 - j;
    }

    public static final int convertASCIIBytesToJavaChars(byte abyte0[], int i, char ac[], int j, int k)
        throws SQLException
    {
        int j1 = j + k;
        int l = j;
        for(int i1 = i; l < j1; i1++)
        {
            ac[l] = (char)(0xff & abyte0[i1]);
            l++;
        }

        return k;
    }

    public static final int convertJavaCharsToASCIIBytes(char ac[], int i, byte abyte0[], int j, int k)
        throws SQLException
    {
        convertJavaCharsToASCIIBytes(ac, i, abyte0, j, k, false);
        return k;
    }

    public static final int convertJavaCharsToASCIIBytes(char ac[], int i, byte abyte0[], int j, int k, boolean flag)
        throws SQLException
    {
        if(flag)
        {
            if(asciiCharSet == null)
                asciiCharSet = make(1);
            byte abyte1[] = asciiCharSet.convertWithReplacement(new String(ac, i, k));
            System.arraycopy(abyte1, 0, abyte0, j, abyte1.length);
            return abyte1.length;
        }
        for(int l = 0; l < k; l++)
            abyte0[j + l] = (byte)ac[i + l];

        return k;
    }

    public static final int convertJavaCharsToISOLATIN1Bytes(char ac[], int i, byte abyte0[], int j, int k)
        throws SQLException
    {
        for(int l = 0; l < k; l++)
        {
            char c = ac[i + l];
            if(c > '\377')
                abyte0[j + l] = -65;
            else
                abyte0[j + l] = (byte)c;
        }

        return k;
    }

    public static final byte[] stringToASCII(String s)
    {
        byte abyte0[] = new byte[s.length()];
        abyte0 = s.getBytes();
        return abyte0;
    }

    public static final long convertUTF32toUTF16(long l)
    {
        if(l > 65535L)
        {
            long l1 = 216L | l - 0x10000L >> 18 & 255L;
            l1 = l - 0x10000L >> 10 & 255L | l1 << 8;
            l1 = 220L | (l & 1023L) >> 8 & 255L | l1 << 8;
            l1 = l & 255L | l1 << 8;
            return l1;
        } else
        {
            return l;
        }
    }

    static final boolean isHiSurrogate(char c)
    {
        return (char)(c & 0xfc00) == '\uD800';
    }

    static final boolean isLoSurrogate(char c)
    {
        return (char)(c & 0xfc00) == '\uDC00';
    }

    static final boolean check80toBF(byte byte0)
    {
        return (byte0 & 0xffffffc0) == -128;
    }

    static final boolean check80to8F(byte byte0)
    {
        return (byte0 & 0xfffffff0) == -128;
    }

    static final boolean check80to9F(byte byte0)
    {
        return (byte0 & 0xffffffe0) == -128;
    }

    static final boolean checkA0toBF(byte byte0)
    {
        return (byte0 & 0xffffffe0) == -96;
    }

    static final boolean check90toBF(byte byte0)
    {
        return (byte0 & 0xffffffc0) == -128 && (byte0 & 0x30) != 0;
    }

    static final char conv2ByteUTFtoUTF16(byte byte0, byte byte1)
    {
        if(byte0 < -62 || byte0 > -33 || !check80toBF(byte1))
            return '\uFFFD';
        else
            return (char)((byte0 & 0x1f) << 6 | byte1 & 0x3f);
    }

    static final char conv3ByteUTFtoUTF16(byte byte0, byte byte1, byte byte2)
    {
        if((byte0 != -32 || !checkA0toBF(byte1) || !check80toBF(byte2)) && (byte0 < -31 || byte0 > -17 || !check80toBF(byte1) || !check80toBF(byte2)))
            return '\uFFFD';
        else
            return (char)((byte0 & 0xf) << 12 | (byte1 & 0x3f) << 6 | byte2 & 0x3f);
    }

    static final char conv3ByteAL32UTF8toUTF16(byte byte0, byte byte1, byte byte2)
    {
        if((byte0 != -32 || !checkA0toBF(byte1) || !check80toBF(byte2)) && (byte0 < -31 || byte0 > -20 || !check80toBF(byte1) || !check80toBF(byte2)) && (byte0 != -19 || !check80to9F(byte1) || !check80toBF(byte2)) && (byte0 < -18 || byte0 > -17 || !check80toBF(byte1) || !check80toBF(byte2)))
            return '\uFFFD';
        else
            return (char)((byte0 & 0xf) << 12 | (byte1 & 0x3f) << 6 | byte2 & 0x3f);
    }

    static final int conv4ByteAL32UTF8toUTF16(byte byte0, byte byte1, byte byte2, byte byte3, char ac[], int i)
    {
        boolean flag = false;
        if((byte0 != -16 || !check90toBF(byte1) || !check80toBF(byte2) || !check80toBF(byte3)) && (byte0 < -15 || byte0 > -13 || !check80toBF(byte1) || !check80toBF(byte2) || !check80toBF(byte3)) && (byte0 != -12 || !check80to8F(byte1) || !check80toBF(byte2) || !check80toBF(byte3)))
        {
            ac[i] = '\uFFFD';
            return 1;
        } else
        {
            ac[i] = (char)((((byte0 & 7) << 2 | byte1 >>> 4 & 3) - 1 & 0xf) << 6 | (byte1 & 0xf) << 2 | byte2 >>> 4 & 3 | 0xd800);
            ac[i + 1] = (char)((byte2 & 0xf) << 6 | byte3 & 0x3f | 0xdc00);
            return 2;
        }
    }

    static void failUTFConversion()
        throws SQLException
    {
        SQLException sqlexception = DatabaseError.createSqlException(null, 55);
        sqlexception.fillInStackTrace();
        throw sqlexception;
    }

    public int encodedByteLength(String s)
    {
        if(s == null || s.length() == 0)
            return 0;
        else
            return convertWithReplacement(s).length;
    }

    public int encodedByteLength(char ac[])
    {
        if(ac == null || ac.length == 0)
            return 0;
        else
            return convertWithReplacement(new String(ac)).length;
    }

    public int toCharWithReplacement(byte abyte0[], int i, char ac[], int j, int k)
        throws SQLException
    {
        SQLException sqlexception = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 23);
        sqlexception.fillInStackTrace();
        throw sqlexception;
    }

    protected OracleConnection getConnectionDuringExceptionHandling()
    {
        return null;
    }

    public boolean isUnknown()
    {
        return false;
    }

    public static final short DEFAULT_CHARSET = -1;
    public static final short ASCII_CHARSET = 1;
    public static final short ISO_LATIN_1_CHARSET = 31;
    public static final short UNICODE_1_CHARSET = 870;
    public static final short US7ASCII_CHARSET = 1;
    public static final short WE8DEC_CHARSET = 2;
    public static final short WE8HP_CHARSET = 3;
    public static final short US8PC437_CHARSET = 4;
    public static final short WE8EBCDIC37_CHARSET = 5;
    public static final short WE8EBCDIC500_CHARSET = 6;
    public static final short WE8EBCDIC285_CHARSET = 8;
    public static final short WE8PC850_CHARSET = 10;
    public static final short D7DEC_CHARSET = 11;
    public static final short F7DEC_CHARSET = 12;
    public static final short S7DEC_CHARSET = 13;
    public static final short E7DEC_CHARSET = 14;
    public static final short SF7ASCII_CHARSET = 15;
    public static final short NDK7DEC_CHARSET = 16;
    public static final short I7DEC_CHARSET = 17;
    public static final short NL7DEC_CHARSET = 18;
    public static final short CH7DEC_CHARSET = 19;
    public static final short YUG7ASCII_CHARSET = 20;
    public static final short SF7DEC_CHARSET = 21;
    public static final short TR7DEC_CHARSET = 22;
    public static final short IW7IS960_CHARSET = 23;
    public static final short IN8ISCII_CHARSET = 25;
    public static final short WE8ISO8859P1_CHARSET = 31;
    public static final short EE8ISO8859P2_CHARSET = 32;
    public static final short SE8ISO8859P3_CHARSET = 33;
    public static final short NEE8ISO8859P4_CHARSET = 34;
    public static final short CL8ISO8859P5_CHARSET = 35;
    public static final short AR8ISO8859P6_CHARSET = 36;
    public static final short EL8ISO8859P7_CHARSET = 37;
    public static final short IW8ISO8859P8_CHARSET = 38;
    public static final short WE8ISO8859P9_CHARSET = 39;
    public static final short NE8ISO8859P10_CHARSET = 40;
    public static final short TH8TISASCII_CHARSET = 41;
    public static final short TH8TISEBCDIC_CHARSET = 42;
    public static final short BN8BSCII_CHARSET = 43;
    public static final short VN8VN3_CHARSET = 44;
    public static final short VN8MSWIN1258_CHARSET = 45;
    public static final short WE8NEXTSTEP_CHARSET = 50;
    public static final short AR8ASMO708PLUS_CHARSET = 61;
    public static final short AR8EBCDICX_CHARSET = 70;
    public static final short AR8XBASIC_CHARSET = 72;
    public static final short EL8DEC_CHARSET = 81;
    public static final short TR8DEC_CHARSET = 82;
    public static final short WE8EBCDIC37C_CHARSET = 90;
    public static final short WE8EBCDIC500C_CHARSET = 91;
    public static final short IW8EBCDIC424_CHARSET = 92;
    public static final short TR8EBCDIC1026_CHARSET = 93;
    public static final short WE8EBCDIC871_CHARSET = 94;
    public static final short WE8EBCDIC284_CHARSET = 95;
    public static final short WE8EBCDIC1047_CHARSET = 96;
    public static final short EEC8EUROASCI_CHARSET = 110;
    public static final short EEC8EUROPA3_CHARSET = 113;
    public static final short LA8PASSPORT_CHARSET = 114;
    public static final short BG8PC437S_CHARSET = 140;
    public static final short EE8PC852_CHARSET = 150;
    public static final short RU8PC866_CHARSET = 152;
    public static final short RU8BESTA_CHARSET = 153;
    public static final short IW8PC1507_CHARSET = 154;
    public static final short RU8PC855_CHARSET = 155;
    public static final short TR8PC857_CHARSET = 156;
    public static final short CL8MACCYRILLIC_CHARSET = 158;
    public static final short CL8MACCYRILLICS_CHARSET = 159;
    public static final short WE8PC860_CHARSET = 160;
    public static final short IS8PC861_CHARSET = 161;
    public static final short EE8MACCES_CHARSET = 162;
    public static final short EE8MACCROATIANS_CHARSET = 163;
    public static final short TR8MACTURKISHS_CHARSET = 164;
    public static final short IS8MACICELANDICS_CHARSET = 165;
    public static final short EL8MACGREEKS_CHARSET = 166;
    public static final short IW8MACHEBREWS_CHARSET = 167;
    public static final short EE8MSWIN1250_CHARSET = 170;
    public static final short CL8MSWIN1251_CHARSET = 171;
    public static final short ET8MSWIN923_CHARSET = 172;
    public static final short BG8MSWIN_CHARSET = 173;
    public static final short EL8MSWIN1253_CHARSET = 174;
    public static final short IW8MSWIN1255_CHARSET = 175;
    public static final short LT8MSWIN921_CHARSET = 176;
    public static final short TR8MSWIN1254_CHARSET = 177;
    public static final short WE8MSWIN1252_CHARSET = 178;
    public static final short BLT8MSWIN1257_CHARSET = 179;
    public static final short D8EBCDIC273_CHARSET = 180;
    public static final short I8EBCDIC280_CHARSET = 181;
    public static final short DK8EBCDIC277_CHARSET = 182;
    public static final short S8EBCDIC278_CHARSET = 183;
    public static final short EE8EBCDIC870_CHARSET = 184;
    public static final short CL8EBCDIC1025_CHARSET = 185;
    public static final short F8EBCDIC297_CHARSET = 186;
    public static final short IW8EBCDIC1086_CHARSET = 187;
    public static final short CL8EBCDIC1025X_CHARSET = 188;
    public static final short N8PC865_CHARSET = 190;
    public static final short BLT8CP921_CHARSET = 191;
    public static final short LV8PC1117_CHARSET = 192;
    public static final short LV8PC8LR_CHARSET = 193;
    public static final short BLT8EBCDIC1112_CHARSET = 194;
    public static final short LV8RST104090_CHARSET = 195;
    public static final short CL8KOI8R_CHARSET = 196;
    public static final short BLT8PC775_CHARSET = 197;
    public static final short F7SIEMENS9780X_CHARSET = 201;
    public static final short E7SIEMENS9780X_CHARSET = 202;
    public static final short S7SIEMENS9780X_CHARSET = 203;
    public static final short DK7SIEMENS9780X_CHARSET = 204;
    public static final short N7SIEMENS9780X_CHARSET = 205;
    public static final short I7SIEMENS9780X_CHARSET = 206;
    public static final short D7SIEMENS9780X_CHARSET = 207;
    public static final short WE8GCOS7_CHARSET = 210;
    public static final short EL8GCOS7_CHARSET = 211;
    public static final short US8BS2000_CHARSET = 221;
    public static final short D8BS2000_CHARSET = 222;
    public static final short F8BS2000_CHARSET = 223;
    public static final short E8BS2000_CHARSET = 224;
    public static final short DK8BS2000_CHARSET = 225;
    public static final short S8BS2000_CHARSET = 226;
    public static final short WE8BS2000_CHARSET = 231;
    public static final short CL8BS2000_CHARSET = 235;
    public static final short WE8BS2000L5_CHARSET = 239;
    public static final short WE8DG_CHARSET = 241;
    public static final short WE8NCR4970_CHARSET = 251;
    public static final short WE8ROMAN8_CHARSET = 261;
    public static final short EE8MACCE_CHARSET = 262;
    public static final short EE8MACCROATIAN_CHARSET = 263;
    public static final short TR8MACTURKISH_CHARSET = 264;
    public static final short IS8MACICELANDIC_CHARSET = 265;
    public static final short EL8MACGREEK_CHARSET = 266;
    public static final short IW8MACHEBREW_CHARSET = 267;
    public static final short US8ICL_CHARSET = 277;
    public static final short WE8ICL_CHARSET = 278;
    public static final short WE8ISOICLUK_CHARSET = 279;
    public static final short WE8MACROMAN8_CHARSET = 351;
    public static final short WE8MACROMAN8S_CHARSET = 352;
    public static final short TH8MACTHAI_CHARSET = 353;
    public static final short TH8MACTHAIS_CHARSET = 354;
    public static final short HU8CWI2_CHARSET = 368;
    public static final short EL8PC437S_CHARSET = 380;
    public static final short EL8EBCDIC875_CHARSET = 381;
    public static final short EL8PC737_CHARSET = 382;
    public static final short LT8PC772_CHARSET = 383;
    public static final short LT8PC774_CHARSET = 384;
    public static final short EL8PC869_CHARSET = 385;
    public static final short EL8PC851_CHARSET = 386;
    public static final short CDN8PC863_CHARSET = 390;
    public static final short HU8ABMOD_CHARSET = 401;
    public static final short AR8ASMO8X_CHARSET = 500;
    public static final short AR8NAFITHA711T_CHARSET = 504;
    public static final short AR8SAKHR707T_CHARSET = 505;
    public static final short AR8MUSSAD768T_CHARSET = 506;
    public static final short AR8ADOS710T_CHARSET = 507;
    public static final short AR8ADOS720T_CHARSET = 508;
    public static final short AR8APTEC715T_CHARSET = 509;
    public static final short AR8NAFITHA721T_CHARSET = 511;
    public static final short AR8HPARABIC8T_CHARSET = 514;
    public static final short AR8NAFITHA711_CHARSET = 554;
    public static final short AR8SAKHR707_CHARSET = 555;
    public static final short AR8MUSSAD768_CHARSET = 556;
    public static final short AR8ADOS710_CHARSET = 557;
    public static final short AR8ADOS720_CHARSET = 558;
    public static final short AR8APTEC715_CHARSET = 559;
    public static final short AR8MSAWIN_CHARSET = 560;
    public static final short AR8NAFITHA721_CHARSET = 561;
    public static final short AR8SAKHR706_CHARSET = 563;
    public static final short AR8ARABICMAC_CHARSET = 565;
    public static final short AR8ARABICMACS_CHARSET = 566;
    public static final short AR8ARABICMACT_CHARSET = 567;
    public static final short LA8ISO6937_CHARSET = 590;
    public static final short US8NOOP_CHARSET = 797;
    public static final short WE8DECTST_CHARSET = 798;
    public static final short JA16VMS_CHARSET = 829;
    public static final short JA16EUC_CHARSET = 830;
    public static final short JA16EUCYEN_CHARSET = 831;
    public static final short JA16SJIS_CHARSET = 832;
    public static final short JA16DBCS_CHARSET = 833;
    public static final short JA16SJISYEN_CHARSET = 834;
    public static final short JA16EBCDIC930_CHARSET = 835;
    public static final short JA16MACSJIS_CHARSET = 836;
    public static final short JA16EUCTILDE_CHARSET = 837;
    public static final short JA16SJISTILDE_CHARSET = 838;
    public static final short KO16KSC5601_CHARSET = 840;
    public static final short KO16DBCS_CHARSET = 842;
    public static final short KO16KSCCS_CHARSET = 845;
    public static final short KO16MSWIN949_CHARSET = 846;
    public static final short ZHS16CGB231280_CHARSET = 850;
    public static final short ZHS16MACCGB231280_CHARSET = 851;
    public static final short ZHS16GBK_CHARSET = 852;
    public static final short ZHS16DBCS_CHARSET = 853;
    public static final short ZHS32GB18030_CHARSET = 854;
    public static final short ZHT32EUC_CHARSET = 860;
    public static final short ZHT32SOPS_CHARSET = 861;
    public static final short ZHT16DBT_CHARSET = 862;
    public static final short ZHT32TRIS_CHARSET = 863;
    public static final short ZHT16DBCS_CHARSET = 864;
    public static final short ZHT16BIG5_CHARSET = 865;
    public static final short ZHT16CCDC_CHARSET = 866;
    public static final short ZHT16MSWIN950_CHARSET = 867;
    public static final short AL24UTFFSS_CHARSET = 870;
    public static final short UTF8_CHARSET = 871;
    public static final short UTFE_CHARSET = 872;
    public static final short AL32UTF8_CHARSET = 873;
    public static final short KO16TSTSET_CHARSET = 996;
    public static final short JA16TSTSET2_CHARSET = 997;
    public static final short JA16TSTSET_CHARSET = 998;
    public static final short US16TSTFIXED_CHARSET = 1001;
    public static final short AL16UTF16_CHARSET = 2000;
    public static final short AL16UTF16LE_CHARSET = 2002;
    public static final short TH8TISEBCDICS_CHARSET = 319;
    public static final short BLT8EBCDIC1112S_CHARSET = 314;
    public static final short CE8BS2000_CHARSET = 233;
    public static final short CL8EBCDIC1025R_CHARSET = 323;
    public static final short CL8EBCDIC1158R_CHARSET = 326;
    public static final short D8EBCDIC1141_CHARSET = 189;
    public static final short DK8EBCDIC1142_CHARSET = 198;
    public static final short EE8BS2000_CHARSET = 232;
    public static final short EE8EBCDIC870S_CHARSET = 316;
    public static final short EL8EBCDIC423R_CHARSET = 327;
    public static final short EL8EBCDIC875S_CHARSET = 311;
    public static final short EL8EBCDIC875R_CHARSET = 324;
    public static final short F8EBCDIC1147_CHARSET = 208;
    public static final short I8EBCDIC1144_CHARSET = 200;
    public static final short WE8BS2000E_CHARSET = 230;
    public static final short WE8EBCDIC1047E_CHARSET = 100;
    public static final short WE8EBCDIC1140_CHARSET = 7;
    public static final short WE8EBCDIC1145_CHARSET = 98;
    public static final short WE8EBCDIC1146_CHARSET = 9;
    public static final short WE8EBCDIC1148_CHARSET = 27;
    public static final short AR8EBCDIC420S_CHARSET = 320;
    public static final short IW8EBCDIC424S_CHARSET = 315;
    public static final short TR8EBCDIC1026S_CHARSET = 312;
    public static final short ZHT16HKSCS_CHARSET = 868;
    public static final short BLT8ISO8859P13_CHARSET = 47;
    public static final short WE8ISO8859P15_CHARSET = 46;
    public static final short AR8MSWIN1256_CHARSET = 560;
    public static final short S8EBCDIC1143_CHARSET = 199;
    public static final short ZHT16HKSCS31_CHARSET = 992;
    public static final short AZ8ISO8859P9E_CHARSET = 52;
    public static final short CEL8ISO8859P14_CHARSET = 48;
    public static final short CL8ISOIR111_CHARSET = 49;
    public static final short CL8KOI8U_CHARSET = 51;
    public static final short WE8PC858_CHARSET = 28;
    public static final short CL8EBCDIC1025C_CHARSET = 322;
    public static final short CL8EBCDIC1025S_CHARSET = 317;
    public static final short CL8EBCDIC1158_CHARSET = 325;
    public static final short EE8EBCDIC870C_CHARSET = 301;
    public static final short WE8EBCDIC924_CHARSET = 101;
    public static final short WE8EBCDIC1140C_CHARSET = 97;
    public static final short WE8EBCDIC1148C_CHARSET = 99;
    public static final short UNICODE_2_CHARSET = 871;
    private static CharacterSet asciiCharSet = null;
    static CharacterSetFactory factory = new CharacterSetFactoryDefault();
    private int oracleId;
    int rep;
    private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
    public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
    public static final boolean TRACE = false;

    static
    {
        try
        {
            Class.forName("oracle.i18n.text.converter.CharacterConverterSJIS");
            CharacterSetWithConverter.ccFactory = new CharacterConverterFactoryOGS();
        }
        catch(ClassNotFoundException classnotfoundexception) { }
    }
}
