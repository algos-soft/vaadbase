package it.algos.vaadbase.converter;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.enumeration.EAPrefType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.Optional;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: sab, 26-mag-2018
 * Time: 14:16
 */
@Slf4j
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AConverterIntegerByte implements Converter<String, byte[]> {


    @Override
    public Result<byte[]> convertToModel(String s, ValueContext valueContext) {
        byte[] risultato= EAPrefType.integer.objectToBytes(s);
        return Result.ok((byte[])risultato);
    }// end of method

    @Override
    public String convertToPresentation(byte[] bytes, ValueContext valueContext) {
        return "pippozx";
    }// end of method

}// end of class
