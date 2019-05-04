package usemam.clc.parser;

public interface PageParser<T> {
    T parse(String url) throws Exception;
}
