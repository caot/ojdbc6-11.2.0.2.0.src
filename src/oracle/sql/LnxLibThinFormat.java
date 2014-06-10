package oracle.sql;

import java.sql.SQLException;
import oracle.core.lmx.CoreException;

class LnxLibThinFormat
{
  boolean LNXNFFMI = false;
  boolean LNXNFFDS = false;
  boolean LNXNFFPR = false;
  boolean LNXNFFBL = false;
  boolean LNXNFFDA = false;
  boolean LNXNFFED = false;
  boolean LNXNFFSN = false;
  boolean LNXNFFVF = false;

  boolean LNXNFFSH = false;
  boolean LNXNFFST = false;
  boolean LNXNFFCH = false;
  boolean LNXNFFCT = false;
  boolean LNXNFFRC = false;
  boolean LNXNFFRN = false;
  boolean LNXNFFLC = false;
  boolean LNXNFFIC = false;

  boolean LNXNFNRD = false;
  boolean LNXNFRDX = false;
  boolean LNXNFFIL = false;

  boolean LNXNFFPT = false;
  boolean LNXNFF05 = false;

  boolean LNXNFFHX = false;
  boolean LNXNFFTM = false;
  boolean LNXNFFUN = false;

  byte[] lnxnfgps = new byte[40];
  int lnxnflhd = 0;
  int lnxnfrhd = 0;
  int lnxnfsiz = 0;
  int lnxnfzld = 0;
  int lnxnfztr = 0;
  private static final int LNXPFL_US = 1;
  private static final int LNXPFL_NLS = -1;
  private static final int LXM_LILCURR = 11;
  private static final int LXM_LIUCURR = 11;
  private static final int LXM_LIICURR = 8;
  private static final int LXM_ROMOUT = 15;

