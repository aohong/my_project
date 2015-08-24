package com.dbframe.po;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;

import com.dbframe.annotation.Column;
import com.dbframe.annotation.Id;
import com.dbframe.annotation.Table;
import com.dbframe.basepo.ShardTable;
import com.dbframe.model.SqlFilter;

@Table("test")
public class MysqlShardTestPO implements ShardTable {

    private Integer id;

    private Integer intVar;

    private String stringVar;

    private Date timeVar;

    private BigDecimal decimalVar;

    private float floatVar;

    private double doubleVar;

    @Override
    public String shardTableSuffix() {
        DecimalFormat df = new DecimalFormat("_000");
        return df.format(intVar % 4);
    }

    @Override
    public String shardTableSuffix(SqlFilter filter) {
        Map<String, Object> map = filter.toMap();
        DecimalFormat df = new DecimalFormat("_000");
        return df.format((Integer) map.get("intVar") % 4);
    }

    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIntVar() {
        return intVar;
    }

    public void setIntVar(Integer intVar) {
        this.intVar = intVar;
    }

    public String getStringVar() {
        return stringVar;
    }

    public void setStringVar(String stringVar) {
        this.stringVar = stringVar;
    }

    public Date getTimeVar() {
        return timeVar;
    }

    public void setTimeVar(Date timeVar) {
        this.timeVar = timeVar;
    }

    public BigDecimal getDecimalVar() {
        return decimalVar;
    }

    public void setDecimalVar(BigDecimal decimalVar) {
        this.decimalVar = decimalVar;
    }

    public float getFloatVar() {
        return floatVar;
    }

    public void setFloatVar(float floatVar) {
        this.floatVar = floatVar;
    }

    @Column(name = "dbl_var")
    public double getDoubleVar() {
        return doubleVar;
    }

    public void setDoubleVar(double doubleVar) {
        this.doubleVar = doubleVar;
    }

    @Override
    public String toString() {
        return "TestPO [id=" + id + ", intVar=" + intVar + ", stringVar="
                + stringVar + ", timeVar=" + timeVar + ", decimalVar="
                + decimalVar + ", floatVar=" + floatVar + ", doubleVar="
                + doubleVar + "]";
    }

}
