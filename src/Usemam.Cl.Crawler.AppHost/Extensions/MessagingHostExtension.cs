using System;
using System.Collections.Generic;
using Funq;
using ServiceStack.Messaging;
using ServiceStack.Messaging.Redis;
using ServiceStack.Redis;
using Usemam.Cl.Crawler.Domain.Messages;

namespace Usemam.Cl.Crawler.AppHost.Extensions
{
    public class MessagingHostExtension : IAppHostExtension, IMessagingBuilder
    {
        private readonly IList<Action<IMessageService, Container>> _handlerRegistrations =
            new List<Action<IMessageService, Container>>();

        public void Attach(Container container)
        {
            var messageService = new RedisMqServer(container.Resolve<IRedisClientsManager>());
            foreach (var handlerRegistration in _handlerRegistrations)
            {
                handlerRegistration(messageService, container);
            }

            container.Register<IMessageService>(messageService);
            messageService.Start();
        }

        public IMessagingBuilder WithMessageHandler<THandler, TMessage>()
            where THandler : IMessageHandler<TMessage>
        {
            _handlerRegistrations.Add(
                (messageService, container) =>
                {
                    container.RegisterAutoWired<THandler>();
                    messageService.RegisterHandler<TMessage>(
                        m =>
                        {
                            container.Resolve<THandler>().Handle(m.GetBody());
                            return null;
                        });
                });
            return this;
        }
    }
}