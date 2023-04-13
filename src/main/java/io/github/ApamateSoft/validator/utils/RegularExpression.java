package io.github.ApamateSoft.validator.utils;

// TODO: Crear pruebas unitarias
public class RegularExpression {

    public static final String NUMBER = "^\\d+$";
    public static final String ALPHABET = "^[a-zA-Z]+$";
    public static final String ALPHABET_UPPERCASE = "^[A-Z]+$";
    public static final String ALPHABET_LOWERCASE = "^[a-z]+$";
    public static final String SPECIAL_CHARACTERS = "^[^A-z\\s\\d][\\\\\\^]?$";
    public static final String ALPHABET_ES = "^[a-zA-ZáéíóúñÁÉÍÓÚÑ]+$";
    public static final String ALPHA_NUMERIC = "^[a-zA-Z0-9]+$";
    public static final String ALPHA_NUMERIC_ES = "^[a-zA-Z0-9]+$";
    public static final String NAME = "^[a-zA-Z]+(\\s?[a-zA-Z])*$";
    public static final String NAME_LOWERCASE = "^[a-z]+(\\s?[a-z])*$";
    public static final String NAME_UPPERCASE = "^[A-Z]+(\\s?[A-Z])*$";
    public static final String NAME_CAPITALIZE = "^[A-Z][a-z]+(\\s?[A-Z][a-z]+)*$";
    public static final String NAME_ES = "^[a-zA-ZáéíóúñÁÉÍÓÚÑ]+(\\s?[a-zA-ZáéíóúñÁÉÍÓÚÑ])*$";
    public static final String NAME_LOWERCASE_ES = "^[a-záéíóúñ]+(\\s?[a-záéíóúñ])*$";
    public static final String NAME_UPPERCASE_ES = "^[A-ZÁÉÍÓÚÑ]+(\\s?[A-ZÁÉÍÓÚÑ])*$";
    public static final String NAME_CAPITALIZE_ES = "^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+(\\s?[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+)*$";
    public static final String INTEGER = "^-?\\d+$";
    public static final String DECIMAL = "^\\-?\\d*\\.?\\d+?$";
    public static final String EMAIL = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$";
    public static final String LINK = "^((https://)|(www.)|(http://))+([a-zA-Z0-9@:%._+~#=]{2,63})+\\.([a-z]{2,6}\\b)+(.)*$";
    public static final String WWW_LINK = "^www.([a-zA-Z0-9@:%._+~#=]{2,63})+\\.([a-z]{2,6}\\b)+(.)*$";
    public static final String HTTP_LINK = "^http://.([a-zA-Z0-9@:%._+~#=]{2,63})+\\.([a-z]{2,6}\\b)+(.)*$";
    public static final String HTTPS_LINK = "^https://.([a-zA-Z0-9@:%._+~#=]{2,63})+\\.([a-z]{2,6}\\b)+(.)*$";
    public static final String IP = "^((^\\s*((([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]))\\s*$)|(^\\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:)))(%.+)?\\s*$))$";
    public static final String IPV4 = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";
    public static final String IPV6 = "^(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))$";
    public static final String PHONE = "^(\\+?(\\d{1,3}))?([-. (]*(\\d{3})[-. )]*)?((\\d{3})[-. ]*(\\d{2,4})(?:[-.x ]*(\\d+))?)\\s*$";
    public static final String TIME = "^(^(([0-1]?\\d)|(2[0-3])):([0-5][0-9])(:?[0-5][0-9])?$)|(^((0?[1-9])|(1[0-2])):([0-5][0-9])(:[0-5][0-9])?\\s?([aA]|[pP])[mM]$)$";
    public static final String TIME12 = "^((0?[1-9])|(1[0-2])):([0-5][0-9])(:[0-5][0-9])?\\s?([aA]|[pP])[mM]$";
    public static final String TIME24 = "^(([0-1]?\\d)|(2[0-3])):([0-5][0-9])(:?[0-5][0-9])?$";
    public static final String CARD = "(\\d{4}[-. ]?){4}|\\d{4}[-. ]?\\d{6}[-. ]?\\d{5}";

}