package usemam.clc.parser;

public class Money {

    private int value;

    public Money(String str) {
        Integer val = Integer.getInteger(str);
        if (val == null) {
            val = Integer.getInteger(str.substring(1));
        }

        if (val == null) {
            throw new IllegalArgumentException("'" + str + "' is not valid Money value.");
        }

        value = val;
    }

    public int getValue() { return value; }
}
