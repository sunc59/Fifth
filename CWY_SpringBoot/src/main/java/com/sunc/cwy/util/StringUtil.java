package com.sunc.cwy.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Vector;

/**
 * 字符串工具类
 */
public class StringUtil {
    static final char[] QUOTE_ENCODE = "&quot;".toCharArray();
    static final char[] AMP_ENCODE = "&amp;".toCharArray();
    static final char[] LT_ENCODE = "&lt;".toCharArray();
    static final char[] GT_ENCODE = "&gt;".toCharArray();

    public StringUtil() {
    }

    public static String join(String[] paramString, String seprater) {
        if (paramString != null && paramString.length != 0) {
            StringBuffer s = new StringBuffer();

            for (int i = 0; i < paramString.length; ++i) {
                s.append(seprater + paramString[i]);
            }

            return s.toString().substring(1);
        } else {
            return "";
        }
    }

    public static String join(byte[] paramString, String seprater) {
        if (paramString != null && paramString.length != 0) {
            StringBuffer s = new StringBuffer();

            for (int i = 0; i < paramString.length; ++i) {
                s.append(seprater + paramString[i]);
            }

            return s.toString().substring(1);
        } else {
            return "";
        }
    }

    public static String[] split(String line, int seperator) {
        if (line == null) {
            return null;
        } else {
            line = line.trim();
            if (line.length() == 0) {
                return null;
            } else {
                Vector<Object> v = new Vector();

                int i;
                int j;
                for (i = 0; (j = line.indexOf(seperator, i)) >= 0; i = j + 1) {
                    v.addElement(line.substring(i, j).trim());
                }

                v.addElement(line.substring(i).trim());
                int size = v.size();
                String[] ps = new String[size];
                if (size > 0) {
                    v.copyInto(ps);
                }

                return ps;
            }
        }
    }

    public static String iso2utf8(String paramString) {
        try {
            return new String(paramString.getBytes("iso-8859-1"), "utf-8");
        } catch (Exception var2) {
            var2.printStackTrace();
            return "";
        }
    }

    public static String iso2gbk(String paramString) {
        try {
            return new String(paramString.getBytes("iso-8859-1"), "GBK");
        } catch (Exception var2) {
            var2.printStackTrace();
            return "";
        }
    }

    public static String iso2utf8DB(String paramString) {
        try {
            String temp = new String(paramString.getBytes("iso-8859-1"), "utf-8");
            return temp.replaceAll("'", "''");
        } catch (Exception var2) {
            var2.printStackTrace();
            return "";
        }
    }

    public static String transform(String paramString1, String paramString2, String paramString3) {
        try {
            return new String(paramString1.getBytes(paramString2), paramString3);
        } catch (Exception var4) {
            var4.printStackTrace();
            return "";
        }
    }

    public static String localToGBK(String source) {
        String sysEnc = System.getProperty("file.encoding");
        String result = "";
        if (sysEnc == null || sysEnc.equals("")) {
            sysEnc = "ISO8859_1";
        }

        if ("GBK".equals(sysEnc)) {
            return source;
        } else {
            if (source != null) {
                try {
                    result = new String(source.getBytes(sysEnc), "GBK");
                } catch (Exception var4) {
                    return source;
                }
            }

            return result;
        }
    }

    public static String gbkToLocal(String source) {
        String sysEnc = System.getProperty("file.encoding");
        String result = "";
        if (sysEnc == null || sysEnc.equals("")) {
            sysEnc = "ISO8859_1";
        }

        if ("GBK".equals(sysEnc)) {
            return source;
        } else {
            if (source != null) {
                try {
                    result = new String(source.getBytes("GBK"), sysEnc);
                } catch (Exception var4) {
                    return source;
                }
            }

            return result;
        }
    }

    public static String toURL(String paramString) {
        try {
            return URLEncoder.encode(paramString, "UTF-8");
        } catch (UnsupportedEncodingException var2) {
            var2.printStackTrace();
            return "";
        }
    }

    public static String strToWeb(String paramString) {
        if (paramString != null && paramString.length() != 0) {
            char[] arrayOfChar = paramString.toCharArray();
            StringBuffer localStringBuffer = new StringBuffer();

            for (int i = 0; i < arrayOfChar.length; ++i) {
                switch (arrayOfChar[i]) {
                    case '\n':
                        localStringBuffer.append("<br/>");
                        break;
                    case ' ':
                        localStringBuffer.append("&nbsp;");
                        break;
                    case '"':
                        localStringBuffer.append("&quot;");
                        break;
                    case '&':
                        localStringBuffer.append("&amp;");
                        break;
                    case '\'':
                        localStringBuffer.append("&acute;");
                        break;
                    case '<':
                        localStringBuffer.append("&lt;");
                        break;
                    case '>':
                        localStringBuffer.append("&gt;");
                        break;
                    default:
                        try {
                            localStringBuffer.append(arrayOfChar[i]);
                        } catch (Exception var5) {
                        }
                }
            }

            return localStringBuffer.toString();
        } else {
            return "";
        }
    }

    public static String webToStr(String paramString) {
        if (paramString != null && paramString.length() != 0) {
            paramString = paramString.replaceAll("&gt;", ">");
            paramString = paramString.replaceAll("&lt;", "<");
            paramString = paramString.replaceAll("&nbsp;", " ");
            paramString = paramString.replaceAll("&quot;", "\"");
            paramString = paramString.replaceAll("&acute;", "'");
            paramString = paramString.replaceAll("&amp;", "&");
            paramString = paramString.replaceAll("<br/>", "\n");
            paramString = paramString.replaceAll("&gt;", ">");
            return paramString;
        } else {
            return "";
        }
    }

