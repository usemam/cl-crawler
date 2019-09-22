using Usemam.Cl.Crawler.Domain.Messages;

namespace Usemam.Cl.Crawler.AppHost
{
    public interface IMessagingBuilder
    {
        IMessagingBuilder WithMessageHandler<THandler, TMessage>()
            where THandler : IMessageHandler<TMessage>;
    }
}