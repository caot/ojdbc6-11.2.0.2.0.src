package oracle.jdbc.driver;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Map;
import oracle.sql.Datum;
import oracle.sql.NUMBER;

class NumberCommonAccessor extends Accessor
{
  static final boolean GET_XXX_ROUNDS = false;
  int[] digs = new int[27];
  static final int LNXSGNBT = 128;
  static final byte LNXDIGS = 20;
  static final byte LNXEXPBS = 64;
  static final int LNXEXPMX = 127;
  static final BigDecimal BIGDEC_ZERO = BigDecimal.valueOf(0L);
  static final byte MAX_LONG_EXPONENT = 9;
  static final byte MIN_LONG_EXPONENT = 9;
  static final byte MAX_INT_EXPONENT = 4;
  static final byte MIN_INT_EXPONENT = 4;
  static final byte MAX_SHORT_EXPONENT = 2;
  static final byte MIN_SHORT_EXPONENT = 2;
  static final byte MAX_BYTE_EXPONENT = 1;
  static final byte MIN_BYTE_EXPONENT = 1;
  static final int[] MAX_LONG = { 202, 10, 23, 34, 73, 4, 69, 55, 78, 59, 8 };

  static final int[] MIN_LONG = { 53, 92, 79, 68, 29, 98, 33, 47, 24, 43, 93, 102 };
  static final int MAX_LONG_length = 11;
  static final int MIN_LONG_length = 12;
  static final double[] factorTable = { 9.999999999999999E+253D, 1.E+252D, 9.999999999999999E+249D, 1.0E+248D, 1.E+246D, 1.E+244D, 1.E+242D, 1.0E+240D, 1.0E+238D, 1.E+236D, 1.0E+234D, 1.E+232D, 1.E+230D, 9.999999999999999E+227D, 1.0E+226D, 1.0E+224D, 1.0E+222D, 1.0E+220D, 1.E+218D, 1.0E+216D, 1.0E+214D, 9.999999999999999E+211D, 9.999999999999999E+209D, 1.0E+208D, 1.0E+206D, 1.0E+204D, 9.999999999999999E+201D, 1.0E+200D, 1.0E+198D, 1.0E+196D, 9.999999999999999E+193D, 1.0E+192D, 1.E+190D, 1.0E+188D, 1.0E+186D, 1.0E+184D, 1.E+182D, 1.0E+180D, 1.E+178D, 1.0E+176D, 1.E+174D, 1.E+172D, 1.0E+170D, 9.999999999999999E+167D, 9.999999999999999E+165D, 1.0E+164D, 9.999999999999999E+161D, 1.0E+160D, 1.0E+158D, 1.0E+156D, 1.0E+154D, 1.0E+152D, 1.0E+150D, 1.0E+148D, 9.999999999999999E+145D, 1.0E+144D, 1.E+142D, 1.E+140D, 1.0E+138D, 1.E+136D, 9.999999999999999E+133D, 1.0E+132D, 1.E+130D, 1.E+128D, 9.999999999999999E+125D, 1.0E+124D, 1.0E+122D, 1.0E+120D, 1.0E+118D, 1.0E+116D, 1.0E+114D, 9.999999999999999E+111D, 1.0E+110D, 1.0E+108D, 1.E+106D, 1.0E+104D, 1.0E+102D, 1.0E+100D, 1.0E+98D, 1.0E+96D, 1.0E+94D, 1.0E+92D, 1.0E+90D, 1.0E+88D, 1.0E+86D, 1.E+84D, 1.0E+82D, 1.0E+80D, 1.0E+78D, 1.0E+76D, 1.0E+74D, 9.999999999999999E+71D, 1.E+70D, 1.0E+68D, 1.0E+66D, 1.0E+64D, 1.0E+62D, 1.0E+60D, 9.999999999999999E+57D, 1.E+56D, 1.E+54D, 1.0E+52D, 1.E+50D, 1.0E+48D, 1.0E+46D, 1.E+44D, 1.0E+42D, 1.0E+40D, 1.0E+38D, 1.0E+36D, 1.0E+34D, 1.E+32D, 1.0E+30D, 1.0E+28D, 1.0E+26D, 1.0E+24D, 1.0E+22D, 1.0E+20D, 1.0E+18D, 10000000000000000.0D, 100000000000000.0D, 1000000000000.0D, 10000000000.0D, 100000000.0D, 1000000.0D, 10000.0D, 100.0D, 1.0D, 0.01D, 0.0001D, 1.0E-06D, 1.0E-08D, 1.0E-10D, 1.0E-12D, 1.0E-14D, 1.0E-16D, 1.E-18D, 1.0E-20D, 1.0E-22D, 9.999999999999999E-25D, 1.0E-26D, 1.0E-28D, 1.E-30D, 1.E-32D, 9.999999999999999E-35D, 9.999999999999999E-37D, 1.0E-38D, 9.999999999999999E-41D, 1.0E-42D, 1.0E-44D, 1.0E-46D, 1.0E-48D, 1.0E-50D, 1.0E-52D, 1.0E-54D, 1.0E-56D, 1.0E-58D, 1.0E-60D, 1.0E-62D, 1.0E-64D, 1.0E-66D, 1.E-68D, 1.0E-70D, 1.0E-72D, 1.0E-74D, 9.999999999999999E-77D, 1.0E-78D, 1.0E-80D, 1.0E-82D, 1.0E-84D, 1.E-86D, 9.999999999999999E-89D, 1.0E-90D, 1.0E-92D, 1.0E-94D, 9.999999999999999E-97D, 9.999999999999999E-99D, 1.0E-100D, 9.999999999999999E-103D, 9.999999999999999E-105D, 9.999999999999999E-107D, 1.0E-108D, 1.E-110D, 1.0E-112D, 1.E-114D, 1.0E-116D, 1.0E-118D, 1.0E-120D, 1.E-122D, 9.999999999999999E-125D, 1.0E-126D, 1.E-128D, 1.E-130D, 1.0E-132D, 1.0E-134D, 1.0E-136D, 1.E-138D, 1.0E-140D, 1.0E-142D, 1.0E-144D, 1.0E-146D, 9.999999999999999E-149D, 1.0E-150D, 1.E-152D, 1.0E-154D, 1.0E-156D, 1.E-158D, 1.0E-160D, 1.0E-162D, 1.0E-164D, 1.0E-166D, 1.0E-168D, 1.0E-170D, 1.0E-172D, 1.0E-174D, 1.0E-176D, 1.0E-178D, 1.0E-180D, 1.0E-182D, 1.E-184D, 9.999999999999999E-187D, 1.0E-188D, 1.0E-190D, 1.E-192D, 1.0E-194D, 1.0E-196D, 9.999999999999999E-199D, 1.0E-200D, 1.0E-202D, 1.0E-204D, 1.0E-206D, 1.E-208D, 1.0E-210D, 1.0E-212D, 9.999999999999999E-215D, 1.0E-216D, 1.0E-218D, 1.0E-220D, 1.0E-222D, 1.0E-224D, 9.999999999999999E-227D, 1.0E-228D, 1.0E-230D, 1.0E-232D, 1.0E-234D, 1.0E-236D, 1.0E-238D, 1.0E-240D, 1.0E-242D, 9.999999999999999E-245D, 1.0E-246D, 1.0E-248D, 1.E-250D, 9.999999999999999E-253D, 9.999999999999999E-255D };

  static final double[] small10pow = { 1.0D, 10.0D, 100.0D, 1000.0D, 10000.0D, 100000.0D, 1000000.0D, 10000000.0D, 100000000.0D, 1000000000.0D, 10000000000.0D, 100000000000.0D, 1000000000000.0D, 10000000000000.0D, 100000000000000.0D, 1000000000000000.0D, 10000000000000000.0D, 1.0E+17D, 1.0E+18D, 1.0E+19D, 1.0E+20D, 1.0E+21D, 1.0E+22D };

  static final int tablemax = factorTable.length;
  static final double tablemaxexponent = 127.0D;
  static final double tableminexponent = 127.0D - (tablemax - 20);
  static final int MANTISSA_SIZE = 53;
  static final int[] expdigs0 = { 25597, 55634, 18440, 18324, 42485, 50370, 56862, 11593, 45703, 57341, 10255, 12549, 59579, 5 };

  static final int[] expdigs1 = { 50890, 19916, 24149, 23777, 11324, 41057, 14921, 56274, 30917, 19462, 54968, 47943, 38791, 3872 };

  static final int[] expdigs2 = { 24101, 29690, 40218, 29073, 29604, 22037, 27674, 9082, 56670, 55244, 20865, 54874, 47573, 38 };

  static final int[] expdigs3 = { 22191, 40873, 1607, 45622, 23883, 24544, 32988, 43530, 61694, 55616, 43150, 32976, 27418, 25379 };

  static final int[] expdigs4 = { 55927, 44317, 6569, 54851, 238, 63160, 51447, 12231, 55667, 25459, 5674, 40962, 52047, 253 };

  static final int[] expdigs5 = { 56264, 8962, 51839, 64773, 39323, 49783, 15587, 30924, 36601, 56615, 27581, 36454, 35254, 2 };

  static final int[] expdigs6 = { 21545, 25466, 59727, 37873, 13099, 7602, 15571, 49963, 37664, 46896, 14328, 59258, 17403, 1663 };

  static final int[] expdigs7 = { 12011, 4842, 3874, 57395, 38141, 46606, 49307, 60792, 31833, 21440, 9318, 47123, 41461, 16 };

  static final int[] expdigs8 = { 52383, 25023, 56409, 43947, 51036, 17420, 62725, 5735, 53692, 44882, 64439, 36137, 24719, 10900 };

  static final int[] expdigs9 = { 65404, 27119, 57580, 26653, 42453, 19179, 26186, 42000, 1847, 62708, 14406, 12813, 247, 109 };

  static final int[] expdigs10 = { 36698, 50078, 40552, 35000, 49576, 56552, 261, 49572, 31475, 59609, 45363, 46658, 5900, 1 };

  static final int[] expdigs11 = { 33321, 54106, 42443, 60698, 47535, 24088, 45785, 18352, 47026, 40291, 5183, 35843, 24059, 714 };

  static final int[] expdigs12 = { 12129, 44450, 22706, 34030, 37175, 8760, 31915, 56544, 23407, 52176, 7260, 41646, 9415, 7 };

  static final int[] expdigs13 = { 43054, 17160, 43698, 6780, 36385, 52800, 62346, 52747, 33988, 2855, 31979, 38083, 44325, 4681 };

  static final int[] expdigs14 = { 60723, 40803, 16165, 19073, 2985, 9703, 41911, 37227, 41627, 1994, 38986, 27250, 53527, 46 };

  static final int[] expdigs15 = { 36481, 57623, 45627, 58488, 53274, 7238, 2063, 31221, 62631, 25319, 35409, 25293, 54667, 30681 };

  static final int[] expdigs16 = { 52138, 47106, 3077, 4517, 41165, 38738, 39997, 10142, 13078, 16637, 53438, 54647, 53630, 306 };

