package 串;

//0位空着
public class SqString {
    int maxLen;
    char[] string;
    int length;

    public SqString(int maxLen) {
        this.maxLen = maxLen;
    }


    public void init() {
        string = new char[maxLen];
        length = 0;
    }


    public boolean strAssign(char... c) {
        return false;
    }


    public boolean strCopy(String s) {
        return false;
    }


    public boolean strEmpty() {
        return false;
    }


    public int strLength() {
        return 0;
    }


    public boolean clearString() {
        return false;
    }


    public boolean destroyString(String s) {
        return false;
    }


    public char[] concat(String s1, String s2) {
        return null;
    }


    public SqString subString(int pos, int len) {
        SqString subS = new SqString(len + 1);

        if (pos + len - 1 > length) {//范围越界
            return null;
        }
        for (int i = pos; i < len; i++) {
            subS.string[i - pos + 1] = string[i];
        }
        subS.length = len;
        return subS;
    }


    public int index(SqString t) {
        int i = 1, n = strLength(), m = t.strLength();
        while (i < n - m + 1) {
            SqString sub = subString(i, m);
            if (strCompare(sub, t) != 0) {
                i++;
            } else
                return i;
        }
        return 0;
    }


    public int strCompare(SqString s, SqString t) {
        for (int i = 1; i <= s.length && i <= t.length; i++) {
            if (s.string[i] != t.string[i]) {
                return s.string[i] - t.string[i];
            }
        }
        //前面都一样比较长度
        return s.string.length - t.string.length;
    }

}
