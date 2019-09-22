using System;
using System.Collections.Generic;
using Funq;
using Microsoft.Extensions.Logging;
using ServiceStack;

namespace Usemam.Cl.Crawler.AppHost.Extensions
{
    public class LoggingHostExtension : IAppHostExtension, ILoggingBuilder
    {
        private readonly IList<Action<Container>> _loggerRegistrations =
            new List<Action<Container>>();

        public void Attach(Container container)
        {
            var loggerFactory = new LoggerFactory().AddConsole();
            container.AddSingleton(loggerFactory);
            foreach (var loggerRegistration in _loggerRegistrations)
            {
                loggerRegistration(container);
            }
        }

        public ILoggingBuilder LoggerFor<T>()
        {
            _loggerRegistrations.Add(
                container => container.Register(c => c.Resolve<ILoggerFactory>().CreateLogger<T>()));
            return this;
        }
    }
}