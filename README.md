About
=====

This repository contains a collection of custom table definitions for  
the Yahoo Query Language (YQL) web service.

For more information on how to use them in YQL, please refer to: https://developer.yahoo.com/yql/guide/yql-opentables-import.html#yql-opentables-import-single

yahoo.finance.components
------------------------
This table returns a list of the components of an index.

Example query:
```sql
use 'https://raw.githubusercontent.com/philippn/yql-tables/master/yahoo.finance.components.xml' as mytable;
select * from mytable where symbol='^GDAXI';
```

Example response:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<query xmlns:yahoo="http://www.yahooapis.com/v1/base.rng" yahoo:count="30" yahoo:created="2014-11-26T08:07:38Z" yahoo:lang="en-US">
  <results>
    <component>ADS.DE</component>
    <component>ALV.DE</component>
    <component>BAS.DE</component>
    <component>BAYN.DE</component>
    <component>BEI.DE</component>
    <component>BMW.DE</component>
    <component>CBK.DE</component>
    <component>CON.DE</component>
    <component>DAI.DE</component>
    <component>DB1.DE</component>
    <component>DBK.DE</component>
    <component>DPW.DE</component>
    <component>DTE.DE</component>
    <component>EOAN.DE</component>
    <component>FME.DE</component>
    <component>FRE.DE</component>
    <component>HEI.DE</component>
    <component>HEN3.DE</component>
    <component>IFX.DE</component>
    <component>LHA.DE</component>
    <component>LIN.DE</component>
    <component>LXS.DE</component>
    <component>MRK.DE</component>
    <component>MUV2.DE</component>
    <component>RWE.DE</component>
    <component>SAP.DE</component>
    <component>SDF.DE</component>
    <component>SIE.DE</component>
    <component>TKA.DE</component>
    <component>VOW3.DE</component>
  </results>
</query>
```
