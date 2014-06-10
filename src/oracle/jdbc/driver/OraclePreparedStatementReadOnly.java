package oracle.jdbc.driver;

class OraclePreparedStatementReadOnly
{
  static Binder theStaticVarnumCopyingBinder = new VarnumCopyingBinder();
  static Binder theStaticVarnumNullBinder = new VarnumNullBinder();
  static Binder theStaticBooleanBinder = new BooleanBinder();
  static Binder theStaticByteBinder = new ByteBinder();
  static Binder theStaticShortBinder = new ShortBinder();
  static Binder theStaticIntBinder = new IntBinder();
  static Binder theStaticLongBinder = new LongBinder();
  static Binder theStaticFloatBinder = new FloatBinder();
  static Binder theStaticDoubleBinder = new DoubleBinder();
  static Binder theStaticBigDecimalBinder = new BigDecimalBinder();
  static Binder theStaticVarcharCopyingBinder = new VarcharCopyingBinder();
  static Binder theStaticVarcharNullBinder = new VarcharNullBinder();
  static Binder theStaticStringBinder = new StringBinder();
  static Binder theStaticSetCHARCopyingBinder = new SetCHARCopyingBinder();
  static Binder theStaticSetCHARBinder = new SetCHARBinder();
  static Binder theStaticLittleEndianSetCHARBinder = new LittleEndianSetCHARBinder();

  static Binder theStaticSetCHARNullBinder = new SetCHARNullBinder();
  static Binder theStaticFixedCHARCopyingBinder = new FixedCHARCopyingBinder();
  static Binder theStaticFixedCHARBinder = new FixedCHARBinder();
  static Binder theStaticFixedCHARNullBinder = new FixedCHARNullBinder();
  static Binder theStaticDateCopyingBinder = new DateCopyingBinder();
  static Binder theStaticDateBinder = new DateBinder();
  static Binder theStaticDateNullBinder = new DateNullBinder();
  static Binder theStaticTimeCopyingBinder = new TimeCopyingBinder();
  static Binder theStaticTimeBinder = new TimeBinder();
  static Binder theStaticTimestampCopyingBinder = new TimestampCopyingBinder();
  static Binder theStaticTimestampBinder = new TimestampBinder();
  static Binder theStaticTimestampNullBinder = new TimestampNullBinder();
  static Binder theStaticOracleNumberBinder = new OracleNumberBinder();
  static Binder theStaticOracleDateBinder = new OracleDateBinder();
  static Binder theStaticOracleTimestampBinder = new OracleTimestampBinder();
  static Binder theStaticTSTZCopyingBinder = new TSTZCopyingBinder();
  static Binder theStaticTSTZBinder = new TSTZBinder();
  static Binder theStaticTSTZNullBinder = new TSTZNullBinder();
  static Binder theStaticTSLTZCopyingBinder = new TSLTZCopyingBinder();
  static Binder theStaticTSLTZBinder = new TSLTZBinder();
  static Binder theStaticTSLTZNullBinder = new TSLTZNullBinder();
  static Binder theStaticRowidCopyingBinder = new RowidCopyingBinder();
  static Binder theStaticURowidCopyingBinder = new RowidCopyingBinder();
  static Binder theStaticRowidBinder = new RowidBinder();
  static Binder theStaticURowidBinder = new RowidBinder();
  static Binder theStaticLittleEndianRowidBinder = new LittleEndianRowidBinder();

  static Binder theStaticRowidNullBinder = new RowidNullBinder();
  static Binder theStaticURowidNullBinder = new RowidNullBinder();
  static Binder theStaticIntervalDSCopyingBinder = new IntervalDSCopyingBinder();

  static Binder theStaticIntervalDSBinder = new IntervalDSBinder();
  static Binder theStaticIntervalDSNullBinder = new IntervalDSNullBinder();
  static Binder theStaticIntervalYMCopyingBinder = new IntervalYMCopyingBinder();

