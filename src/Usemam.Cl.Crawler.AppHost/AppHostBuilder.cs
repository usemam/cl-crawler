using System;
using System.Collections.Generic;
using System.Reflection;
using Usemam.Cl.Crawler.AppHost.Extensions;

namespace Usemam.Cl.Crawler.AppHost
{
    public class HostBuilder
    {
        private readonly IList<Assembly> _assemblies = new List<Assembly>();
        private readonly IList<IAppHostExtension> _extensions = new List<IAppHostExtension>();
        private string _hostName;

        public HostBuilder WithName(string name)
        {
            _hostName = name;
            return this;
        }

        public HostBuilder WithAssembly(Assembly assembly)
        {
            _assemblies.Add(assembly);
            return this;
        }

        public HostBuilder WithRedis(Action<IRedisConfiguration> configuration)
        {
            var redisExtension = new RedisHostExtension();
            configuration(redisExtension);
            _extensions.Add(redisExtension);
            return this;
        }

        public HostBuilder WithMessaging(Action<IMessagingBuilder> configureHandlers = null)
        {
            var messagingExtension = new MessagingHostExtension();
            configureHandlers?.Invoke(messagingExtension);
            _extensions.Add(messagingExtension);
            return this;
        }

        public HostBuilder WithLogging(Action<ILoggingBuilder> configureLoggers)
        {
            var loggingExtension = new LoggingHostExtension();
            configureLoggers(loggingExtension);
            _extensions.Add(loggingExtension);
            return this;
        }

        public HostBuilder WithExtension(IAppHostExtension extension)
        {
            _extensions.Add(extension);
            return this;
        }

        public AppHost BuildAppHost()
        {
            return new AppHost(_hostName, _assemblies, _extensions);
        }

        public WebHost BuildWebHost()
        {
            return new WebHost(_hostName, _assemblies, _extensions);
        }
    }
}