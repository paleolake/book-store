package book.store.common;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class Num {

    private BigDecimal base;

    private Num(BigDecimal base) {
        this.base = base;
    }

    public static Num create(double val) {
        return create(String.valueOf(val));
    }

    public static Num create(String val) {
        return create(new BigDecimal(val));
    }

    public static Num create(BigDecimal val) {
        return new Num(val);
    }

    /**
     * 加上
     *
     * @param val
     * @return
     */
    public Num add(BigDecimal val) {
        base = base.add(val);
        return this;
    }

    /**
     * 加上
     *
     * @param val
     * @return
     */
    public Num add(String val) {
        return add(new BigDecimal(val));
    }

    /**
     * 加上
     *
     * @param val
     * @return
     */
    public Num add(double val) {
        return add(String.valueOf(val));
    }

    /**
     * 减去
     *
     * @param val
     * @return
     */
    public Num sub(BigDecimal val) {
        base = base.subtract(val);
        return this;
    }

    /**
     * 减去
     *
     * @param val
     * @return
     */
    public Num sub(String val) {
        return this.sub(new BigDecimal(val));
    }

    /**
     * 减去
     *
     * @param val
     * @return
     */
    public Num sub(Double val) {
        return this.sub(String.valueOf(val));
    }

    /**
     * 乘以
     *
     * @param val
     * @return
     */
    public Num mul(BigDecimal val) {
        base = base.multiply(val);
        return this;
    }

    /**
     * 乘以
     *
     * @param val
     * @return
     */
    public Num mul(double val) {
        return mul(String.valueOf(val));
    }

    /**
     * 乘以
     *
     * @param val
     * @return
     */
    public Num mul(String val) {
        return mul(new BigDecimal(val));
    }

    /**
     * 除以
     *
     * @param val
     * @return
     */
    public Num div(String val, int scale, RoundingMode mode) {
        return this.div(new BigDecimal(val), scale, mode);
    }

    /**
     * 除以
     *
     * @param val
     * @return
     */
    public Num div(double val, int scale, RoundingMode mode) {
        return this.div(String.valueOf(val), scale, mode);
    }

    /**
     * 除以
     *
     * @param val
     * @return
     */
    public Num div(BigDecimal val, int scale, RoundingMode mode) {
        base = base.divide(val, scale, mode);
        return this;
    }

    /**
     * 精确比较
     *
     * @param val
     * @return 如果两个数一样则返回0，如果第一个数比第二个数大则返回1，反之返回-1
     */
    public int compareTo(double val) {
        return base.compareTo(new BigDecimal(String.valueOf(val)));
    }

    /**
     * 精确对比两个数字：大于等于
     *
     * @param val
     * @return
     */
    public boolean ge(double val) {
        BigDecimal b2 = new BigDecimal(String.valueOf(val));
        return base.compareTo(b2) == 0 || base.compareTo(b2) == 1 ? true : false;
    }

    /**
     * 精确对比两个数字：小于等于
     *
     * @param val
     * @return
     */
    public boolean le(double val) {
        BigDecimal b2 = new BigDecimal(String.valueOf(val));
        return base.compareTo(b2) == 0 || base.compareTo(b2) == -1 ? true : false;
    }

    /**
     * 精确对比两个数字：大于
     *
     * @param val
     * @return
     */
    public boolean gt(double val) {
        BigDecimal b2 = new BigDecimal(String.valueOf(val));
        return base.compareTo(b2) == 1 ? true : false;
    }

    /**
     * 精确对比两个数字：小于
     *
     * @param val
     * @return
     */
    public boolean lt(double val) {
        BigDecimal b2 = new BigDecimal(String.valueOf(val));
        return base.compareTo(b2) == -1 ? true : false;
    }

    /**
     * 精确对比两个数字：等于
     *
     * @param val
     * @return
     */
    public boolean eq(double val) {
        BigDecimal b2 = new BigDecimal(String.valueOf(val));
        return base.compareTo(b2) == 0 ? true : false;
    }

    /**
     * 取模
     *
     * @param val
     * @return
     */
    public Num remainder(double val) {
        base = base.remainder(new BigDecimal(String.valueOf(val)));
        return this;
    }

    /**
     * 取模
     *
     * @param val
     * @return
     */
    public Num remainder(String val) {
        base = base.remainder(new BigDecimal(val));
        return this;
    }

    /**
     * 取模
     *
     * @param val
     * @return
     */
    public Num remainder(BigDecimal val) {
        base = base.remainder(val);
        return this;
    }

    /**
     * @return
     * @see BigDecimal#movePointLeft(int)
     */
    public Num left(int n) {
        base = base.movePointLeft(n);
        return this;
    }

    /**
     * @return
     * @see BigDecimal#movePointRight(int)
     */
    public Num right(int n) {
        base = base.movePointRight(n);
        return this;
    }

    public BigDecimal bigValue() {
        return base;
    }

    public double doubleValue() {
        return base.doubleValue();
    }


    public int intValue() {
        return base.intValue();
    }

    public float floatValue() {
        return base.floatValue();
    }

    /**
     * 精确度后的数字直接去掉
     *
     * @param scale
     * @return
     */
    public Num floor(int scale) {
        base = base.setScale(scale, RoundingMode.FLOOR);
        return this;
    }

    public double floorValue(int scale) {
        return base.setScale(scale, RoundingMode.FLOOR).doubleValue();
    }

    public Num halfup(int scale) {
        base = base.setScale(scale, RoundingMode.HALF_UP);
        return this;
    }

    public double halfupValue(int scale) {
        return base.setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    public static void main(String[] args) {
        BigDecimal a = new BigDecimal(100.00);
        System.out.println(a.doubleValue());
    }

}
