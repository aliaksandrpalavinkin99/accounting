package com.xm.accounting.controller.converter;

import com.xm.accounting.dto.SortType;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SortTypeConverter implements Converter<String, SortType> {
    @Override
    public SortType convert(String source) {
        if (EnumUtils.isValidEnum(SortType.class, source)) {
            return SortType.valueOf(source);
        } else {
            return SortType.valueOf(source.toUpperCase());
        }
    }
}