  static final int[] expdigs17 = { 25425, 24719, 55736, 8564, 12208, 3664, 51518, 17140, 61079, 30312, 2500, 30693, 4468, 3 };

  static final int[] expdigs18 = { 58368, 65134, 52675, 3178, 26300, 7986, 11833, 515, 23109, 63525, 29138, 19030, 50114, 2010 };

  static final int[] expdigs19 = { 41216, 15724, 12323, 26246, 59245, 58406, 46648, 13767, 11372, 15053, 61895, 48686, 7054, 20 };

  static final int[] expdigs20 = { 0, 29248, 62416, 1433, 14025, 43846, 39905, 44375, 137, 47955, 62409, 33386, 48983, 13177 };

  static final int[] expdigs21 = { 0, 21264, 53708, 60962, 25043, 64008, 31200, 50906, 9831, 56185, 43877, 36378, 50952, 131 };

  static final int[] expdigs22 = { 0, 50020, 25440, 60247, 44814, 39961, 6865, 26068, 34832, 9081, 17478, 44928, 20825, 1 };

  static final int[] expdigs23 = { 0, 0, 52929, 10084, 25506, 6346, 61348, 31525, 52689, 61296, 27615, 15903, 40426, 863 };

  static final int[] expdigs24 = { 0, 16384, 24122, 53840, 43508, 13170, 51076, 37670, 58198, 31414, 57292, 61762, 41691, 8 };

  static final int[] expdigs25 = { 0, 0, 4096, 29077, 42481, 30581, 10617, 59493, 46251, 1892, 5557, 4505, 52391, 5659 };

  static final int[] expdigs26 = { 0, 0, 58368, 11431, 1080, 29797, 47947, 36639, 42405, 50481, 29546, 9875, 39190, 56 };

  static final int[] expdigs27 = { 0, 0, 0, 57600, 63028, 53094, 12749, 18174, 21993, 48265, 14922, 59933, 4030, 37092 };

  static final int[] expdigs28 = { 0, 0, 0, 576, 1941, 35265, 9302, 42780, 50682, 28007, 29640, 28124, 60333, 370 };

  static final int[] expdigs29 = { 0, 0, 0, 5904, 8539, 12149, 36793, 43681, 12958, 60573, 21267, 35015, 46478, 3 };

  static final int[] expdigs30 = { 0, 0, 0, 0, 7268, 50548, 47962, 3644, 22719, 26999, 41893, 7421, 56711, 2430 };

  static final int[] expdigs31 = { 0, 0, 0, 0, 7937, 49002, 60772, 28216, 38893, 55975, 63988, 59711, 20227, 24 };

  static final int[] expdigs32 = { 0, 0, 0, 16384, 38090, 63404, 55657, 8801, 62648, 13666, 57656, 60234, 15930 };

  static final int[] expdigs33 = { 0, 0, 0, 4096, 37081, 37989, 16940, 55138, 17665, 39458, 9751, 20263, 159 };

  static final int[] expdigs34 = { 0, 0, 0, 58368, 35104, 16108, 61773, 14313, 30323, 54789, 57113, 38868, 1 };

  static final int[] expdigs35 = { 0, 0, 0, 8448, 18701, 29652, 51080, 65023, 27172, 37903, 3192, 1044 };

  static final int[] expdigs36 = { 0, 0, 0, 37440, 63101, 2917, 39177, 50457, 25830, 50186, 28867, 10 };

  static final int[] expdigs37 = { 0, 0, 0, 56080, 45850, 37384, 3668, 12301, 38269, 18196, 6842 };

  static final int[] expdigs38 = { 0, 0, 0, 46436, 13565, 50181, 34770, 37478, 5625, 27707, 68 };

  static final int[] expdigs39 = { 0, 0, 0, 32577, 45355, 38512, 38358, 3651, 36101, 44841 };

  static final int[] expdigs40 = { 0, 0, 16384, 28506, 5696, 56746, 15456, 50499, 27230, 448 };

  static final int[] expdigs41 = { 0, 0, 4096, 285, 9232, 58239, 57170, 38515, 31729, 4 };

  static final int[] expdigs42 = { 0, 0, 58368, 41945, 57108, 12378, 28752, 48226, 2938 };

  static final int[] expdigs43 = { 0, 0, 24832, 47605, 49067, 23716, 61891, 25385, 29 };

  static final int[] expdigs44 = { 0, 0, 8768, 2442, 50298, 23174, 19624, 19259 };

  static final int[] expdigs45 = { 0, 0, 40720, 45899, 1813, 31689, 38862, 192 };

  static final int[] expdigs46 = { 0, 0, 36452, 14221, 34752, 48813, 60681, 1 };

  static final int[] expdigs47 = { 0, 0, 61313, 34220, 16731, 11629, 1262 };

  static final int[] expdigs48 = { 0, 16384, 60906, 18036, 40144, 40748, 12 };

  static final int[] expdigs49 = { 0, 4096, 609, 15909, 52830, 8271 };

  static final int[] expdigs50 = { 0, 58368, 3282, 56520, 47058, 82 };

  static final int[] expdigs51 = { 0, 41216, 52461, 7118, 54210 };

  static final int[] expdigs52 = { 0, 45632, 51642, 6624, 542 };

  static final int[] expdigs53 = { 0, 25360, 24109, 27591, 5 };

  static final int[] expdigs54 = { 0, 42852, 46771, 3552 };

  static final int[] expdigs55 = { 0, 28609, 34546, 35 };

  static final int[] expdigs56 = { 16384, 4218, 23283 };

  static final int[] expdigs57 = { 4096, 54437, 232 };

  static final int[] expdigs58 = { 58368, 21515, 2 };

  static final int[] expdigs59 = { 57600, 1525 };

  static final int[] expdigs60 = { 16960, 15 };

  static final int[] expdigs61 = { 10000 };

  static final int[] expdigs62 = { 100 };

  static final int[] expdigs63 = { 1 };

  static final int[] expdigs64 = { 36700, 62914, 23592, 49807, 10485, 36700, 62914, 23592, 49807, 10485, 36700, 62914, 23592, 655 };

  static final int[] expdigs65 = { 14784, 18979, 33659, 19503, 2726, 9542, 629, 2202, 40475, 10590, 4299, 47815, 36280, 6 };

  static final int[] expdigs66 = { 16332, 9978, 33613, 31138, 35584, 64252, 13857, 14424, 62281, 46279, 36150, 46573, 63392, 4294 };

  static final int[] expdigs67 = { 6716, 24348, 22618, 23904, 21327, 3919, 44703, 19149, 28803, 48959, 6259, 50273, 62237, 42 };

  static final int[] expdigs68 = { 8471, 23660, 38254, 26440, 33662, 38879, 9869, 11588, 41479, 23225, 60127, 24310, 32615, 28147 };

  static final int[] expdigs69 = { 13191, 6790, 63297, 30410, 12788, 42987, 23691, 28296, 32527, 38898, 41233, 4830, 31128, 281 };

  static final int[] expdigs70 = { 4064, 53152, 62236, 29139, 46658, 12881, 31694, 4870, 19986, 24637, 9587, 28884, 53395, 2 };

  static final int[] expdigs71 = { 26266, 10526, 16260, 55017, 35680, 40443, 19789, 17356, 30195, 55905, 28426, 63010, 44197, 1844 };

  static final int[] expdigs72 = { 38273, 7969, 37518, 26764, 23294, 63974, 18547, 17868, 24550, 41191, 17323, 53714, 29277, 18 };

  static final int[] expdigs73 = { 16739, 37738, 38090, 26589, 43521, 1543, 15713, 10671, 11975, 41533, 18106, 9348, 16921, 12089 };

  static final int[] expdigs74 = { 14585, 61981, 58707, 16649, 25994, 39992, 28337, 17801, 37475, 22697, 31638, 16477, 58496, 120 };

  static final int[] expdigs75 = { 58472, 2585, 40564, 27691, 44824, 27269, 58610, 54572, 35108, 30373, 35050, 10650, 13692, 1 };

  static final int[] expdigs76 = { 50392, 58911, 41968, 49557, 29112, 29939, 43526, 63500, 55595, 27220, 25207, 38361, 18456, 792 };

  static final int[] expdigs77 = { 26062, 32046, 3696, 45060, 46821, 40931, 50242, 60272, 24148, 20588, 6150, 44948, 60477, 7 };

  static final int[] expdigs78 = { 12430, 30407, 320, 41980, 58777, 41755, 41041, 13609, 45167, 13348, 40838, 60354, 19454, 5192 };

  static final int[] expdigs79 = { 30926, 26518, 13110, 43018, 54982, 48258, 24658, 15209, 63366, 11929, 20069, 43857, 60487, 51 };

  static final int[] expdigs80 = { 51263, 54048, 48761, 48627, 30576, 49046, 4414, 61195, 61755, 48474, 19124, 55906, 15511, 34028 };

  static final int[] expdigs81 = { 39834, 11681, 47018, 3107, 64531, 54229, 41331, 41899, 51735, 42427, 59173, 13010, 18505, 340 };

  static final int[] expdigs82 = { 27268, 6670, 31272, 9861, 45865, 10372, 12865, 62678, 23454, 35158, 20252, 29621, 26399, 3 };

  static final int[] expdigs83 = { 57738, 46147, 66, 48154, 11239, 21430, 55809, 46003, 15044, 25138, 52780, 48043, 4883, 2230 };

  static final int[] expdigs84 = { 20893, 62065, 64225, 52254, 59094, 55919, 60195, 5702, 48647, 50058, 7736, 41768, 19709, 22 };

  static final int[] expdigs85 = { 37714, 32321, 45840, 36031, 33290, 47121, 5146, 28127, 9887, 25390, 52929, 2698, 1073, 14615 };

  static final int[] expdigs86 = { 35111, 8187, 18153, 56721, 40309, 59453, 51824, 4868, 45974, 3530, 43783, 8546, 9841, 146 };

  static final int[] expdigs87 = { 23288, 61030, 42779, 19572, 29894, 47780, 45082, 32816, 43713, 33458, 25341, 63655, 30244, 1 };

  static final int[] expdigs88 = { 58138, 33000, 62869, 37127, 61799, 298, 46353, 5693, 63898, 62040, 989, 23191, 53065, 957 };

  static final int[] expdigs89 = { 42524, 32442, 36673, 15444, 22900, 658, 61412, 32824, 21610, 64190, 1975, 11373, 37886, 9 };

  static final int[] expdigs90 = { 26492, 4357, 32437, 10852, 34233, 53968, 55056, 34692, 64553, 38226, 41929, 21646, 6667, 6277 };

  static final int[] expdigs91 = { 61213, 698, 16053, 50571, 2963, 50347, 13657, 48188, 46520, 19387, 33187, 25775, 50529, 62 };

  static final int[] expdigs92 = { 42864, 54351, 45226, 20476, 23443, 17724, 3780, 44701, 52910, 23402, 28374, 46862, 40234, 41137 };