    public static String codeXMLStr(String str) {
        char AND_CHAR = 38;
        char LEFT_CHAR = 60;
        char AND_CHAR_REPLACEMENT = 249;
        char LEFT_CHAR_REPLACEMENT = 250;
        if (str != null && str.length() != 0) {
            int len = str.length();
            char[] val = new char[len];
            str.getChars(0, len, val, 0);
            int i = -1;

            do {
                ++i;
            } while (i < len && val[i] != AND_CHAR && val[i] != LEFT_CHAR);

            if (i >= len) {
                return str;
            } else {
                char[] buf = new char[len];

                for (int j = 0; j < i; ++j) {
                    buf[j] = val[j];
                }

                for (; i < len; ++i) {
                    char c = val[i];
                    if (c == AND_CHAR) {
                        buf[i] = (char) AND_CHAR_REPLACEMENT;
                    } else if (c == LEFT_CHAR) {
                        buf[i] = (char) LEFT_CHAR_REPLACEMENT;
                    } else {
                        buf[i] = c;
                    }
                }

                return new String(buf);
            }
        } else {
            return str;
        }
    }

    public static String decodeXMLStr(String source) {
        char AND_CHAR = 38;
        char LEFT_CHAR = 60;
        char AND_CHAR_REPLACEMENT = 249;
        char LEFT_CHAR_REPLACEMENT = 250;
        if (source != null && source.length() != 0) {
            int len = source.length();
            char[] val = new char[len];
            source.getChars(0, len, val, 0);
            int i = -1;

            do {
                ++i;
            } while (i < len && val[i] != AND_CHAR_REPLACEMENT && val[i] != LEFT_CHAR_REPLACEMENT);

            if (i >= len) {
                return source;
            } else {
                char[] buf = new char[len];

                for (int j = 0; j < i; ++j) {
                    buf[j] = val[j];
                }

                for (; i < len; ++i) {
                    char c = val[i];
                    if (c == AND_CHAR_REPLACEMENT) {
                        buf[i] = (char) AND_CHAR;
                    } else if (c == LEFT_CHAR_REPLACEMENT) {
                        buf[i] = (char) LEFT_CHAR;
                    } else {
                        buf[i] = c;
                    }
                }

                return new String(buf);
            }
        } else {
            return source;
        }
    }

    public static final String escapeForXML(String string) {
        if (string == null) {
            return null;
        } else {
            int i = 0;
            int last = 0;
            char[] input = string.toCharArray();
            int len = input.length;

            StringBuffer out;
            for (out = new StringBuffer((int) ((double) len * 1.3D)); i < len; ++i) {
                char ch = input[i];
                if (ch <= '>') {
                    if (ch == '<') {
                        if (i > last) {
                            out.append(input, last, i - last);
                        }

                        last = i + 1;
                        out.append(LT_ENCODE);
                    } else if (ch == '&') {
                        if (i > last) {
                            out.append(input, last, i - last);
                        }

                        last = i + 1;
                        out.append(AMP_ENCODE);
                    } else if (ch == '"') {
                        if (i > last) {
                            out.append(input, last, i - last);
                        }

                        last = i + 1;
                        out.append(QUOTE_ENCODE);
                    }
                }
            }

            if (last == 0) {
                return string;
            } else {
                if (i > last) {
                    out.append(input, last, i - last);
                }

                return out.toString();
            }
        }
    }

    public static final String unescapeFromXML(String string) {
        string = string.replaceAll("&lt;", "<");
        string = string.replaceAll("&gt;", ">");
        string = string.replaceAll("&quot;", "\"");
        return string.replaceAll("&amp;", "&");
    }

    public static boolean isLetter(char paramChar) {
        char c = 128;
        return paramChar / c == 0;
    }

    public static boolean isInt(String s) {
        boolean b = true;

        try {
            Long.parseLong(s);
        } catch (Exception var3) {
            b = false;
        }

        return b;
    }

    public static boolean isEmptyString(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isExist(String str, String[] array) {
        boolean result = false;
        if (array == null) {
            return result;
        } else {
            for (int i = 0; i < array.length; ++i) {
                if (str.equals(array[i])) {
                    result = true;
                }
            }

            return result;
        }
    }

    public static String rightAlign(String data, int length, String fill) {
        for (int i = data.length(); i < length; ++i) {
            data = fill + data;
        }

        return data;
    }

    public static String leftAlign(String data, int length, String fill) {
        for (int i = data.length(); i < length; ++i) {
            data = data + fill;
        }

        return data;
    }

    public static String convertNull2Empty(String str) {
        return isEmptyString(str) ? "" : str.trim();
    }

    public static int length(String paramString) {
        char[] arrayOfChar = paramString.toCharArray();
        int i = 0;

        for (int j = 0; j < arrayOfChar.length; ++j) {
            ++i;
            if (!isLetter(arrayOfChar[j])) {
                ++i;
            }
        }

        return i;
    }

    public static String toBin(char paramChar) {
        char c = '耀';
        StringBuffer localStringBuffer = new StringBuffer(16);

        for (int j = 0; j < 16; ++j) {
            localStringBuffer.append((paramChar & c) != 0 ? 1 : 0);
            c = (char) (c >>> 1);
        }

        return localStringBuffer.toString();
    }
}

