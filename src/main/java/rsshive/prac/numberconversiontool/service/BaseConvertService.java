package rsshive.prac.numberconversiontool.service;

import rsshive.prac.numberconversiontool.model.BaseType;

public interface BaseConvertService {
    String convert(String value, BaseType from, BaseType to);
}
