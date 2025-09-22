package rsshive.prac.numberconversiontool.model;

public enum BaseType {
    BINARY(2),
    OCTAL(8),
    DECIMAL(10),
    HEXADECIMAL(16),
    TEXT(256); // ASCII
    private final int base;

    BaseType(int base) {
        this.base = base;
    }

    public int getBase() {
        return base;
    }
}
