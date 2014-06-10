package oracle.sql;

import java.util.HashMap;

class TableClass
{
  private HashMap<String, Integer> zoneToIdMap;
  private HashMap<Integer, String> idToZoneMap;
  private HashMap<Integer, String> oldIdToZoneMap;

  TableClass(int paramInt, float paramFloat)
  {
    this.zoneToIdMap = new HashMap(paramInt, paramFloat);
    this.idToZoneMap = new HashMap(paramInt, paramFloat);

    this.oldIdToZoneMap = new HashMap(10, 0.99F);
  }

  void put(String paramString, Integer paramInteger)
  {
    this.zoneToIdMap.put(paramString, paramInteger);
    this.idToZoneMap.put(paramInteger, paramString);
  }

  void putOld(String paramString, Integer paramInteger)
  {
    this.oldIdToZoneMap.put(paramInteger, paramString);
  }

  Integer getID(String paramString)
  {
    return (Integer)this.zoneToIdMap.get(paramString);
  }

  String getZone(Integer paramInteger)
  {
    return (String)this.idToZoneMap.get(paramInteger);
  }

  String getOldZone(Integer paramInteger)
  {
    return (String)this.oldIdToZoneMap.get(paramInteger);
  }
}