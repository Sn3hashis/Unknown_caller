package com.mhvmedia.unknowncaller.Extra;

import com.mhvmedia.unknowncaller.Variables.CallRates;
import com.mhvmedia.unknowncaller.Variables.CountryCodes;
import com.mhvmedia.unknowncaller.Variables.Variables;
/** Created by AwsmCreators * */
public class Function {
    public static String checkCountry (String countrycode){
        String result = Double.toString(Variables.MINIMUM_CALL_RATE);
        if (countrycode.contains(CountryCodes.Afghanistan)){
            result = Integer.toString(CallRates.Afghanistan);
        }else if (countrycode.contains(CountryCodes.Aland_Islands)){
            result = Integer.toString(CallRates.AlandIslands);
        }else if (countrycode.contains(CountryCodes.Albania)){
            result = Integer.toString(CallRates.Albania);
        }else if (countrycode.contains(CountryCodes.Algeria)){
            result = Integer.toString(CallRates.Algeria);
        }else if (countrycode.contains(CountryCodes.AmericanSamoa)){
            result = Integer.toString(CallRates.AmericanSamoa);
        }else if (countrycode.contains(CountryCodes.Andorra)){
            result = Integer.toString(CallRates.Andorra);
        }else if (countrycode.contains(CountryCodes.Angola)){
            result = Integer.toString(CallRates.Angola);
        }else if (countrycode.contains(CountryCodes.Anguilla)){
            result = Integer.toString(CallRates.Anguilla);
        }else if (countrycode.contains(CountryCodes.Antarctica)){
            result = Integer.toString(CallRates.Antarctica);
        }else if (countrycode.contains(CountryCodes.AntiguaandBarbuda)){
            result = Integer.toString(CallRates.AntiguaandBarbuda);
        }else if (countrycode.contains(CountryCodes.Argentina)){
            result = Integer.toString(CallRates.Argentina);
        }else if (countrycode.contains(CountryCodes.Armenia)){
            result = Integer.toString(CallRates.Armenia);
        }else if (countrycode.contains(CountryCodes.Aruba)){
            result = Integer.toString(CallRates.Aruba);
        }else if (countrycode.contains(CountryCodes.Australia)){
            result = Integer.toString(CallRates.Australia);
        }else if (countrycode.contains(CountryCodes.Austria)){
            result = Integer.toString(CallRates.Austria);
        }else if (countrycode.contains(CountryCodes.Azerbaijan)){
            result = Integer.toString(CallRates.Azerbaijan);
        }else if (countrycode.contains(CountryCodes.Bahamas)){
            result = Integer.toString(CallRates.Bahamas);
        }else if (countrycode.contains(CountryCodes.Bahrain)){
            result = Integer.toString(CallRates.Bahrain);
        }else if (countrycode.contains(CountryCodes.Bangladesh)){
            result = Integer.toString(CallRates.Bangladesh);
        }else if (countrycode.contains(CountryCodes.Barbados)){
            result = Integer.toString(CallRates.Barbados);
        }else if (countrycode.contains(CountryCodes.Belarus)){
            result = Integer.toString(CallRates.Belarus);
        }else if (countrycode.contains(CountryCodes.Belgium)){
            result = Integer.toString(CallRates.Belgium);
        }else if (countrycode.contains(CountryCodes.Belize)){
            result = Integer.toString(CallRates.Belize);
        }else if (countrycode.contains(CountryCodes.Benin)){
            result = Integer.toString(CallRates.Benin);
        }else if (countrycode.contains(CountryCodes.Bermuda)){
            result = Integer.toString(CallRates.Bermuda);
        }else if (countrycode.contains(CountryCodes.Bhutan)){
            result = Integer.toString(CallRates.Bhutan);
        }else if (countrycode.contains(CountryCodes.Bolivia)){
            result = Integer.toString(CallRates.Bolivia);
        }else if (countrycode.contains(CountryCodes.Bonaire)){
            result = Integer.toString(CallRates.Bonaire);
        }else if (countrycode.contains(CountryCodes.BosniaandHerzegovina)){
            result = Integer.toString(CallRates.BosniaandHerzegovina);
        }else if (countrycode.contains(CountryCodes.Botswana)){
            result = Integer.toString(CallRates.Botswana);
        }else if (countrycode.contains(CountryCodes.BouvetIsland)){
            result = Integer.toString(CallRates.BouvetIsland);
        }else if (countrycode.contains(CountryCodes.Brazil)){
            result = Integer.toString(CallRates.Brazil);
        }else if (countrycode.contains(CountryCodes.BritishIndianOceanTerritory)){
            result = Integer.toString(CallRates.BritishIndianOceanTerritory);
        }else if (countrycode.contains(CountryCodes.Brunei)){
            result = Integer.toString(CallRates.Brunei);
        }else if (countrycode.contains(CountryCodes.Bulgaria)){
            result = Integer.toString(CallRates.Bulgaria);
        }else if (countrycode.contains(CountryCodes.BurkinaFaso)){
            result = Integer.toString(CallRates.BurkinaFaso);
        }else if (countrycode.contains(CountryCodes.Burundi)){
            result = Integer.toString(CallRates.Burundi);
        }else if (countrycode.contains(CountryCodes.Cambodia)){
            result = Integer.toString(CallRates.Cambodia);
        }else if (countrycode.contains(CountryCodes.Cameroon)){
            result = Integer.toString(CallRates.Cameroon);
        }else if (countrycode.contains(CountryCodes.Canada)){
            result = Integer.toString(CallRates.Canada);
        }else if (countrycode.contains(CountryCodes.CapeVerde)){
            result = Integer.toString(CallRates.CapeVerde);
        }else if (countrycode.contains(CountryCodes.CaymanIslands)){
            result = Integer.toString(CallRates.CaymanIslands);
        }else if (countrycode.contains(CountryCodes.CentralAfricanRepublic)){
            result = Integer.toString(CallRates.CentralAfricanRepublic);
        }else if (countrycode.contains(CountryCodes.Chad)){
            result = Integer.toString(CallRates.Chad);
        }else if (countrycode.contains(CountryCodes.Chile)){
            result = Integer.toString(CallRates.Chile);
        }else if (countrycode.contains(CountryCodes.China)){
            result = Integer.toString(CallRates.China);
        }else if (countrycode.contains(CountryCodes.ChristmasIsland)){
            result = Integer.toString(CallRates.ChristmasIsland);
        }else if (countrycode.contains(CountryCodes.CocosIslands)){
            result = Integer.toString(CallRates.CocosIslands);
        }else if (countrycode.contains(CountryCodes.Colombia)){
            result = Integer.toString(CallRates.Colombia);
        }else if (countrycode.contains(CountryCodes.Comoros)){
            result = Integer.toString(CallRates.Comoros);
        }else if (countrycode.contains(CountryCodes.CookIslands)){
            result = Integer.toString(CallRates.CookIslands);
        }else if (countrycode.contains(CountryCodes.CostaRica)){
            result = Integer.toString(CallRates.CostaRica);
        }else if (countrycode.contains(CountryCodes.CotedIvoire)){
            result = Integer.toString(CallRates.CotedIvoire);
        }else if (countrycode.contains(CountryCodes.Croatia)){
            result = Integer.toString(CallRates.Croatia);
        }else if (countrycode.contains(CountryCodes.Cuba)){
            result = Integer.toString(CallRates.Cuba);
        }else if (countrycode.contains(CountryCodes.Curacao)){
            result = Integer.toString(CallRates.Curacao);
        }else if (countrycode.contains(CountryCodes.Cyprus)){
            result = Integer.toString(CallRates.Cyprus);
        }else if (countrycode.contains(CountryCodes.CzechRepublic)){
            result = Integer.toString(CallRates.CzechRepublic);
        }else if (countrycode.contains(CountryCodes.DemocraticRepublicoftheCongo)){
            result = Integer.toString(CallRates.DemocraticRepublicoftheCongo);
        }else if (countrycode.contains(CountryCodes.Denmark)){
            result = Integer.toString(CallRates.Denmark);
        }else if (countrycode.contains(CountryCodes.Djibouti)){
            result = Integer.toString(CallRates.Djibouti);
        }else if (countrycode.contains(CountryCodes.Dominica)){
            result = Integer.toString(CallRates.Dominica);
        }else if (countrycode.contains(CountryCodes.DominicanRepublic)){
            result = Integer.toString(CallRates.DominicanRepublic);
        }else if (countrycode.contains(CountryCodes.EastTimor)){
            result = Integer.toString(CallRates.EastTimor);
        }else if (countrycode.contains(CountryCodes.Ecuador)){
            result = Integer.toString(CallRates.Ecuador);
        }else if (countrycode.contains(CountryCodes.Egypt)){
            result = Integer.toString(CallRates.Egypt);
        }else if (countrycode.contains(CountryCodes.ElSalvador)){
            result = Integer.toString(CallRates.ElSalvador);
        }else if (countrycode.contains(CountryCodes.England)){
            result = Integer.toString(CallRates.England);
        }else if (countrycode.contains(CountryCodes.EquatorialGuinea)){
            result = Integer.toString(CallRates.EquatorialGuinea);
        }else if (countrycode.contains(CountryCodes.Eritrea)){
            result = Integer.toString(CallRates.Eritrea);
        }else if (countrycode.contains(CountryCodes.Estonia)){
            result = Integer.toString(CallRates.Estonia);
        }else if (countrycode.contains(CountryCodes.Ethiopia)){
            result = Integer.toString(CallRates.Ethiopia);
        }else if (countrycode.contains(CountryCodes.EuropeanUnion)){
            result = Integer.toString(CallRates.EuropeanUnion);
        }else if (countrycode.contains(CountryCodes.FalklandIslands)){
            result = Integer.toString(CallRates.FalklandIslands);
        }else if (countrycode.contains(CountryCodes.FaroeIslands)){
            result = Integer.toString(CallRates.FaroeIslands);
        }else if (countrycode.contains(CountryCodes.Fiji)){
            result = Integer.toString(CallRates.Fiji);
        }else if (countrycode.contains(CountryCodes.Finland)){
            result = Integer.toString(CallRates.Finland);
        }else if (countrycode.contains(CountryCodes.France)){
            result = Integer.toString(CallRates.France);
        }else if (countrycode.contains(CountryCodes.FrenchGuiana)){
            result = Integer.toString(CallRates.FrenchGuiana);
        }else if (countrycode.contains(CountryCodes.FrenchPolynesia)){
            result = Integer.toString(CallRates.FrenchPolynesia);
        }else if (countrycode.contains(CountryCodes.TerritoryoftheFrenchSouthernandAntarcticLands)){
            result = Integer.toString(CallRates.TerritoryoftheFrenchSouthernandAntarcticLands);
        }else if (countrycode.contains(CountryCodes.Gabon)){
            result = Integer.toString(CallRates.Gabon);
        }else if (countrycode.contains(CountryCodes.Gambia)){
            result = Integer.toString(CallRates.Gambia);
        }else if (countrycode.contains(CountryCodes.Georgia)){
            result = Integer.toString(CallRates.Georgia);
        }else if (countrycode.contains(CountryCodes.Germany)){
            result = Integer.toString(CallRates.Germany);
        }else if (countrycode.contains(CountryCodes.Ghana)){
            result = Integer.toString(CallRates.Ghana);
        }else if (countrycode.contains(CountryCodes.Gibraltar)){
            result = Integer.toString(CallRates.Gibraltar);
        }else if (countrycode.contains(CountryCodes.Greece)){
            result = Integer.toString(CallRates.Greece);
        }else if (countrycode.contains(CountryCodes.Greenland)){
            result = Integer.toString(CallRates.Greenland);
        }else if (countrycode.contains(CountryCodes.Grenada)){
            result = Integer.toString(CallRates.Grenada);
        }else if (countrycode.contains(CountryCodes.Guadeloupe)){
            result = Integer.toString(CallRates.Guadeloupe);
        }else if (countrycode.contains(CountryCodes.Guam)){
            result = Integer.toString(CallRates.Guam);
        }else if (countrycode.contains(CountryCodes.Guatemala)){
            result = Integer.toString(CallRates.Guatemala);
        }else if (countrycode.contains(CountryCodes.Guernsey)){
            result = Integer.toString(CallRates.Guernsey);
        }else if (countrycode.contains(CountryCodes.Guinea)){
            result = Integer.toString(CallRates.Guinea);
        }else if (countrycode.contains(CountryCodes.GuineaBissau)){
            result = Integer.toString(CallRates.GuineaBissau);
        }else if (countrycode.contains(CountryCodes.Guyana)){
            result = Integer.toString(CallRates.Guyana);
        }else if (countrycode.contains(CountryCodes.Haiti)){
            result = Integer.toString(CallRates.Haiti);
        }else if (countrycode.contains(CountryCodes.HeardIsland)){
            result = Integer.toString(CallRates.HeardIsland);
        }else if (countrycode.contains(CountryCodes.Honduras)){
            result = Integer.toString(CallRates.Honduras);
        }else if (countrycode.contains(CountryCodes.HongKong)){
            result = Integer.toString(CallRates.HongKong);
        }else if (countrycode.contains(CountryCodes.Hungary)){
            result = Integer.toString(CallRates.Hungary);
        }else if (countrycode.contains(CountryCodes.Iceland)){
            result = Integer.toString(CallRates.Iceland);
        }else if (countrycode.contains(CountryCodes.India)){
            result = Integer.toString(CallRates.India);
        }else if (countrycode.contains(CountryCodes.Indonesia)){
            result = Integer.toString(CallRates.Indonesia);
        }else if (countrycode.contains(CountryCodes.Iran)){
            result = Integer.toString(CallRates.Iran);
        }else if (countrycode.contains(CountryCodes.Iraq)){
            result = Integer.toString(CallRates.Iraq);
        }else if (countrycode.contains(CountryCodes.Ireland)){
            result = Integer.toString(CallRates.Ireland);
        }else if (countrycode.contains(CountryCodes.IsleofMan)){
            result = Integer.toString(CallRates.IsleofMan);
        }else if (countrycode.contains(CountryCodes.Israel)){
            result = Integer.toString(CallRates.Israel);
        }else if (countrycode.contains(CountryCodes.Italy)){
            result = Integer.toString(CallRates.Italy);
        }else if (countrycode.contains(CountryCodes.Jamaica)){
            result = Integer.toString(CallRates.Jamaica);
        }else if (countrycode.contains(CountryCodes.Japan)){
            result = Integer.toString(CallRates.Japan);
        }else if (countrycode.contains(CountryCodes.Jersey)){
            result = Integer.toString(CallRates.Jersey);
        }else if (countrycode.contains(CountryCodes.Jordan)){
            result = Integer.toString(CallRates.Jordan);
        }else if (countrycode.contains(CountryCodes.Kazakhstan)){
            result = Integer.toString(CallRates.Kazakhstan);
        }else if (countrycode.contains(CountryCodes.Kenya)){
            result = Integer.toString(CallRates.Kenya);
        }else if (countrycode.contains(CountryCodes.Kiribati)){
            result = Integer.toString(CallRates.Kiribati);
        }else if (countrycode.contains(CountryCodes.Kosovo)){
            result = Integer.toString(CallRates.Kosovo);
        }else if (countrycode.contains(CountryCodes.Kuwait)){
            result = Integer.toString(CallRates.Kuwait);
        }else if (countrycode.contains(CountryCodes.Kyrgyzstan)){
            result = Integer.toString(CallRates.Kyrgyzstan);
        }else if (countrycode.contains(CountryCodes.Laos)){
            result = Integer.toString(CallRates.Laos);
        }else if (countrycode.contains(CountryCodes.Latvia)){
            result = Integer.toString(CallRates.Latvia);
        }else if (countrycode.contains(CountryCodes.Lebanon)){
            result = Integer.toString(CallRates.Lebanon);
        }else if (countrycode.contains(CountryCodes.Lesotho)){
            result = Integer.toString(CallRates.Lesotho);
        }else if (countrycode.contains(CountryCodes.Liberia)){
            result = Integer.toString(CallRates.Liberia);
        }else if (countrycode.contains(CountryCodes.Libya)){
            result = Integer.toString(CallRates.Libya);
        }else if (countrycode.contains(CountryCodes.Liechtenstein)){
            result = Integer.toString(CallRates.Liechtenstein);
        }else if (countrycode.contains(CountryCodes.Lithuania)){
            result = Integer.toString(CallRates.Lithuania);
        }else if (countrycode.contains(CountryCodes.Luxembourg)){
            result = Integer.toString(CallRates.Luxembourg);
        }else if (countrycode.contains(CountryCodes.Macao)){
            result = Integer.toString(CallRates.Macao);
        }else if (countrycode.contains(CountryCodes.Macedonia)){
            result = Integer.toString(CallRates.Macedonia);
        }else if (countrycode.contains(CountryCodes.Madagascar)){
            result = Integer.toString(CallRates.Madagascar);
        }else if (countrycode.contains(CountryCodes.Malawi)){
            result = Integer.toString(CallRates.Malawi);
        }else if (countrycode.contains(CountryCodes.Malaysia)){
            result = Integer.toString(CallRates.Malaysia);
        }else if (countrycode.contains(CountryCodes.Maldives)){
            result = Integer.toString(CallRates.Maldives);
        }else if (countrycode.contains(CountryCodes.Mali)){
            result = Integer.toString(CallRates.Mali);
        }else if (countrycode.contains(CountryCodes.Malta)){
            result = Integer.toString(CallRates.Malta);
        }else if (countrycode.contains(CountryCodes.MarshallIslands)){
            result = Integer.toString(CallRates.MarshallIslands);
        }else if (countrycode.contains(CountryCodes.Martinique)){
            result = Integer.toString(CallRates.Martinique);
        }else if (countrycode.contains(CountryCodes.Mauritania)){
            result = Integer.toString(CallRates.Mauritania);
        }else if (countrycode.contains(CountryCodes.Mauritius)){
            result = Integer.toString(CallRates.Mauritius);
        }else if (countrycode.contains(CountryCodes.Mayotte)){
            result = Integer.toString(CallRates.Mayotte);
        }else if (countrycode.contains(CountryCodes.Mexico)){
            result = Integer.toString(CallRates.Mexico);
        }else if (countrycode.contains(CountryCodes.Micronesia)){
            result = Integer.toString(CallRates.Micronesia);
        }else if (countrycode.contains(CountryCodes.Moldova)){
            result = Integer.toString(CallRates.Moldova);
        }else if (countrycode.contains(CountryCodes.Monaco)){
            result = Integer.toString(CallRates.Monaco);
        }else if (countrycode.contains(CountryCodes.Mongolia)){
            result = Integer.toString(CallRates.Mongolia);
        }else if (countrycode.contains(CountryCodes.Montenegro)){
            result = Integer.toString(CallRates.Montenegro);
        }else if (countrycode.contains(CountryCodes.Montserrat)){
            result = Integer.toString(CallRates.Montserrat);
        }else if (countrycode.contains(CountryCodes.Morocco)){
            result = Integer.toString(CallRates.Morocco);
        }else if (countrycode.contains(CountryCodes.Mozambique)){
            result = Integer.toString(CallRates.Mozambique);
        }else if (countrycode.contains(CountryCodes.Myanmar)){
            result = Integer.toString(CallRates.Myanmar);
        }else if (countrycode.contains(CountryCodes.Namibia)){
            result = Integer.toString(CallRates.Namibia);
        }else if (countrycode.contains(CountryCodes.Nauru)){
            result = Integer.toString(CallRates.Nauru);
        }else if (countrycode.contains(CountryCodes.Nepal)){
            result = Integer.toString(CallRates.Nepal);
        }else if (countrycode.contains(CountryCodes.Netherlands)){
            result = Integer.toString(CallRates.Netherlands);
        }else if (countrycode.contains(CountryCodes.NewCaledonia)){
            result = Integer.toString(CallRates.NewCaledonia);
        }else if (countrycode.contains(CountryCodes.NewZealand)){
            result = Integer.toString(CallRates.NewZealand);
        }else if (countrycode.contains(CountryCodes.Nicaragua)){
            result = Integer.toString(CallRates.Nicaragua);
        }else if (countrycode.contains(CountryCodes.Niger)){
            result = Integer.toString(CallRates.Niger);
        }else if (countrycode.contains(CountryCodes.Nigeria)){
            result = Integer.toString(CallRates.Nigeria);
        }else if (countrycode.contains(CountryCodes.Niue)){
            result = Integer.toString(CallRates.Niue);
        }else if (countrycode.contains(CountryCodes.NorfolkIsland)){
            result = Integer.toString(CallRates.NorfolkIsland);
        }else if (countrycode.contains(CountryCodes.NorthKorea)){
            result = Integer.toString(CallRates.NorthKorea);
        }else if (countrycode.contains(CountryCodes.NorthernMarianaIslands)){
            result = Integer.toString(CallRates.NorthernMarianaIslands);
        }else if (countrycode.contains(CountryCodes.Norway)){
            result = Integer.toString(CallRates.Norway);
        }else if (countrycode.contains(CountryCodes.Oman)){
            result = Integer.toString(CallRates.Oman);
        }else if (countrycode.contains(CountryCodes.Pakistan)){
            result = Integer.toString(CallRates.Pakistan);
        }else if (countrycode.contains(CountryCodes.Palau)){
            result = Integer.toString(CallRates.Palau);
        }else if (countrycode.contains(CountryCodes.Palestinianterritories)){
            result = Integer.toString(CallRates.Palestinianterritories);
        }else if (countrycode.contains(CountryCodes.Panama)){
            result = Integer.toString(CallRates.Panama);
        }else if (countrycode.contains(CountryCodes.PapuaNewGuinea)){
            result = Integer.toString(CallRates.PapuaNewGuinea);
        }else if (countrycode.contains(CountryCodes.Paraguay)){
            result = Integer.toString(CallRates.Paraguay);
        }else if (countrycode.contains(CountryCodes.Peru)){
            result = Integer.toString(CallRates.Peru);
        }else if (countrycode.contains(CountryCodes.Philippines)){
            result = Integer.toString(CallRates.Philippines);
        }else if (countrycode.contains(CountryCodes.PitcairnIslands)){
            result = Integer.toString(CallRates.PitcairnIslands);
        }else if (countrycode.contains(CountryCodes.Poland)){
            result = Integer.toString(CallRates.Poland);
        }else if (countrycode.contains(CountryCodes.Portugal)){
            result = Integer.toString(CallRates.Portugal);
        }else if (countrycode.contains(CountryCodes.PuertoRico)){
            result = Integer.toString(CallRates.PuertoRico);
        }else if (countrycode.contains(CountryCodes.Qatar)){
            result = Integer.toString(CallRates.Qatar);
        }else if (countrycode.contains(CountryCodes.Congo)){
            result = Integer.toString(CallRates.Congo);
        }else if (countrycode.contains(CountryCodes.Reunion)){
            result = Integer.toString(CallRates.Reunion);
        }else if (countrycode.contains(CountryCodes.Romania)){
            result = Integer.toString(CallRates.Romania);
        }else if (countrycode.contains(CountryCodes.Russia)){
            result = Integer.toString(CallRates.Russia);
        }else if (countrycode.contains(CountryCodes.Rwanda)){
            result = Integer.toString(CallRates.Rwanda);
        }else if (countrycode.contains(CountryCodes.SaintBarthelemy)){
            result = Integer.toString(CallRates.SaintBarthelemy);
        }else if (countrycode.contains(CountryCodes.SaintHelena)){
            result = Integer.toString(CallRates.SaintHelena);
        }else if (countrycode.contains(CountryCodes.SaintKittsandNevis)){
            result = Integer.toString(CallRates.SaintKittsandNevis);
        }else if (countrycode.contains(CountryCodes.SaintLucia)){
            result = Integer.toString(CallRates.SaintLucia);
        }else if (countrycode.contains(CountryCodes.SaintMartin)){
            result = Integer.toString(CallRates.SaintMartin);
        }else if (countrycode.contains(CountryCodes.SaintPierreandMiquelon)){
            result = Integer.toString(CallRates.SaintPierreandMiquelon);
        }else if (countrycode.contains(CountryCodes.SaintVincentandtheGrenadines)){
            result = Integer.toString(CallRates.SaintVincentandtheGrenadines);
        }else if (countrycode.contains(CountryCodes.Samoa)){
            result = Integer.toString(CallRates.Samoa);
        }else if (countrycode.contains(CountryCodes.SanMarino)){
            result = Integer.toString(CallRates.SanMarino);
        }else if (countrycode.contains(CountryCodes.SaoTomeandPrincipe)){
            result = Integer.toString(CallRates.SaoTomeandPrincipe);
        }else if (countrycode.contains(CountryCodes.SaudiArabia)){
            result = Integer.toString(CallRates.SaudiArabia);
        }else if (countrycode.contains(CountryCodes.Scotland)){
            result = Integer.toString(CallRates.Scotland);
        }else if (countrycode.contains(CountryCodes.Senegal)){
            result = Integer.toString(CallRates.Senegal);
        }else if (countrycode.contains(CountryCodes.Serbia)){
            result = Integer.toString(CallRates.Serbia);
        }else if (countrycode.contains(CountryCodes.Seychelles)){
            result = Integer.toString(CallRates.Seychelles);
        }else if (countrycode.contains(CountryCodes.SierraLeone)){
            result = Integer.toString(CallRates.SierraLeone);
        }else if (countrycode.contains(CountryCodes.Singapore)){
            result = Integer.toString(CallRates.Singapore);
        }else if (countrycode.contains(CountryCodes.SintMaarten)){
            result = Integer.toString(CallRates.SintMaarten);
        }else if (countrycode.contains(CountryCodes.Slovakia)){
            result = Integer.toString(CallRates.Slovakia);
        }else if (countrycode.contains(CountryCodes.Slovenia)){
            result = Integer.toString(CallRates.Slovenia);
        }else if (countrycode.contains(CountryCodes.SolomonIslands)){
            result = Integer.toString(CallRates.SolomonIslands);
        }else if (countrycode.contains(CountryCodes.Somalia)){
            result = Integer.toString(CallRates.Somalia);
        }else if (countrycode.contains(CountryCodes.SouthAfrica)){
            result = Integer.toString(CallRates.SouthAfrica);
        }else if (countrycode.contains(CountryCodes.SouthGeorgiaandtheSouthSandwichIslands)){
            result = Integer.toString(CallRates.SouthGeorgiaandtheSouthSandwichIslands);
        }else if (countrycode.contains(CountryCodes.SouthKorea)){
            result = Integer.toString(CallRates.SouthKorea);
        }else if (countrycode.contains(CountryCodes.SouthSudan)){
            result = Integer.toString(CallRates.SouthSudan);
        }else if (countrycode.contains(CountryCodes.SovietUnion)){
            result = Integer.toString(CallRates.SovietUnion);
        }else if (countrycode.contains(CountryCodes.Spain)){
            result = Integer.toString(CallRates.Spain);
        }else if (countrycode.contains(CountryCodes.SriLanka)){
            result = Integer.toString(CallRates.SriLanka);
        }else if (countrycode.contains(CountryCodes.Sudan)){
            result = Integer.toString(CallRates.Sudan);
        }else if (countrycode.contains(CountryCodes.Suriname)){
            result = Integer.toString(CallRates.Suriname);
        }else if (countrycode.contains(CountryCodes.SvalbardandJanMayen)){
            result = Integer.toString(CallRates.SvalbardandJanMayen);
        }else if (countrycode.contains(CountryCodes.Swaziland)){
            result = Integer.toString(CallRates.Swaziland);
        }else if (countrycode.contains(CountryCodes.Sweden)){
            result = Integer.toString(CallRates.Sweden);
        }else if (countrycode.contains(CountryCodes.Switzerland)){
            result = Integer.toString(CallRates.Switzerland);
        }else if (countrycode.contains(CountryCodes.Syria)){
            result = Integer.toString(CallRates.Syria);
        }else if (countrycode.contains(CountryCodes.Taiwan)){
            result = Integer.toString(CallRates.Taiwan);
        }else if (countrycode.contains(CountryCodes.Tajikistan)){
            result = Integer.toString(CallRates.Tajikistan);
        }else if (countrycode.contains(CountryCodes.Tanzania)){
            result = Integer.toString(CallRates.Tanzania);
        }else if (countrycode.contains(CountryCodes.Thailand)){
            result = Integer.toString(CallRates.Thailand);
        }else if (countrycode.contains(CountryCodes.Togo)){
            result = Integer.toString(CallRates.Togo);
        }else if (countrycode.contains(CountryCodes.Tokelau)){
            result = Integer.toString(CallRates.Tokelau);
        }else if (countrycode.contains(CountryCodes.Tonga)){
            result = Integer.toString(CallRates.Tonga);
        }else if (countrycode.contains(CountryCodes.TrinidadandTobago)){
            result = Integer.toString(CallRates.TrinidadandTobago);
        }else if (countrycode.contains(CountryCodes.Tunisia)){
            result = Integer.toString(CallRates.Tunisia);
        }else if (countrycode.contains(CountryCodes.Turkey)){
            result = Integer.toString(CallRates.Turkey);
        }else if (countrycode.contains(CountryCodes.Turkmenistan)){
            result = Integer.toString(CallRates.Turkmenistan);
        }else if (countrycode.contains(CountryCodes.TurksandCaicosIslands)){
            result = Integer.toString(CallRates.TurksandCaicosIslands);
        }else if (countrycode.contains(CountryCodes.Tuvalu)){
            result = Integer.toString(CallRates.Tuvalu);
        }else if (countrycode.contains(CountryCodes.Uganda)){
            result = Integer.toString(CallRates.Uganda);
        }else if (countrycode.contains(CountryCodes.Ukraine)){
            result = Integer.toString(CallRates.Ukraine);
        }else if (countrycode.contains(CountryCodes.UnitedArabEmirates)){
            result = Integer.toString(CallRates.UnitedArabEmirates);
        }else if (countrycode.contains(CountryCodes.UnitedKingdom)){
            result = Integer.toString(CallRates.UnitedKingdom);
        }else if (countrycode.contains(CountryCodes.UnitedStatesofAmerica)){
            result = Integer.toString(CallRates.UnitedStatesofAmerica);
        }else if (countrycode.contains(CountryCodes.Uruguay)){
            result = Integer.toString(CallRates.Uruguay);
        }else if (countrycode.contains(CountryCodes.Uzbekistan)){
            result = Integer.toString(CallRates.Uzbekistan);
        }else if (countrycode.contains(CountryCodes.Vanuatu)){
            result = Integer.toString(CallRates.Vanuatu);
        }else if (countrycode.contains(CountryCodes.VaticanCity)){
            result = Integer.toString(CallRates.VaticanCity);
        }else if (countrycode.contains(CountryCodes.Venezuela)){
            result = Integer.toString(CallRates.Venezuela);
        }else if (countrycode.contains(CountryCodes.Vietnam)){
            result = Integer.toString(CallRates.Vietnam);
        }else if (countrycode.contains(CountryCodes.VirginIslands)){
            result = Integer.toString(CallRates.VirginIslands);
        }else if (countrycode.contains(CountryCodes.VirginIslandsoftheUnitedStates)){
            result = Integer.toString(CallRates.VirginIslandsoftheUnitedStates);
        }else if (countrycode.contains(CountryCodes.Wales)){
            result = Integer.toString(CallRates.Wales);
        }else if (countrycode.contains(CountryCodes.WallisandFutuna)){
            result = Integer.toString(CallRates.WallisandFutuna);
        }else if (countrycode.contains(CountryCodes.WesternSahara)){
            result = Integer.toString(CallRates.WesternSahara);
        }else if (countrycode.contains(CountryCodes.Yemen)){
            result = Integer.toString(CallRates.Yemen);
        }else if (countrycode.contains(CountryCodes.Zambia)){
            result = Integer.toString(CallRates.Zambia);
        }else if (countrycode.contains(CountryCodes.Zimbabwe)){
            result = Integer.toString(CallRates.Zimbabwe);
        }
        return result;
    }
}