  static final int[] expdigs93 = { 23366, 62147, 58123, 44113, 55284, 39498, 3314, 9622, 9704, 27759, 25187, 43722, 24650, 411 };

  static final int[] expdigs94 = { 38899, 44530, 19586, 37141, 1863, 9570, 32801, 31553, 51870, 62536, 51369, 30583, 7455, 4 };

  static final int[] expdigs95 = { 10421, 4321, 43699, 3472, 65252, 17057, 13858, 29819, 14733, 21490, 40602, 31315, 65186, 2695 };

  static final int[] expdigs96 = { 6002, 54438, 29272, 34113, 17036, 25074, 36183, 953, 25051, 12011, 20722, 4245, 62911, 26 };

  static final int[] expdigs97 = { 14718, 45935, 8408, 42891, 21312, 56531, 44159, 45581, 20325, 36295, 35509, 24455, 30844, 17668 };

  static final int[] expdigs98 = { 54542, 45023, 23021, 3050, 31015, 20881, 50904, 40432, 33626, 14125, 44264, 60537, 44872, 176 };

  static final int[] expdigs99 = { 60183, 8969, 14648, 17725, 11451, 50016, 34587, 46279, 19341, 42084, 16826, 5848, 50256, 1 };

  static final int[] expdigs100 = { 64999, 53685, 60382, 19151, 25736, 5357, 31302, 23283, 14225, 52622, 56781, 39489, 60351, 1157 };

  static final int[] expdigs101 = { 1305, 4469, 39270, 18541, 63827, 59035, 54707, 16616, 32910, 48367, 64137, 2360, 37959, 11 };

  static final int[] expdigs102 = { 45449, 32125, 19705, 56098, 51958, 5225, 18285, 13654, 9341, 25888, 50946, 26855, 36068, 7588 };

  static final int[] expdigs103 = { 27324, 53405, 43450, 25464, 3796, 3329, 46058, 53220, 26307, 53998, 33932, 23861, 58032, 75 };

  static final int[] expdigs104 = { 63080, 50735, 1844, 21406, 57926, 63607, 24936, 52889, 23469, 64488, 539, 8859, 21210, 49732 };

  static final int[] expdigs105 = { 62890, 39828, 3950, 32982, 39245, 21607, 40226, 50991, 18584, 10475, 59643, 40720, 21183, 497 };

  static final int[] expdigs106 = { 37329, 64623, 11835, 985, 46923, 48712, 28582, 21481, 28366, 41392, 13703, 49559, 63781, 4 };

  static final int[] expdigs107 = { 3316, 60011, 41933, 47959, 54404, 39790, 12283, 941, 46090, 42226, 18108, 38803, 16879, 3259 };

  static final int[] expdigs108 = { 46563, 56305, 5006, 45044, 49040, 12849, 778, 6563, 46336, 3043, 7390, 2354, 38835, 32 };

  static final int[] expdigs109 = { 28653, 3742, 33331, 2671, 39772, 29981, 56489, 1973, 26280, 26022, 56391, 56434, 57039, 21359 };

  static final int[] expdigs110 = { 9461, 17732, 7542, 26241, 8917, 24548, 61513, 13126, 59245, 41547, 1874, 41852, 39236, 213 };

  static final int[] expdigs111 = { 36794, 22459, 63645, 14024, 42032, 53329, 25518, 11272, 18287, 20076, 62933, 3039, 8912, 2 };

  static final int[] expdigs112 = { 14926, 15441, 32337, 42579, 26354, 35154, 22815, 36955, 12564, 8047, 856, 41917, 55080, 1399 };

  static final int[] expdigs113 = { 8668, 50617, 10153, 17465, 1574, 28532, 15301, 58041, 38791, 60373, 663, 29255, 65431, 13 };

  static final int[] expdigs114 = { 21589, 32199, 24754, 45321, 9349, 26230, 35019, 37508, 20896, 42986, 31405, 12458, 65173, 9173 };

  static final int[] expdigs115 = { 46746, 1632, 61196, 50915, 64318, 41549, 2971, 23968, 59191, 58756, 61917, 779, 48493, 91 };

  static final int[] expdigs116 = { 1609, 63382, 15744, 15685, 51627, 56348, 33838, 52458, 44148, 11077, 56293, 41906, 45227, 60122 };

  static final int[] expdigs117 = { 19676, 45198, 6055, 38823, 8380, 49060, 17377, 58196, 43039, 21737, 59545, 12870, 14870, 601 };

  static final int[] expdigs118 = { 4128, 2418, 28241, 13495, 26298, 3767, 31631, 5169, 8950, 27087, 56956, 4060, 804, 6 };

  static final int[] expdigs119 = { 39930, 40673, 19029, 54677, 38145, 23200, 41325, 24564, 24955, 54484, 23863, 52998, 13147, 3940 };

  static final int[] expdigs120 = { 3676, 24655, 34924, 27416, 23974, 887, 10899, 4833, 21221, 28725, 19899, 57546, 26345, 39 };

  static final int[] expdigs121 = { 28904, 41324, 18596, 42292, 12070, 52013, 30810, 61057, 55753, 32324, 38953, 6752, 32688, 25822 };

  static final int[] expdigs122 = { 42232, 26627, 2807, 27948, 50583, 49016, 32420, 64180, 3178, 3600, 21361, 52496, 14744, 258 };

  static final int[] expdigs123 = { 2388, 59904, 28863, 7488, 31963, 8354, 47510, 15059, 2653, 58363, 31670, 21496, 38158, 2 };

  static final int[] expdigs124 = { 50070, 5266, 26158, 10774, 15148, 6873, 30230, 33898, 63720, 51799, 4515, 50124, 19875, 1692 };

  static final int[] expdigs125 = { 54240, 3984, 12058, 2729, 13914, 11865, 38313, 39660, 10467, 20834, 36745, 57517, 60491, 16 };

  static final int[] expdigs126 = { 5387, 58214, 9214, 13883, 14445, 34873, 21745, 13490, 23334, 25008, 58535, 19372, 44484, 11090 };

  static final int[] expdigs127 = { 27578, 64807, 12543, 794, 13907, 61297, 12013, 64360, 15961, 20566, 24178, 15922, 59427, 110 };

  static final int[] expdigs128 = { 49427, 41935, 46000, 59645, 45358, 51075, 15848, 32756, 38170, 14623, 35631, 57175, 7147, 1 };

  static final int[] expdigs129 = { 33941, 39160, 55469, 45679, 22878, 60091, 37210, 18508, 1638, 57398, 65026, 41643, 54966, 726 };

  static final int[] expdigs130 = { 60632, 24639, 41842, 62060, 20544, 59583, 52800, 1495, 48513, 43827, 10480, 1727, 17589, 7 };

  static final int[] expdigs131 = { 5590, 60244, 53985, 26632, 53049, 33628, 58267, 54922, 21641, 62744, 58109, 2070, 26887, 4763 };

  static final int[] expdigs132 = { 62970, 37957, 34618, 29757, 24123, 2302, 17622, 58876, 44780, 6525, 33349, 36065, 41556, 47 };

  static final int[] expdigs133 = { 1615, 24878, 20040, 11487, 23235, 27766, 59005, 57847, 60881, 11588, 63635, 61281, 31817, 31217 };

  static final int[] expdigs134 = { 14434, 2870, 65081, 44023, 40864, 40254, 47120, 6476, 32066, 23053, 17020, 19618, 11459, 312 };

  static final int[] expdigs135 = { 43398, 40005, 36695, 8304, 12205, 16131, 42414, 38075, 63890, 2851, 61774, 59833, 7978, 3 };

  static final int[] expdigs136 = { 56426, 22060, 15473, 31824, 19088, 38788, 64386, 12875, 35770, 65519, 11824, 19623, 56959, 2045 };

  static final int[] expdigs137 = { 16292, 32333, 10640, 47504, 29026, 30534, 23581, 6682, 10188, 24248, 44027, 51969, 30060, 20 };

  static final int[] expdigs138 = { 29432, 37518, 55373, 2727, 33243, 22572, 16689, 35625, 34145, 15830, 59880, 32552, 52948, 13407 };

  static final int[] expdigs139 = { 61898, 27244, 41841, 33450, 18682, 13988, 24415, 11497, 1652, 34237, 34677, 325, 5117, 134 };

  static final int[] expdigs140 = { 16347, 3549, 48915, 22616, 21158, 51913, 32356, 21086, 3293, 8862, 1002, 26873, 22333, 1 };

  static final int[] expdigs141 = { 25966, 63733, 28215, 31946, 40858, 58538, 11004, 6877, 6109, 3965, 35478, 37365, 45488, 878 };

  static final int[] expdigs142 = { 45479, 34060, 17321, 19980, 1719, 16314, 29601, 8588, 58388, 22321, 14117, 63288, 51572, 8 };

  static final int[] expdigs143 = { 46861, 47640, 11481, 23766, 46730, 53756, 8682, 60589, 42028, 27453, 29714, 31598, 39954, 5758 };

  static final int[] expdigs144 = { 29304, 58803, 51232, 27762, 60760, 17576, 19092, 26820, 11561, 48771, 6850, 27841, 38410, 57 };

  static final int[] expdigs145 = { 2916, 49445, 34666, 46387, 18627, 58279, 60468, 190, 3545, 51889, 51605, 47909, 40910, 37739 };

  static final int[] expdigs146 = { 19034, 62098, 15419, 33887, 38852, 53011, 28129, 37357, 11176, 48360, 9035, 9654, 25968, 377 };

  static final int[] expdigs147 = { 25094, 10451, 7363, 55389, 57404, 27399, 11422, 39695, 28947, 12935, 61694, 26310, 50722, 3 };

  static final int[][] expdigstable = { expdigs0, expdigs1, expdigs2, expdigs3, expdigs4, expdigs5, expdigs6, expdigs7, expdigs8, expdigs9, expdigs10, expdigs11, expdigs12, expdigs13, expdigs14, expdigs15, expdigs16, expdigs17, expdigs18, expdigs19, expdigs20, expdigs21, expdigs22, expdigs23, expdigs24, expdigs25, expdigs26, expdigs27, expdigs28, expdigs29, expdigs30, expdigs31, expdigs32, expdigs33, expdigs34, expdigs35, expdigs36, expdigs37, expdigs38, expdigs39, expdigs40, expdigs41, expdigs42, expdigs43, expdigs44, expdigs45, expdigs46, expdigs47, expdigs48, expdigs49, expdigs50, expdigs51, expdigs52, expdigs53, expdigs54, expdigs55, expdigs56, expdigs57, expdigs58, expdigs59, expdigs60, expdigs61, expdigs62, expdigs63, expdigs64, expdigs65, expdigs66, expdigs67, expdigs68, expdigs69, expdigs70, expdigs71, expdigs72, expdigs73, expdigs74, expdigs75, expdigs76, expdigs77, expdigs78, expdigs79, expdigs80, expdigs81, expdigs82, expdigs83, expdigs84, expdigs85, expdigs86, expdigs87, expdigs88, expdigs89, expdigs90, expdigs91, expdigs92, expdigs93, expdigs94, expdigs95, expdigs96, expdigs97, expdigs98, expdigs99, expdigs100, expdigs101, expdigs102, expdigs103, expdigs104, expdigs105, expdigs106, expdigs107, expdigs108, expdigs109, expdigs110, expdigs111, expdigs112, expdigs113, expdigs114, expdigs115, expdigs116, expdigs117, expdigs118, expdigs119, expdigs120, expdigs121, expdigs122, expdigs123, expdigs124, expdigs125, expdigs126, expdigs127, expdigs128, expdigs129, expdigs130, expdigs131, expdigs132, expdigs133, expdigs134, expdigs135, expdigs136, expdigs137, expdigs138, expdigs139, expdigs140, expdigs141, expdigs142, expdigs143, expdigs144, expdigs145, expdigs146, expdigs147 };

