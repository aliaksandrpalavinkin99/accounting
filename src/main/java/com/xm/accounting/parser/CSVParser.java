package com.xm.accounting.parser;

import java.util.List;

public interface CSVParser<T> {

    boolean isParseNeeded();
    List<T> parse();
}
