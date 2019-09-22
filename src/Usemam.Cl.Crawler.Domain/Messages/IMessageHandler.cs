namespace Usemam.Cl.Crawler.Domain.Messages
{
    public interface IMessageHandler<in TMessage>
    {
        void Handle(TMessage message);
    }
}