  static final int[] nexpdigstable = { 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 13, 13, 13, 12, 12, 11, 11, 10, 10, 10, 9, 9, 8, 8, 8, 7, 7, 6, 6, 5, 5, 5, 4, 4, 3, 3, 3, 2, 2, 1, 1, 1, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14 };

  static final int[] binexpstable = { 90, 89, 89, 88, 88, 88, 87, 87, 86, 86, 86, 85, 85, 84, 84, 83, 83, 83, 82, 82, 81, 81, 81, 80, 80, 79, 79, 78, 78, 78, 77, 77, 76, 76, 76, 75, 75, 74, 74, 73, 73, 73, 72, 72, 71, 71, 71, 70, 70, 69, 69, 68, 68, 68, 67, 67, 66, 66, 66, 65, 65, 64, 64, 64, 63, 63, 62, 62, 61, 61, 61, 60, 60, 59, 59, 59, 58, 58, 57, 57, 56, 56, 56, 55, 55, 54, 54, 54, 53, 53, 52, 52, 51, 51, 51, 50, 50, 49, 49, 49, 48, 48, 47, 47, 46, 46, 46, 45, 45, 44, 44, 44, 43, 43, 42, 42, 41, 41, 41, 40, 40, 39, 39, 39, 38, 38, 37, 37, 37, 36, 36, 35, 35, 34, 34, 34, 33, 33, 32, 32, 32, 31, 31, 30, 30, 29, 29, 29 };

  private static final String _Copyright_2007_Oracle_All_Rights_Reserved_ = null;
  public static final String BUILD_DATE = "Sat_Aug_14_12:18:34_PDT_2010";
  public static final boolean TRACE = false;

  void init(OracleStatement paramOracleStatement, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean)
    throws SQLException
  {
    init(paramOracleStatement, 6, 6, paramShort, paramBoolean);
    initForDataAccess(paramInt2, paramInt1, null);
  }

  void init(OracleStatement paramOracleStatement, int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, short paramShort)
    throws SQLException
  {
    init(paramOracleStatement, 6, 6, paramShort, false);
    initForDescribe(paramInt1, paramInt2, paramBoolean, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramShort, null);

    initForDataAccess(0, paramInt2, null);
  }

  void initForDataAccess(int paramInt1, int paramInt2, String paramString)
    throws SQLException
  {
    if (paramInt1 != 0) {
      this.externalType = paramInt1;
    }
    this.internalTypeMaxLength = 21;

    if ((paramInt2 > 0) && (paramInt2 < this.internalTypeMaxLength)) {
      this.internalTypeMaxLength = paramInt2;
    }
    this.byteLength = (this.internalTypeMaxLength + 1);
  }

  int getInt(int paramInt)
    throws SQLException
  {
    int i = 0;
    Object localObject;
    if (this.rowSpaceIndicator == null)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
    {
      localObject = this.rowSpaceByte;
      int j = this.columnIndex + this.byteLength * paramInt + 1;
      int k = localObject[(j - 1)];
      int m = localObject[j];

      int n = 0;
      int i1;
      int i2;
      int i3;
      int i4;
      int i7;
      if ((m & 0xFFFFFF80) != 0)
      {
        i1 = (byte)((m & 0xFFFFFF7F) - 65);
        i2 = (byte)(k - 1);

        i3 = i2 > i1 + 1 ? i1 + 2 : i2 + 1;
        i4 = i3 + j;

        if (i1 >= 4)
        {
          if (i1 > 4)
          {
            throwOverflow();
          }
          long l1 = 0L;

          if (i3 > 1)
          {
            l1 = localObject[(j + 1)] - 1;

            for (i7 = 2 + j; i7 < i4; i7++) {
              l1 = l1 * 100L + (localObject[i7] - 1);
            }

          }

          for (i7 = i1 - i2; i7 >= 0; i7--) {
            l1 *= 100L;
          }
          if (l1 > 2147483647L) {
            throwOverflow();
          }
          n = (int)l1;
        }
        else
        {
          if (i3 > 1)
          {
            n = localObject[(j + 1)] - 1;

            for (i5 = 2 + j; i5 < i4; i5++) {
              n = n * 100 + (localObject[i5] - 1);
            }

          }

          for (int i5 = i1 - i2; i5 >= 0; i5--) {
            n *= 100;
          }

        }

      }
      else
      {
        i1 = (byte)(((m ^ 0xFFFFFFFF) & 0xFFFFFF7F) - 65);
        i2 = (byte)(k - 1);

        if ((i2 != 20) || (localObject[(j + i2)] == 102)) {
          i2 = (byte)(i2 - 1);
        }
        i3 = i2 > i1 + 1 ? i1 + 2 : i2 + 1;
        i4 = i3 + j;

        if (i1 >= 4)
        {
          if (i1 > 4)
          {
            throwOverflow();
          }

          long l2 = 0L;

          if (i3 > 1)
          {
            l2 = 101 - localObject[(j + 1)];

            for (i7 = 2 + j; i7 < i4; i7++) {
              l2 = l2 * 100L + (101 - localObject[i7]);
            }

          }

          for (i7 = i1 - i2; i7 >= 0; i7--) {
            l2 *= 100L;
          }
          l2 = -l2;

          if (l2 < -2147483648L) {
            throwOverflow();
          }
          n = (int)l2;
        }
        else
        {
          if (i3 > 1)
          {
            n = 101 - localObject[(j + 1)];

            for (i6 = 2 + j; i6 < i4; i6++) {
              n = n * 100 + (101 - localObject[i6]);
            }

          }

          for (int i6 = i1 - i2; i6 >= 0; i6--) {
            n *= 100;
          }
          n = -n;
        }
      }

      i = n;
    }

    return i;
  }

  boolean getBoolean(int paramInt)
    throws SQLException
  {
    boolean bool = false;
    Object localObject;
    if (this.rowSpaceIndicator == null)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
    {
      localObject = this.rowSpaceByte;
      int i = this.columnIndex + this.byteLength * paramInt + 1;
      int j = localObject[(i - 1)];

      bool = (j != 1) || (localObject[i] != -128);
    }

    return bool;
  }

  short getShort(int paramInt)
    throws SQLException
  {
    short s = 0;
    Object localObject;
    if (this.rowSpaceIndicator == null)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
    {
      localObject = this.rowSpaceByte;
      int i = this.columnIndex + this.byteLength * paramInt + 1;
      int j = localObject[(i - 1)];

      int k = localObject[i];

      int m = 0;
      int n;
      int i1;
      int i2;
      int i3;
      int i4;
      if ((k & 0xFFFFFF80) != 0)
      {
        n = (byte)((k & 0xFFFFFF7F) - 65);

        if (n > 2)
        {
          throwOverflow();
        }
        i1 = (byte)(j - 1);

        i2 = i1 > n + 1 ? n + 2 : i1 + 1;
        i3 = i2 + i;

        if (i2 > 1)
        {
          m = localObject[(i + 1)] - 1;

          for (i4 = 2 + i; i4 < i3; i4++) {
            m = m * 100 + (localObject[i4] - 1);
          }

        }

        for (i4 = n - i1; i4 >= 0; i4--) {
          m *= 100;
        }
        if ((n == 2) && 
          (m > 32767)) {
          throwOverflow();
        }

      }
      else
      {
        n = (byte)(((k ^ 0xFFFFFFFF) & 0xFFFFFF7F) - 65);

        if (n > 2)
        {
          throwOverflow();
        }
        i1 = (byte)(j - 1);

        if ((i1 != 20) || (localObject[(i + i1)] == 102)) {
          i1 = (byte)(i1 - 1);
        }
        i2 = i1 > n + 1 ? n + 2 : i1 + 1;
        i3 = i2 + i;

        if (i2 > 1)
        {
          m = 101 - localObject[(i + 1)];

          for (i4 = 2 + i; i4 < i3; i4++) {
            m = m * 100 + (101 - localObject[i4]);
          }

        }

        for (i4 = n - i1; i4 >= 0; i4--) {
          m *= 100;
        }
        m = -m;

        if ((n == 2) && 
          (m < -32768)) {
          throwOverflow();
        }
      }
      s = (short)m;
    }

    return s;
  }

  byte getByte(int paramInt)
    throws SQLException
  {
    byte b = 0;
    Object localObject;
    if (this.rowSpaceIndicator == null)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
    {
      localObject = this.rowSpaceByte;
      int i = this.columnIndex + this.byteLength * paramInt + 1;
      int j = localObject[(i - 1)];

      int k = localObject[i];

      int m = 0;
      int n;
      int i1;
      if ((k & 0xFFFFFF80) != 0)
      {
        n = (byte)((k & 0xFFFFFF7F) - 65);

        if (n > 1)
        {
          throwOverflow();
        }
        i1 = (byte)(j - 1);

        if (i1 > n + 1)
        {
          switch (n)
          {
          default:
            break;
          case -1:
            break;
          case 0:
            m = localObject[(i + 1)] - 1;

            break;
          case 1:
            m = (localObject[(i + 1)] - 1) * 100 + (localObject[(i + 2)] - 1);

            if (m <= 127) break;
            throwOverflow(); break;
          }

        }
        else if (i1 == 1)
        {
          if (n == 1)
          {
            m = (localObject[(i + 1)] - 1) * 100;

            if (m > 127)
              throwOverflow();
          }
          else {
            m = localObject[(i + 1)] - 1;
          }
        } else if (i1 == 2)
        {
          m = (localObject[(i + 1)] - 1) * 100 + (localObject[(i + 2)] - 1);

          if (m > 127) {
            throwOverflow();
          }

        }

      }
      else
      {
        n = (byte)(((k ^ 0xFFFFFFFF) & 0xFFFFFF7F) - 65);

        if (n > 1)
        {
          throwOverflow();
        }
        i1 = (byte)(j - 1);

        if ((i1 != 20) || (localObject[(i + i1)] == 102)) {
          i1 = (byte)(i1 - 1);
        }
        if (i1 > n + 1)
        {
          switch (n)
          {
          default:
            break;
          case -1:
            break;
          case 0:
            m = -(101 - localObject[(i + 1)]);

            break;
          case 1:
            m = -((101 - localObject[(i + 1)]) * 100 + (101 - localObject[(i + 2)]));

            if (m >= -128) break;
            throwOverflow(); break;
          }

        }
        else if (i1 == 1)
        {
          if (n == 1)
          {
            m = -(101 - localObject[(i + 1)]) * 100;

            if (m < -128)
              throwOverflow();
          }
          else {
            m = -(101 - localObject[(i + 1)]);
          }
        } else if (i1 == 2)
        {
          m = -((101 - localObject[(i + 1)]) * 100 + (101 - localObject[(i + 2)]));

          if (m < -128) {
            throwOverflow();
          }
        }
      }
      b = (byte)m;
    }

    return b;
  }

