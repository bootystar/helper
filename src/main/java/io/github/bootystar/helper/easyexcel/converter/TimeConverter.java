package io.github.bootystar.helper.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Excel Time转换器
 *
 * @author booty
 */
public class TimeConverter implements Converter<Time> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public Class<Time> supportJavaTypeKey() {
        return Time.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Time convertToJavaData(ReadCellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        String cellValue = cellData.getStringValue();
        if (cellValue == null || cellValue.isEmpty()) {
            return null;
        }
        LocalTime lt = LocalTime.parse(cellValue, formatter);
        return Time.valueOf(lt);
    }

    @Override
    public WriteCellData<String> convertToExcelData(Time value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (value == null) {
            return new WriteCellData<>("");
        }
        LocalTime lt = value.toLocalTime();
        return new WriteCellData<>(lt.format(formatter));
    }
}