  static Binder theStaticIntervalYMBinder = new IntervalYMBinder();
  static Binder theStaticIntervalYMNullBinder = new IntervalYMNullBinder();
  static Binder theStaticBfileCopyingBinder = new BfileCopyingBinder();
  static Binder theStaticBfileBinder = new BfileBinder();
  static Binder theStaticBfileNullBinder = new BfileNullBinder();
  static Binder theStaticBlobCopyingBinder = new BlobCopyingBinder();
  static Binder theStaticBlobBinder = new BlobBinder();
  static Binder theStaticBlobNullBinder = new BlobNullBinder();
  static Binder theStaticClobCopyingBinder = new ClobCopyingBinder();
  static Binder theStaticClobBinder = new ClobBinder();
  static Binder theStaticClobNullBinder = new ClobNullBinder();
  static Binder theStaticRawCopyingBinder = new RawCopyingBinder();
  static Binder theStaticRawBinder = new RawBinder();
  static Binder theStaticRawNullBinder = new RawNullBinder();
  static Binder theStaticPlsqlRawCopyingBinder = new PlsqlRawCopyingBinder();
  static Binder theStaticPlsqlRawBinder = new PlsqlRawBinder();
  static Binder theStaticBinaryFloatCopyingBinder = new BinaryFloatCopyingBinder();

  static Binder theStaticBinaryFloatBinder = new BinaryFloatBinder();
  static Binder theStaticBinaryFloatNullBinder = new BinaryFloatNullBinder();
  static Binder theStaticBinaryDoubleCopyingBinder = new BinaryDoubleCopyingBinder();

  static Binder theStaticBinaryDoubleBinder = new BinaryDoubleBinder();
  static Binder theStaticBinaryDoubleNullBinder = new BinaryDoubleNullBinder();
  static Binder theStaticBINARY_FLOATCopyingBinder = new BINARY_FLOATCopyingBinder();

  static Binder theStaticBINARY_FLOATBinder = new BINARY_FLOATBinder();
  static Binder theStaticBINARY_FLOATNullBinder = new BINARY_FLOATNullBinder();
  static Binder theStaticBINARY_DOUBLECopyingBinder = new BINARY_DOUBLECopyingBinder();

  static Binder theStaticBINARY_DOUBLEBinder = new BINARY_DOUBLEBinder();
  static Binder theStaticBINARY_DOUBLENullBinder = new BINARY_DOUBLENullBinder();

  static Binder theStaticLongStreamBinder = new LongStreamBinder();
  static Binder theStaticLongStreamForStringBinder = new LongStreamForStringBinder();
  static Binder theStaticLongStreamForStringCopyingBinder = new LongStreamForStringCopyingBinder();
  static Binder theStaticLongRawStreamBinder = new LongRawStreamBinder();
  static Binder theStaticLongRawStreamForBytesBinder = new LongRawStreamForBytesBinder();
  static Binder theStaticLongRawStreamForBytesCopyingBinder = new LongRawStreamForBytesCopyingBinder();
  static Binder theStaticNamedTypeCopyingBinder = new NamedTypeCopyingBinder();
  static Binder theStaticNamedTypeBinder = new NamedTypeBinder();
  static Binder theStaticNamedTypeNullBinder = new NamedTypeNullBinder();
  static Binder theStaticRefTypeCopyingBinder = new RefTypeCopyingBinder();
  static Binder theStaticRefTypeBinder = new RefTypeBinder();
  static Binder theStaticRefTypeNullBinder = new RefTypeNullBinder();
  static Binder theStaticPlsqlIbtCopyingBinder = new PlsqlIbtCopyingBinder();
  static Binder theStaticPlsqlIbtBinder = new PlsqlIbtBinder();
  static Binder theStaticPlsqlIbtNullBinder = new PlsqlIbtNullBinder();
  static Binder theStaticOutBinder = new OutBinder();
  static Binder theStaticReturnParamBinder = new ReturnParamBinder();

  static Binder theStaticT4CRowidCopyingBinder = new T4CRowidCopyingBinder();
  static Binder theStaticT4CRowidBinder = new T4CRowidBinder();
  static Binder theStaticT4CRowidNullBinder = new T4CRowidNullBinder();

  static Binder theStaticT4CURowidCopyingBinder = new T4CURowidCopyingBinder();
  static Binder theStaticT4CURowidBinder = new T4CURowidBinder();
  static Binder theStaticT4CURowidNullBinder = new T4CURowidNullBinder();
}