  long getLong(int paramInt)
    throws SQLException
  {
    long l1 = 0L;
    Object localObject;
    if (this.rowSpaceIndicator == null)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
    {
      localObject = this.rowSpaceByte;
      int i = this.columnIndex + this.byteLength * paramInt + 1;
      int j = localObject[(i - 1)];

      int k = localObject[i];
      long l2 = 0L;
      int m;
      int i1;
      int i2;
      int i3;
      int i4;
      int n;
      if ((k & 0xFFFFFF80) != 0)
      {
        if ((k == -128) && (j == 1)) {
          return 0L;
        }
        m = (byte)((k & 0xFFFFFF7F) - 65);

        if (m > 9)
        {
          throwOverflow();
        }
        if (m == 9)
        {
          i1 = 1;
          i2 = j;

          if (j > 11) {
            i2 = 11;
          }
          while (i1 < i2)
          {
            i3 = localObject[(i + i1)] & 0xFF;
            i4 = MAX_LONG[i1];

            if (i3 != i4)
            {
              if (i3 < i4) {
                break;
              }
              throwOverflow();
            }

            i1++;
          }

          if ((i1 == i2) && (j > 11)) {
            throwOverflow();
          }
        }
        n = (byte)(j - 1);

        i1 = n > m + 1 ? m + 2 : n + 1;
        i2 = i1 + i;

        if (i1 > 1)
        {
          l2 = localObject[(i + 1)] - 1;

          for (i3 = 2 + i; i3 < i2; i3++) {
            l2 = l2 * 100L + (localObject[i3] - 1);
          }

        }

        for (i3 = m - n; i3 >= 0; i3--) {
          l2 *= 100L;
        }

      }
      else
      {
        m = (byte)(((k ^ 0xFFFFFFFF) & 0xFFFFFF7F) - 65);

        if (m > 9)
        {
          throwOverflow();
        }
        if (m == 9)
        {
          i1 = 1;
          i2 = j;

          if (j > 12) {
            i2 = 12;
          }
          while (i1 < i2)
          {
            i3 = localObject[(i + i1)] & 0xFF;
            i4 = MIN_LONG[i1];

            if (i3 != i4)
            {
              if (i3 > i4) {
                break;
              }
              throwOverflow();
            }

            i1++;
          }

          if ((i1 == i2) && (j < 12)) {
            throwOverflow();
          }
        }
        n = (byte)(j - 1);

        if ((n != 20) || (localObject[(i + n)] == 102)) {
          n = (byte)(n - 1);
        }
        i1 = n > m + 1 ? m + 2 : n + 1;
        i2 = i1 + i;

        if (i1 > 1)
        {
          l2 = 101 - localObject[(i + 1)];

          for (i3 = 2 + i; i3 < i2; i3++) {
            l2 = l2 * 100L + (101 - localObject[i3]);
          }

        }

        for (i3 = m - n; i3 >= 0; i3--) {
          l2 *= 100L;
        }
        l2 = -l2;
      }

      l1 = l2;
    }

    return l1;
  }

  float getFloat(int paramInt)
    throws SQLException
  {
    float f = 0.0F;
    Object localObject;
    if (this.rowSpaceIndicator == null)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
    {
      localObject = this.rowSpaceByte;
      int i = this.columnIndex + this.byteLength * paramInt + 1;
      int j = localObject[(i - 1)];

      int k = localObject[i];

      double d = 0.0D;

      int i1 = i + 1;
      int m;
      int i2;
      int n;
      if ((k & 0xFFFFFF80) != 0)
      {
        if ((k == -128) && (j == 1)) {
          return 0.0F;
        }
        if ((j == 2) && (k == -1) && (localObject[(i + 1)] == 101))
        {
          return (1.0F / 1.0F);
        }
        m = (byte)((k & 0xFFFFFF7F) - 65);

        i2 = j - 1;

        while ((localObject[i1] == 1) && (i2 > 0))
        {
          i1++;
          i2--;
          m = (byte)(m - 1);
        }

        n = (int)(127.0D - m);

        switch (i2)
        {
        case 1:
          d = (localObject[i1] - 1) * factorTable[n];

          break;
        case 2:
          d = ((localObject[i1] - 1) * 100 + (localObject[(i1 + 1)] - 1)) * factorTable[(n + 1)];

          break;
        case 3:
          d = ((localObject[i1] - 1) * 10000 + (localObject[(i1 + 1)] - 1) * 100 + (localObject[(i1 + 2)] - 1)) * factorTable[(n + 2)];

          break;
        case 4:
          d = ((localObject[i1] - 1) * 1000000 + (localObject[(i1 + 1)] - 1) * 10000 + (localObject[(i1 + 2)] - 1) * 100 + (localObject[(i1 + 3)] - 1)) * factorTable[(n + 3)];

          break;
        case 5:
          d = ((localObject[(i1 + 1)] - 1) * 1000000 + (localObject[(i1 + 2)] - 1) * 10000 + (localObject[(i1 + 3)] - 1) * 100 + (localObject[(i1 + 4)] - 1)) * factorTable[(n + 4)] + (localObject[i1] - 1) * factorTable[n];

          break;
        case 6:
          d = ((localObject[(i1 + 2)] - 1) * 1000000 + (localObject[(i1 + 3)] - 1) * 10000 + (localObject[(i1 + 4)] - 1) * 100 + (localObject[(i1 + 5)] - 1)) * factorTable[(n + 5)] + ((localObject[i1] - 1) * 100 + (localObject[(i1 + 1)] - 1)) * factorTable[(n + 1)];

          break;
        default:
          d = ((localObject[(i1 + 3)] - 1) * 1000000 + (localObject[(i1 + 4)] - 1) * 10000 + (localObject[(i1 + 5)] - 1) * 100 + (localObject[(i1 + 6)] - 1)) * factorTable[(n + 6)] + ((localObject[i1] - 1) * 10000 + (localObject[(i1 + 1)] - 1) * 100 + (localObject[(i1 + 2)] - 1)) * factorTable[(n + 2)];

          break;
        }

      }
      else
      {
        if ((k == 0) && (j == 1)) {
          return (1.0F / -1.0F);
        }
        m = (byte)(((k ^ 0xFFFFFFFF) & 0xFFFFFF7F) - 65);

        i2 = j - 1;

        if ((i2 != 20) || (localObject[(i + i2)] == 102)) {
          i2--;
        }
        while ((localObject[i1] == 1) && (i2 > 0))
        {
          i1++;
          i2--;
          m = (byte)(m - 1);
        }

        n = (int)(127.0D - m);

        switch (i2)
        {
        case 1:
          d = -(101 - localObject[i1]) * factorTable[n];

          break;
        case 2:
          d = -((101 - localObject[i1]) * 100 + (101 - localObject[(i1 + 1)])) * factorTable[(n + 1)];

          break;
        case 3:
          d = -((101 - localObject[i1]) * 10000 + (101 - localObject[(i1 + 1)]) * 100 + (101 - localObject[(i1 + 2)])) * factorTable[(n + 2)];

          break;
        case 4:
          d = -((101 - localObject[i1]) * 1000000 + (101 - localObject[(i1 + 1)]) * 10000 + (101 - localObject[(i1 + 2)]) * 100 + (101 - localObject[(i1 + 3)])) * factorTable[(n + 3)];

          break;
        case 5:
          d = -(((101 - localObject[(i1 + 1)]) * 1000000 + (101 - localObject[(i1 + 2)]) * 10000 + (101 - localObject[(i1 + 3)]) * 100 + (101 - localObject[(i1 + 4)])) * factorTable[(n + 4)] + (101 - localObject[i1]) * factorTable[n]);

          break;
        case 6:
          d = -(((101 - localObject[(i1 + 2)]) * 1000000 + (101 - localObject[(i1 + 3)]) * 10000 + (101 - localObject[(i1 + 4)]) * 100 + (101 - localObject[(i1 + 5)])) * factorTable[(n + 5)] + ((101 - localObject[i1]) * 100 + (101 - localObject[(i1 + 1)])) * factorTable[(n + 1)]);

          break;
        default:
          d = -(((101 - localObject[(i1 + 3)]) * 1000000 + (101 - localObject[(i1 + 4)]) * 10000 + (101 - localObject[(i1 + 5)]) * 100 + (101 - localObject[(i1 + 6)])) * factorTable[(n + 6)] + ((101 - localObject[i1]) * 10000 + (101 - localObject[(i1 + 1)]) * 100 + (101 - localObject[(i1 + 2)])) * factorTable[(n + 2)]);
        }

      }

      f = (float)d;
    }

    return f;
  }

