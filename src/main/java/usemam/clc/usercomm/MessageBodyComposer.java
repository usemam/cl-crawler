package usemam.clc.usercomm;

public interface MessageBodyComposer<T> {

    String compose(T messageData);
}