  public void parseFormat(String paramString)
    throws SQLException
  {
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;

    int i2 = 0;
    int i3 = 0;

    int i4 = 0;
    int i5 = 0;
    int i6 = 0;
    int i7 = 0;
    int i8 = 0;
    int i9 = 39;
    int i10 = 0;

    i3 = paramString.length();

    char[] arrayOfChar = paramString.toCharArray();

    this.LNXNFFIL = true;

    while (i3 != 0)
    {
      int i1 = Character.toLowerCase(arrayOfChar[i4]);

      switch (i1)
      {
      case 48:
      case 53:
      case 57:
      case 120:
        if (this.LNXNFFSN)
        {
          throw new SQLException(CoreException.getMessage((byte)5));
        }

        if ((i2 == 120) && (i1 != 120))
        {
          throw new SQLException(CoreException.getMessage((byte)5));
        }

        if (i1 == 53)
        {
          if (i3 == 2)
          {
            i6 = Character.toLowerCase(arrayOfChar[(i4 + 1)]);
          }
          else if (i3 == 3)
          {
            i6 = Character.toLowerCase(arrayOfChar[(i4 + 1)]);
            i7 = Character.toLowerCase(arrayOfChar[(i4 + 2)]);
          }

          if ((!this.LNXNFF05) && ((i3 == 1) || ((i3 == 2) && (i6 == 115)) || (i6 == 99) || (i6 == 108) || (i6 == 117) || ((i3 == 3) && (((i6 == 112) && (i7 == 114)) || ((i6 == 112) && (i7 == 116)) || ((i6 == 109) && (i7 == 105))))))
          {
            this.LNXNFF05 = true;
          }
          else
          {
            throw new SQLException(CoreException.getMessage((byte)5));
          }

        }

        if (i1 == 120)
        {
          if ((i2 == 0) || (i2 == 109) || (i2 == 48) || (i2 == 120))
          {
            this.LNXNFFHX = true;

            if (arrayOfChar[i4] == 'x')
            {
              this.LNXNFFLC = true;
            }
          }
          else
          {
            throw new SQLException(CoreException.getMessage((byte)5));
          }

        }

        i++;
        if (i1 != 48)
        {
          break label2099;
        }
        if ((k == 0) && (j != 0))
          break label2099;
        j = i; break;
      case 103:
        if ((this.LNXNFFSN) || (this.LNXNFFHX) || (k != 0) || (i8 == i9) || (i10 > 0) || (i == 0))
        {
          throw new SQLException(CoreException.getMessage((byte)5));
        }

        i10 = -1;

        this.lnxnfgps[i8] = ((byte)(0x80 | i));
        i8++;
        break;
      case 44:
        if ((this.LNXNFFSN) || (this.LNXNFFHX) || (k != 0) || (i8 == i9) || (i10 < 0) || (i == 0))
        {
          throw new SQLException(CoreException.getMessage((byte)5));
        }

        m = 1;
        this.lnxnfgps[i8] = ((byte)i);
        i8++;
        i10 = 1;
        break;
      case 99:
      case 108:
      case 117:
        if ((this.LNXNFFCH) || (this.LNXNFFCT) || (this.LNXNFFRC) || (this.LNXNFFSN) || (this.LNXNFFDS) || (this.LNXNFFHX))
        {
          throw new SQLException(CoreException.getMessage((byte)5));
        }

        if (i1 == 99)
        {
          n += 7;
          this.LNXNFFIC = true;
        }
        else if (i1 == 108)
        {
          n += 10;
        }
        else
        {
          n += 10;
          this.LNXNFFUN = true;
        }
        if (i4 == i5)
        {
          this.LNXNFFCH = true;
          break label2099;
        }

        if (i3 == 2)
        {
          i6 = Character.toLowerCase(arrayOfChar[(i4 + 1)]);
        }
        else if (i3 == 3)
        {
          i6 = Character.toLowerCase(arrayOfChar[(i4 + 1)]);
          i7 = Character.toLowerCase(arrayOfChar[(i4 + 2)]);
        }

        if ((i3 == 1) || ((i3 == 2) && (i6 == 115)) || ((i3 == 3) && (((i6 == 112) && (i7 == 114)) || ((i6 == 112) && (i7 == 116)) || ((i6 == 109) && (i7 == 105)))))
        {
          this.LNXNFFCT = true;
          break label2099;
        }

        if (this.LNXNFF05)
        {
          throw new SQLException(CoreException.getMessage((byte)5));
        }

        this.LNXNFFRC = true;
      case 100:
        if ((i10 > 0) || (this.LNXNFFHX))
        {
          throw new SQLException(CoreException.getMessage((byte)5));
        }

        i10 = -1;
      case 118:
        if (i1 == 118)
        {
          this.LNXNFNRD = true;
        }

      case 46:
        if ((this.LNXNFFSN) || (this.LNXNFFHX) || (k != 0))
        {
          throw new SQLException(CoreException.getMessage((byte)5));
        }

        k = 1;
        this.lnxnflhd = i;
        if (j != 0)
        {
          this.lnxnfzld = (i - j + 1);
          j = 0;
        }
        else
        {
          this.lnxnfzld = 0;
        }

        i = 0;
        if ((i1 != 46) && (i1 != 100))
          break label2099;
        n++;

        if (i1 != 46) {
          break label2099;
        }
        if (i10 < 0)
        {
          throw new SQLException(CoreException.getMessage((byte)5));
        }

        i10 = 1;
        this.LNXNFRDX = true; break;
      case 98:
        if ((this.LNXNFFSN) || (this.LNXNFFBL) || (this.LNXNFFHX))
        {
          throw new SQLException(CoreException.getMessage((byte)5));
        }

        this.LNXNFFBL = true;
        break;
      case 101:
        if ((this.LNXNFFSN) || (this.LNXNFF05) || (this.LNXNFFHX) || (m != 0))
        {
          throw new SQLException(CoreException.getMessage((byte)5));
        }

        this.LNXNFFSN = true;

        if ((i3 < 4) || (arrayOfChar[i4] != arrayOfChar[(i4 + 1)]) || (arrayOfChar[i4] != arrayOfChar[(i4 + 2)]) || (arrayOfChar[i4] != arrayOfChar[(i4 + 3)]))
        {
          throw new SQLException(CoreException.getMessage((byte)5));
        }

        i4 += 3;
        i3 -= 3;
        n += 5;
        break;
      case 36:
        if ((this.LNXNFFSN) || (this.LNXNFFDS) || (this.LNXNFFCH) || (this.LNXNFFCT) || (this.LNXNFFRC) || (this.LNXNFFHX))
        {
          throw new SQLException(CoreException.getMessage((byte)5));
        }

        this.LNXNFFDS = true;
        n++;
        break;
      case 114:
        if ((i4 != i5) || (i3 != 2))
        {
          throw new SQLException(CoreException.getMessage((byte)5));
        }

        this.LNXNFFRN = true;

        if (arrayOfChar[i4] == 'r')
        {
          this.LNXNFFLC = true;
        }
        this.lnxnfsiz = 15;

        this.LNXNFFVF = true;
        return;
      case 102:
        if ((i4 != i5) || (!this.LNXNFFIL))
        {
          throw new SQLException(CoreException.getMessage((byte)5));
        }

        this.LNXNFFIL = false;
        i4++;
        if (Character.toLowerCase(arrayOfChar[i4]) == 'm')
        {
          i3--;

          i5 = i4 + 1;
          i1 = 109;
          break label2099;
        }

        throw new SQLException(CoreException.getMessage((byte)5));
      case 112:
        if ((this.LNXNFFSH) || (this.LNXNFFST) || (this.LNXNFFHX))
        {
          throw new SQLException(CoreException.getMessage((byte)5));
        }

        n++;

        i3--;
        if (i3 > 1)
        {
          throw new SQLException(CoreException.getMessage((byte)5));
        }

        i4++;
        if (Character.toLowerCase(arrayOfChar[i4]) == 'r')
        {
          this.LNXNFFPR = true;
          break label2099;
        }

        if (Character.toLowerCase(arrayOfChar[i4]) == 't')
        {
          this.LNXNFFPT = true;
          break label2099;
        }

        throw new SQLException(CoreException.getMessage((byte)5));
      case 109:
        if ((this.LNXNFFSH) || (this.LNXNFFST) || (this.LNXNFFHX))
        {
          throw new SQLException(CoreException.getMessage((byte)5));
        }

        this.LNXNFFMI = true;

        i4++;
        if (Character.toLowerCase(arrayOfChar[i4]) == 'i')
        {
          i3--;
          if (i3 <= 1)
            break label2099;
          throw new SQLException(CoreException.getMessage((byte)5));
        }

        throw new SQLException(CoreException.getMessage((byte)5));
      case 115:
        if ((this.LNXNFFSH) || (this.LNXNFFHX))
        {
          throw new SQLException(CoreException.getMessage((byte)5));
        }

        if (i4 == i5)
        {
          this.LNXNFFSH = true;

          i5++;
          break label2099;
        }
        if (i3 == 1)
        {
          this.LNXNFFST = true;
          break label2099;
        }

        throw new SQLException(CoreException.getMessage((byte)5));
      case 116:
        if ((i4 != i5) || (i3 < 2) || (i3 > 3))
        {
          throw new SQLException(CoreException.getMessage((byte)5));
        }

        if (Character.toLowerCase(arrayOfChar[(i4 + 1)]) != 'm')
        {
          throw new SQLException(CoreException.getMessage((byte)5));
        }

        this.LNXNFFTM = true;
        this.LNXNFFIL = false;
        switch (i3 == 3 ? Character.toLowerCase(arrayOfChar[(i4 + 2)]) : 57)
        {
        case '9':
          break;
        case 'e':
          this.LNXNFFSN = true;
          break;
        default:
          throw new SQLException(CoreException.getMessage((byte)5));
        }

        this.lnxnflhd = 0;
        this.lnxnfrhd = 0;
        this.lnxnfsiz = 64;
        this.lnxnfzld = 0;
        this.lnxnfztr = 0;

        this.LNXNFFVF = true;
        return;
      case 37:
      case 38:
      case 39:
      case 40:
      case 41:
      case 42:
      case 43:
      case 45:
      case 47:
      case 49:
      case 50:
      case 51:
      case 52:
      case 54:
      case 55:
      case 56:
      case 58:
      case 59:
      case 60:
      case 61:
      case 62:
      case 63:
      case 64:
      case 65:
      case 66:
      case 67:
      case 68:
      case 69:
      case 70:
      case 71:
      case 72:
      case 73:
      case 74:
      case 75:
      case 76:
      case 77:
      case 78:
      case 79:
      case 80:
      case 81:
      case 82:
      case 83:
      case 84:
      case 85:
      case 86:
      case 87:
      case 88:
      case 89:
      case 90:
      case 91:
      case 92:
      case 93:
      case 94:
      case 95:
      case 96:
      case 97:
      case 104:
      case 105:
      case 106:
      case 107:
      case 110:
      case 111:
      case 113:
      case 119: } throw new SQLException(CoreException.getMessage((byte)5));

      label2099: i2 = i1;

      i4++;
      i3--;
    }

    if (k != 0)
    {
      this.lnxnfrhd = i;
      this.lnxnfztr = ((this.LNXNFFIL) || (this.LNXNFNRD) ? i : j);
    }
    else
    {
      this.lnxnflhd = i;
      this.lnxnfzld = (j != 0 ? i - j + 1 : 0);
      this.lnxnfrhd = 0;
      this.lnxnfztr = 0;
      this.LNXNFNRD = true;
    }

    if (this.LNXNFFSN)
    {
      if (this.lnxnflhd <= 1)
      {
        if (this.lnxnflhd == 0)
        {
          throw new SQLException(CoreException.getMessage((byte)5));
        }

      }
      else
      {
        this.lnxnflhd = 1;
      }

      if (this.lnxnfzld > 1)
      {
        this.lnxnfzld = 1;
      }

    }

    n += this.lnxnflhd;
    n += this.lnxnfrhd;
    n += i8 + 1;

    if (n > 64)
    {
      throw new SQLException(CoreException.getMessage((byte)5));
    }

    this.lnxnfsiz = n;
  }
}