  double getDouble(int paramInt)
    throws SQLException
  {
    double d1 = 0.0D;
    Object localObject;
    if (this.rowSpaceIndicator == null)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
    {
      localObject = this.rowSpaceByte;
      int i = this.columnIndex + this.byteLength * paramInt + 1;
      int j = localObject[(i - 1)];

      int k = localObject[i];

      int n = i + 1;
      int i1 = j - 1;

      int i7 = 1;
      int m;
      int i6;
      int i4;
      if ((k & 0xFFFFFF80) != 0)
      {
        if ((k == -128) && (j == 1)) {
          return 0.0D;
        }
        if ((j == 2) && (k == -1) && (localObject[(i + 1)] == 101))
        {
          return (1.0D / 0.0D);
        }
        m = (byte)((k & 0xFFFFFF7F) - 65);

        i6 = (localObject[(n + i1 - 1)] - 1) % 10 == 0 ? 1 : 0;

        i4 = localObject[n] - 1;
      }
      else
      {
        i7 = 0;

        if ((k == 0) && (j == 1)) {
          return (-1.0D / 0.0D);
        }
        m = (byte)(((k ^ 0xFFFFFFFF) & 0xFFFFFF7F) - 65);

        if ((i1 != 20) || (localObject[(i + i1)] == 102)) {
          i1--;
        }
        i6 = (101 - localObject[(n + i1 - 1)]) % 10 == 0 ? 1 : 0;

        i4 = 101 - localObject[n];
      }

      int i5 = i1 << 1;

      if (i6 != 0) {
        i5--;
      }
      int i8 = (m + 1 << 1) - i5;

      if (i4 < 10)
        i5--;
      int i9;
      int i10;
      int i11;
      int i12;
      int i13;
      int i14;
      int i15;
      int i18;
      int i19;
      double d2;
      int i20;
      if ((i5 <= 15) && (((i8 >= 0) && (i8 <= 37 - i5)) || ((i8 < 0) && (i8 >= -22))))
      {
        i9 = 0;
        i10 = 0;
        i11 = 0;
        i12 = 0;
        i13 = 0;
        i14 = 0;
        i15 = 0;

        if (i7 != 0) {
          switch (i1)
          {
          default:
            i15 = localObject[(n + 7)] - 1;
          case 7:
            i14 = localObject[(n + 6)] - 1;
          case 6:
            i13 = localObject[(n + 5)] - 1;
          case 5:
            i12 = localObject[(n + 4)] - 1;
          case 4:
            i11 = localObject[(n + 3)] - 1;
          case 3:
            i10 = localObject[(n + 2)] - 1;
          case 2:
            i9 = localObject[(n + 1)] - 1;
          case 1:
          }
        }
        else
        {
          switch (i1)
          {
          default:
            i15 = 101 - localObject[(n + 7)];
          case 7:
            i14 = 101 - localObject[(n + 6)];
          case 6:
            i13 = 101 - localObject[(n + 5)];
          case 5:
            i12 = 101 - localObject[(n + 4)];
          case 4:
            i11 = 101 - localObject[(n + 3)];
          case 3:
            i10 = 101 - localObject[(n + 2)];
          case 2:
            i9 = 101 - localObject[(n + 1)];
          case 1:
          }
        }
        double d3;
        if (i6 != 0)
          switch (i1)
          {
          default:
            d3 = i4 / 10;

            break;
          case 2:
            d3 = i4 * 10 + i9 / 10;

            break;
          case 3:
            d3 = i4 * 1000 + i9 * 10 + i10 / 10;

            break;
          case 4:
            d3 = i4 * 100000 + i9 * 1000 + i10 * 10 + i11 / 10;

            break;
          case 5:
            d3 = i4 * 10000000 + i9 * 100000 + i10 * 1000 + i11 * 10 + i12 / 10;

            break;
          case 6:
            i18 = i9 * 10000000 + i10 * 100000 + i11 * 1000 + i12 * 10 + i13 / 10;

            d3 = i4 * 1000000000L + i18;

            break;
          case 7:
            i18 = i10 * 10000000 + i11 * 100000 + i12 * 1000 + i13 * 10 + i14 / 10;

            i19 = i4 * 100 + i9;
            d3 = i19 * 1000000000L + i18;

            break;
          case 8:
            i18 = i11 * 10000000 + i12 * 100000 + i13 * 1000 + i14 * 10 + i15 / 10;

            i19 = i4 * 10000 + i9 * 100 + i10;
            d3 = i19 * 1000000000L + i18;

            break;
          }
        else {
          switch (i1)
          {
          default:
            d3 = i4;

            break;
          case 2:
            d3 = i4 * 100 + i9;

            break;
          case 3:
            d3 = i4 * 10000 + i9 * 100 + i10;

            break;
          case 4:
            d3 = i4 * 1000000 + i9 * 10000 + i10 * 100 + i11;

            break;
          case 5:
            i18 = i9 * 1000000 + i10 * 10000 + i11 * 100 + i12;
            d3 = i4 * 100000000L + i18;

            break;
          case 6:
            i18 = i10 * 1000000 + i11 * 10000 + i12 * 100 + i13;
            i19 = i4 * 100 + i9;
            d3 = i19 * 100000000L + i18;

            break;
          case 7:
            i18 = i11 * 1000000 + i12 * 10000 + i13 * 100 + i14;
            i19 = i4 * 10000 + i9 * 100 + i10;
            d3 = i19 * 100000000L + i18;

            break;
          case 8:
            i18 = i12 * 1000000 + i13 * 10000 + i14 * 100 + i15;
            i19 = i4 * 1000000 + i9 * 10000 + i10 * 100 + i11;
            d3 = i19 * 100000000L + i18;
          }

        }

        if ((i8 == 0) || (d3 == 0.0D))
          d2 = d3;
        else if (i8 >= 0)
        {
          if (i8 <= 22)
          {
            d2 = d3 * small10pow[i8];
          }
          else
          {
            i20 = 15 - i5;

            d3 *= small10pow[i20];
            d2 = d3 * small10pow[(i8 - i20)];
          }

        }
        else
        {
          d2 = d3 / small10pow[(-i8)];
        }
      }
      else
      {
        i9 = 0;
        i10 = 0;
        i11 = 0;
        i12 = 0;
        i13 = 0;
        i14 = 0;
        i15 = 0;
        int i16 = 0;
        int i17 = 0;
        i18 = 0;

        int i24 = 0;
        int i25 = 0;
        int i26 = 0;
        int i27 = 0;
        int i28 = 0;

        int i32 = 0;
        int i33 = 0;
        int i2;
        if (i7 != 0)
        {
          if ((i1 & 0x1) != 0)
          {
            i2 = 2;
            i9 = i4;
          }
          else
          {
            i2 = 3;
            i9 = i4 * 100 + (localObject[(n + 1)] - 1);
          }

          for (; i2 < i1; i2 += 2)
          {
            i34 = (localObject[(n + i2 - 1)] - 1) * 100 + (localObject[(n + i2)] - 1) + i9 * 10000;

            switch (i18)
            {
            default:
              i9 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i10 * 10000;
              i10 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i11 * 10000;
              i11 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i12 * 10000;
              i12 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i13 * 10000;
              i13 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i14 * 10000;
              i14 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i15 * 10000;
              i15 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i16 * 10000;
              i16 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i17 * 10000;
              i17 = i34 & 0xFFFF;

              break;
            case 7:
              i9 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i10 * 10000;
              i10 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i11 * 10000;
              i11 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i12 * 10000;
              i12 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i13 * 10000;
              i13 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i14 * 10000;
              i14 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i15 * 10000;
              i15 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i16 * 10000;
              i16 = i34 & 0xFFFF;

              break;
            case 6:
              i9 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i10 * 10000;
              i10 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i11 * 10000;
              i11 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i12 * 10000;
              i12 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i13 * 10000;
              i13 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i14 * 10000;
              i14 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i15 * 10000;
              i15 = i34 & 0xFFFF;

              break;
            case 5:
              i9 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i10 * 10000;
              i10 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i11 * 10000;
              i11 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i12 * 10000;
              i12 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i13 * 10000;
              i13 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i14 * 10000;
              i14 = i34 & 0xFFFF;

              break;
            case 4:
              i9 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i10 * 10000;
              i10 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i11 * 10000;
              i11 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i12 * 10000;
              i12 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i13 * 10000;
              i13 = i34 & 0xFFFF;

              break;
            case 3:
              i9 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i10 * 10000;
              i10 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i11 * 10000;
              i11 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i12 * 10000;
              i12 = i34 & 0xFFFF;

              break;
            case 2:
              i9 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i10 * 10000;
              i10 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i11 * 10000;
              i11 = i34 & 0xFFFF;

              break;
            case 1:
              i9 = i34 & 0xFFFF;
              i34 = (i34 >> 16 & 0xFFFF) + i10 * 10000;
              i10 = i34 & 0xFFFF;

              break;
            case 0:
              i9 = i34 & 0xFFFF;
            }

            i34 = i34 >> 16 & 0xFFFF;

            if (i34 != 0)
            {
              i18++;

              switch (i18)
              {
              case 8:
                i17 = i34;

                break;
              case 7:
                i16 = i34;

                break;
              case 6:
                i15 = i34;

                break;
              case 5:
                i14 = i34;

                break;
              case 4:
                i13 = i34;

                break;
              case 3:
                i12 = i34;

                break;
              case 2:
                i11 = i34;

                break;
              case 1:
                i10 = i34;
              }

            }

          }

        }

        if ((i1 & 0x1) != 0)
        {
          i2 = 2;
          i9 = i4;
        }
        else
        {
          i2 = 3;
          i9 = i4 * 100 + (101 - localObject[(n + 1)]);
        }

        for (; i2 < i1; i2 += 2)
        {
          i34 = (101 - localObject[(n + i2 - 1)]) * 100 + (101 - localObject[(n + i2)]) + i9 * 10000;

          switch (i18)
          {
          default:
            i9 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i10 * 10000;
            i10 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i11 * 10000;
            i11 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i12 * 10000;
            i12 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i13 * 10000;
            i13 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i14 * 10000;
            i14 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i15 * 10000;
            i15 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i16 * 10000;
            i16 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i17 * 10000;
            i17 = i34 & 0xFFFF;

            break;
          case 7:
            i9 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i10 * 10000;
            i10 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i11 * 10000;
            i11 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i12 * 10000;
            i12 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i13 * 10000;
            i13 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i14 * 10000;
            i14 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i15 * 10000;
            i15 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i16 * 10000;
            i16 = i34 & 0xFFFF;

            break;
          case 6:
            i9 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i10 * 10000;
            i10 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i11 * 10000;
            i11 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i12 * 10000;
            i12 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i13 * 10000;
            i13 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i14 * 10000;
            i14 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i15 * 10000;
            i15 = i34 & 0xFFFF;

            break;
          case 5:
            i9 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i10 * 10000;
            i10 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i11 * 10000;
            i11 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i12 * 10000;
            i12 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i13 * 10000;
            i13 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i14 * 10000;
            i14 = i34 & 0xFFFF;

            break;
          case 4:
            i9 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i10 * 10000;
            i10 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i11 * 10000;
            i11 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i12 * 10000;
            i12 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i13 * 10000;
            i13 = i34 & 0xFFFF;

            break;
          case 3:
            i9 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i10 * 10000;
            i10 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i11 * 10000;
            i11 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i12 * 10000;
            i12 = i34 & 0xFFFF;

            break;
          case 2:
            i9 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i10 * 10000;
            i10 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i11 * 10000;
            i11 = i34 & 0xFFFF;

            break;
          case 1:
            i9 = i34 & 0xFFFF;
            i34 = (i34 >> 16 & 0xFFFF) + i10 * 10000;
            i10 = i34 & 0xFFFF;

            break;
          case 0:
            i9 = i34 & 0xFFFF;
          }

          i34 = i34 >> 16 & 0xFFFF;

          if (i34 != 0)
          {
            i18++;

            switch (i18)
            {
            case 8:
              i17 = i34;

              break;
            case 7:
              i16 = i34;

              break;
            case 6:
              i15 = i34;

              break;
            case 5:
              i14 = i34;

              break;
            case 4:
              i13 = i34;

              break;
            case 3:
              i12 = i34;

              break;
            case 2:
              i11 = i34;

              break;
            case 1:
              i10 = i34;
            }

          }

        }

        int i31 = i18;

        i18++;

        int i21 = 62 - m + i1;

        int i22 = nexpdigstable[i21];
        int[] arrayOfInt = expdigstable[i21];
        i33 = i18 + 5;

        int i34 = 0;

        if (i22 > i33)
        {
          i34 = i22 - i33;
          i22 = i33;
        }

        i20 = 0;
        int i23 = 0;
        int i29 = i22 - 1 + (i18 - 1) - 4;
        int i35;
        int i3;
        for (i19 = 0; i19 < i29; i19++)
        {
          i35 = i23 & 0xFFFF;

          i23 = i23 >> 16 & 0xFFFF;

          i36 = i18 < i19 + 1 ? i18 : i19 + 1;

          for (i3 = i19 - i22 + 1 > 0 ? i19 - i22 + 1 : 0; i3 < i36; 
            i3++)
          {
            i37 = i34 + i19 - i3;

            switch (i3)
            {
            case 8:
              i38 = i17 * arrayOfInt[i37];

              break;
            case 7:
              i38 = i16 * arrayOfInt[i37];

              break;
            case 6:
              i38 = i15 * arrayOfInt[i37];

              break;
            case 5:
              i38 = i14 * arrayOfInt[i37];

              break;
            case 4:
              i38 = i13 * arrayOfInt[i37];

              break;
            case 3:
              i38 = i12 * arrayOfInt[i37];

              break;
            case 2:
              i38 = i11 * arrayOfInt[i37];

              break;
            case 1:
              i38 = i10 * arrayOfInt[i37];

              break;
            default:
              i38 = i9 * arrayOfInt[i37];
            }

            i35 += (i38 & 0xFFFF);
            i23 += (i38 >> 16 & 0xFFFF);
          }

          i32 = (i32 != 0) || ((i35 & 0xFFFF) != 0) ? 1 : 0;
          i23 += (i35 >> 16 & 0xFFFF);
        }

        i29 += 5;

        for (; i19 < i29; i19++)
        {
          i35 = i23 & 0xFFFF;

          i23 = i23 >> 16 & 0xFFFF;

          i36 = i18 < i19 + 1 ? i18 : i19 + 1;

          for (i3 = i19 - i22 + 1 > 0 ? i19 - i22 + 1 : 0; i3 < i36; 
            i3++)
          {
            i37 = i34 + i19 - i3;

            switch (i3)
            {
            case 8:
              i38 = i17 * arrayOfInt[i37];

              break;
            case 7:
              i38 = i16 * arrayOfInt[i37];

              break;
            case 6:
              i38 = i15 * arrayOfInt[i37];

              break;
            case 5:
              i38 = i14 * arrayOfInt[i37];

              break;
            case 4:
              i38 = i13 * arrayOfInt[i37];

              break;
            case 3:
              i38 = i12 * arrayOfInt[i37];

              break;
            case 2:
              i38 = i11 * arrayOfInt[i37];

              break;
            case 1:
              i38 = i10 * arrayOfInt[i37];

              break;
            default:
              i38 = i9 * arrayOfInt[i37];
            }

            i35 += (i38 & 0xFFFF);
            i23 += (i38 >> 16 & 0xFFFF);
          }

          switch (i20++)
          {
          case 4:
            i28 = i35 & 0xFFFF;

            break;
          case 3:
            i27 = i35 & 0xFFFF;

            break;
          case 2:
            i26 = i35 & 0xFFFF;

            break;
          case 1:
            i25 = i35 & 0xFFFF;

            break;
          default:
            i24 = i35 & 0xFFFF;
          }

          i23 += (i35 >> 16 & 0xFFFF);
        }

        while (i23 != 0)
        {
          if (i20 < 5) {
            switch (i20++)
            {
            case 4:
              i28 = i23 & 0xFFFF;

              break;
            case 3:
              i27 = i23 & 0xFFFF;

              break;
            case 2:
              i26 = i23 & 0xFFFF;

              break;
            case 1:
              i25 = i23 & 0xFFFF;

              break;
            default:
              i24 = i23 & 0xFFFF;

              break;
            }
          }
          else {
            i32 = (i32 != 0) || (i24 != 0) ? 1 : 0;
            i24 = i25;
            i25 = i26;
            i26 = i27;
            i27 = i28;
            i28 = i23 & 0xFFFF;
          }

          i23 = i23 >> 16 & 0xFFFF;
          i31++;
        }

        int i30 = (binexpstable[i21] + i31) * 16 - 1;

        switch (i20)
        {
        case 5:
          i35 = i28;

          break;
        case 4:
          i35 = i27;

          break;
        case 3:
          i35 = i26;

          break;
        case 2:
          i35 = i25;

          break;
        default:
          i35 = i24;
        }

        for (int i36 = i35 >> 1; i36 != 0; i36 >>= 1) {
          i30++;
        }

        i36 = 5;
        int i37 = i35 << 5;
        int i38 = 0;

        i23 = 0;

        while ((i37 & 0x100000) == 0)
        {
          i37 <<= 1;
          i36++;
        }

        switch (i20)
        {
        case 5:
          if (i36 > 16)
          {
            i37 |= i27 << i36 - 16 | i26 >> 32 - i36;
            i38 = i26 << i36 | i25 << i36 - 16 | i24 >> 32 - i36;

            i23 = i24 & 1 << 31 - i36;
            i32 = (i32 != 0) || (i24 << i36 + 1 != 0) ? 1 : 0;
          }
          else if (i36 == 16)
          {
            i37 |= i27;
            i38 = i26 << 16 | i25;
            i23 = i24 & 0x8000;
            i32 = (i32 != 0) || ((i24 & 0x7FFF) != 0) ? 1 : 0;
          }
          else
          {
            i37 |= i27 >> 16 - i36;
            i38 = i27 << 16 + i36 | i26 << i36 | i25 >> 16 - i36;

            i23 = i25 & 1 << 15 - i36;

            if (i36 < 15) {
              i32 = (i32 != 0) || (i25 << i36 + 17 != 0) ? 1 : 0;
            }
            i32 = (i32 != 0) || (i24 != 0) ? 1 : 0;
          }

          break;
        case 4:
          if (i36 > 16)
          {
            i37 |= i26 << i36 - 16 | i25 >> 32 - i36;
            i38 = i25 << i36 | i24 << i36 - 16;
          }
          else if (i36 == 16)
          {
            i37 |= i26;

            i38 = i25 << 16 | i24;
          }
          else
          {
            i37 |= i26 >> 16 - i36;
            i38 = i26 << 16 + i36 | i25 << i36 | i24 >> 16 - i36;

            i23 = i24 & 1 << 15 - i36;

            if (i36 < 15)
              i32 = (i32 != 0) || (i24 << i36 + 17 != 0) ? 1 : 0;  } break;
        case 3:
          if (i36 > 16)
          {
            i37 |= i25 << i36 - 16 | i24 >> 32 - i36;
            i38 = i24 << i36;
          }
          else if (i36 == 16)
          {
            i37 |= i25;

            i38 = i24 << 16;
          }
          else
          {
            i37 |= i25 >> 16 - i36;
            i38 = i25 << 16 + i36;

            i38 |= i24 << i36;
          }

          break;
        case 2:
          if (i36 > 16)
          {
            i37 |= i24 << i36 - 16;
          }
          else if (i36 == 16)
          {
            i37 |= i24;
          }
          else
          {
            i37 |= i24 >> 16 - i36;
            i38 = i24 << 16 + i36;
          }

          break;
        }

        if ((i23 != 0) && ((i32 != 0) || ((i38 & 0x1) != 0)))
        {
          if (i38 == -1)
          {
            i38 = 0;
            i37++;

            if ((i37 & 0x200000) != 0)
            {
              i38 = i38 >> 1 | i37 << 31;
              i37 >>= 1;
              i30++;
            }
          }
          else {
            i38++;
          }

        }

        long l = i30 << 52 | (i37 & 0xFFFFF) << 32 | i38 & 0xFFFFFFFF;

        d2 = Double.longBitsToDouble(l);
      }

      d1 = i7 != 0 ? d2 : -d2;
    }

    return d1;
  }

  double getDoubleImprecise(int paramInt)
    throws SQLException
  {
    double d1 = 0.0D;
    Object localObject;
    if (this.rowSpaceIndicator == null)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
    {
      localObject = this.rowSpaceByte;
      int i = this.columnIndex + this.byteLength * paramInt + 1;
      int j = localObject[(i - 1)];

      int k = localObject[i];

      double d2 = 0.0D;

      int i1 = i + 1;

      if ((k & 0xFFFFFF80) != 0)
      {
        if ((k == -128) && (j == 1)) {
          return 0.0D;
        }
        if ((j == 2) && (k == -1) && (localObject[(i + 1)] == 101))
        {
          return (1.0D / 0.0D);
        }
        m = (byte)((k & 0xFFFFFF7F) - 65);

        i3 = j - 1;

        n = (int)(127.0D - m);

        i2 = i3 % 4;

        switch (i2)
        {
        case 1:
          d2 = (localObject[i1] - 1) * factorTable[n];

          break;
        case 2:
          d2 = ((localObject[i1] - 1) * 100 + (localObject[(i1 + 1)] - 1)) * factorTable[(n + 1)];

          break;
        case 3:
          d2 = ((localObject[i1] - 1) * 10000 + (localObject[(i1 + 1)] - 1) * 100 + (localObject[(i1 + 2)] - 1)) * factorTable[(n + 2)];

          break;
        }

        for (; i2 < i3; i2 += 4) {
          d2 += ((localObject[(i1 + i2)] - 1) * 1000000 + (localObject[(i1 + i2 + 1)] - 1) * 10000 + (localObject[(i1 + i2 + 2)] - 1) * 100 + (localObject[(i1 + i2 + 3)] - 1)) * factorTable[(n + i2 + 3)];
        }

      }

      if ((k == 0) && (j == 1)) {
        return (-1.0D / 0.0D);
      }
      int m = (byte)(((k ^ 0xFFFFFFFF) & 0xFFFFFF7F) - 65);

      int i3 = j - 1;

      if ((i3 != 20) || (localObject[(i + i3)] == 102)) {
        i3--;
      }

      int n = (int)(127.0D - m);

      int i2 = i3 % 4;

      switch (i2)
      {
      case 1:
        d2 = (101 - localObject[i1]) * factorTable[n];

        break;
      case 2:
        d2 = ((101 - localObject[i1]) * 100 + (101 - localObject[(i1 + 1)])) * factorTable[(n + 1)];

        break;
      case 3:
        d2 = ((101 - localObject[i1]) * 10000 + (101 - localObject[(i1 + 1)]) * 100 + (101 - localObject[(i1 + 2)])) * factorTable[(n + 2)];

        break;
      }

      for (; i2 < i3; i2 += 4) {
        d2 += ((101 - localObject[(i1 + i2)]) * 1000000 + (101 - localObject[(i1 + i2 + 1)]) * 10000 + (101 - localObject[(i1 + i2 + 2)]) * 100 + (101 - localObject[(i1 + i2 + 3)])) * factorTable[(n + i2 + 3)];
      }

      d2 = -d2;

      d1 = d2;
    }

    return d1;
  }

  BigDecimal getBigDecimal(int paramInt)
    throws SQLException
  {
    BigDecimal localBigDecimal = null;
    Object localObject;
    if (this.rowSpaceIndicator == null)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
    {
      localObject = this.rowSpaceByte;
      int i = this.columnIndex + this.byteLength * paramInt + 1;
      int j = localObject[(i - 1)];

      for (int k = 0; k < 27; k++) {
        this.digs[k] = 0;
      }
      k = 0;
      int m = 1;

      int i1 = 26;
      int i2 = 0;

      int i5 = localObject[i];

      int i10 = 0;

      if ((i5 & 0xFFFFFF80) != 0)
      {
        if ((i5 == -128) && (j == 1)) {
          return BIGDEC_ZERO;
        }
        if ((j == 2) && (i5 == -1) && (localObject[(i + 1)] == 101))
        {
          throwOverflow();
        }
        i6 = 1;
        i7 = (byte)((i5 & 0xFFFFFF7F) - 65);

        i4 = j - 1;
        n = i4 - 1;
        i8 = i7 - i4 + 1 << 1;

        if (i8 > 0)
        {
          i8 = 0;
          n = i7;
        }
        else if (i8 < 0) {
          i10 = (localObject[(i + i4)] - 1) % 10 == 0 ? 1 : 0;
        }
        m = (byte)(m + 1); k = localObject[(i + m)] - 1;

        while ((n & 0x1) != 0)
        {
          if (m > i4) {
            k *= 100;
          } else {
            m = (byte)(m + 1); k = k * 100 + (localObject[(i + m)] - 1);
          }
          n--;
        }

      }

      if ((i5 == 0) && (j == 1))
      {
        throwOverflow();
      }
      int i6 = -1;
      int i7 = (byte)(((i5 ^ 0xFFFFFFFF) & 0xFFFFFF7F) - 65);

      int i4 = j - 1;

      if ((i4 != 20) || (localObject[(i + i4)] == 102)) {
        i4--;
      }
      int n = i4 - 1;

      int i8 = i7 - i4 + 1 << 1;

      if (i8 > 0)
      {
        i8 = 0;
        n = i7;
      }
      else if (i8 < 0) {
        i10 = (101 - localObject[(i + i4)]) % 10 == 0 ? 1 : 0;
      }
      m = (byte)(m + 1); k = 101 - localObject[(i + m)];

      while ((n & 0x1) != 0)
      {
        if (m > i4) {
          k *= 100;
        } else {
          m = (byte)(m + 1); k = k * 100 + (101 - localObject[(i + m)]);
        }
        n--;
      }

      if (i10 != 0)
      {
        i8++;
        k /= 10;
      }

      int i9 = i4 - 1;

      while (n != 0)
      {
        if (i6 == 1)
        {
          if (i10 != 0)
          {
            i2 = (localObject[(i + m - 1)] - 1) % 10 * 1000 + (localObject[(i + m)] - 1) * 10 + (localObject[(i + m + 1)] - 1) / 10 + k * 10000;

            m = (byte)(m + 2);
          }
          else if (m < i9)
          {
            i2 = (localObject[(i + m)] - 1) * 100 + (localObject[(i + m + 1)] - 1) + k * 10000;

            m = (byte)(m + 2);
          }
          else
          {
            i2 = 0;

            if (m <= i4)
            {
              for (i11 = 0; 
                m <= i4; i11++) {
                m = (byte)(m + 1); i2 = i2 * 100 + (localObject[(i + m)] - 1);
              }
              for (; i11 < 2; i11++) {
                i2 *= 100;
              }
            }
            i2 += k * 10000;
          }
        }
        else if (i10 != 0)
        {
          i2 = (101 - localObject[(i + m - 1)]) % 10 * 1000 + (101 - localObject[(i + m)]) * 10 + (101 - localObject[(i + m + 1)]) / 10 + k * 10000;

          m = (byte)(m + 2);
        }
        else if (m < i9)
        {
          i2 = (101 - localObject[(i + m)]) * 100 + (101 - localObject[(i + m + 1)]) + k * 10000;

          m = (byte)(m + 2);
        }
        else
        {
          i2 = 0;

          if (m <= i4)
          {
            for (i11 = 0; 
              m <= i4; i11++) {
              m = (byte)(m + 1); i2 = i2 * 100 + (101 - localObject[(i + m)]);
            }
            for (; i11 < 2; i11++) {
              i2 *= 100;
            }
          }
          i2 += k * 10000;
        }

        k = i2 & 0xFFFF;

        for (int i11 = 25; i11 >= i1; i11--)
        {
          i2 = (i2 >> 16) + this.digs[i11] * 10000;
          this.digs[i11] = (i2 & 0xFFFF);
        }

        if (i1 > 0)
        {
          i2 >>= 16;
          if (i2 != 0) {
            i1 = (byte)(i1 - 1); this.digs[i1] = i2;
          }
        }
        n -= 2;
      }

      this.digs[26] = k;

      int i12 = (byte)(this.digs[i1] >> 8 & 0xFF);
      int i3;
      byte[] arrayOfByte;
      int i13;
      int i14;
      if (i12 == 0)
      {
        i3 = 53 - (i1 << 1);
        arrayOfByte = new byte[i3];

        for (i13 = 26; i13 > i1; i13--)
        {
          i14 = i13 - i1 << 1;

          arrayOfByte[(i14 - 1)] = ((byte)(this.digs[i13] >> 8 & 0xFF));
          arrayOfByte[i14] = ((byte)(this.digs[i13] & 0xFF));
        }

        arrayOfByte[0] = ((byte)(this.digs[i1] & 0xFF));
      }
      else
      {
        i3 = 54 - (i1 << 1);
        arrayOfByte = new byte[i3];

        for (i13 = 26; i13 > i1; i13--)
        {
          i14 = i13 - i1 << 1;

          arrayOfByte[i14] = ((byte)(this.digs[i13] >> 8 & 0xFF));
          arrayOfByte[(i14 + 1)] = ((byte)(this.digs[i13] & 0xFF));
        }

        arrayOfByte[0] = i12;
        arrayOfByte[1] = ((byte)(this.digs[i1] & 0xFF));
      }

      if ((i8 == 0) && (i3 < 8) && (i3 > 0)) {
        long l = arrayOfByte[0] & 0xFF;
        for (int i15 = 1; i15 < i3; i15++) l = l << 8 | arrayOfByte[i15] & 0xFF;
        l *= i6;
        localBigDecimal = new BigDecimal(l);
      }
      else
      {
        BigInteger localBigInteger = new BigInteger(i6, arrayOfByte);

        localBigDecimal = new BigDecimal(localBigInteger, -i8);
      }

    }

    return localBigDecimal;
  }

  BigDecimal getBigDecimal(int paramInt1, int paramInt2)
    throws SQLException
  {
    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt1)] == -1)
    {
      return null;
    }

    return getBigDecimal(paramInt1).setScale(paramInt2, 6);
  }

  String getString(int paramInt)
    throws SQLException
  {
    String str1 = null;
    Object localObject;
    if (this.rowSpaceIndicator == null)
    {
      localObject = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      ((SQLException)localObject).fillInStackTrace();
      throw ((Throwable)localObject);
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
    {
      localObject = this.rowSpaceByte;
      int i = this.columnIndex + this.byteLength * paramInt + 1;
      int j = localObject[(i - 1)];

      byte[] arrayOfByte = new byte[j];

      System.arraycopy(localObject, i, arrayOfByte, 0, j);

      NUMBER localNUMBER = new NUMBER(arrayOfByte);

      String str2 = NUMBER.toString(arrayOfByte);
      int k = str2.length();

      if ((str2.startsWith("0.")) || (str2.startsWith("-0."))) {
        k--;
      }
      if (k > 38)
      {
        str2 = localNUMBER.toText(-44, null);

        int m = str2.indexOf('E');
        int n = str2.indexOf('+');

        if (m == -1)
        {
          m = str2.indexOf('e');
        }

        int i1 = m - 1;

        while (str2.charAt(i1) == '0')
        {
          i1--;
        }

        String str3 = str2.substring(0, i1 + 1);
        String str4 = null;

        if (n > 0)
        {
          str4 = str2.substring(n + 1);
        }
        else
        {
          str4 = str2.substring(m + 1);
        }

        return (str3 + "E" + str4).trim();
      }

      return localNUMBER.toText(38, null).trim();
    }

    return str1;
  }

  NUMBER getNUMBER(int paramInt)
    throws SQLException
  {
    NUMBER localNUMBER = null;

    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
    {
      int i = this.columnIndex + this.byteLength * paramInt + 1;
      int j = this.rowSpaceByte[(i - 1)];
      byte[] arrayOfByte = new byte[j];

      System.arraycopy(this.rowSpaceByte, i, arrayOfByte, 0, j);

      localNUMBER = new NUMBER(arrayOfByte);
    }

    return localNUMBER;
  }

  Object getObject(int paramInt)
    throws SQLException
  {
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
      if (this.externalType == 0)
      {
        if ((this.statement.connection.j2ee13Compliant) && (this.precision != 0) && (this.scale == -127))
        {
          localObject = new Double(getDouble(paramInt));
        }
        else localObject = getBigDecimal(paramInt);
      }
      else
      {
        switch (this.externalType)
        {
        case -7:
          return Boolean.valueOf(getBoolean(paramInt));
        case -6:
          return Byte.valueOf(getByte(paramInt));
        case 5:
          return Short.valueOf(getShort(paramInt));
        case 4:
          return Integer.valueOf(getInt(paramInt));
        case -5:
          return Long.valueOf(getLong(paramInt));
        case 6:
        case 8:
          return Double.valueOf(getDouble(paramInt));
        case 7:
          return Float.valueOf(getFloat(paramInt));
        case 2:
        case 3:
          return getBigDecimal(paramInt);
        case -4:
        case -3:
        case -2:
        case -1:
        case 0:
        case 1: } localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 4);
        localSQLException.fillInStackTrace();
        throw localSQLException;
      }

    }

    return localObject;
  }

  Object getObject(int paramInt, Map paramMap)
    throws SQLException
  {
    return getObject(paramInt);
  }

  Datum getOracleObject(int paramInt)
    throws SQLException
  {
    return getNUMBER(paramInt);
  }

  byte[] getBytes(int paramInt)
    throws SQLException
  {
    byte[] arrayOfByte = null;

    if (this.rowSpaceIndicator == null)
    {
      SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 21);
      localSQLException.fillInStackTrace();
      throw localSQLException;
    }

    if (this.rowSpaceIndicator[(this.indicatorIndex + paramInt)] != -1)
    {
      int i = this.columnIndex + this.byteLength * paramInt + 1;
      int j = this.rowSpaceByte[(i - 1)];

      arrayOfByte = new byte[j];

      System.arraycopy(this.rowSpaceByte, i, arrayOfByte, 0, j);
    }

    return arrayOfByte;
  }

  void throwOverflow()
    throws SQLException
  {
    SQLException localSQLException = DatabaseError.createSqlException(getConnectionDuringExceptionHandling(), 26);
    localSQLException.fillInStackTrace();
    throw localSQLException;
  }
}