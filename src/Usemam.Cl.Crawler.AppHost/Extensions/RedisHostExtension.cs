using System;
using Funq;
using ServiceStack.Redis;
using Usemam.Cl.Crawler.Domain.Repositories;

namespace Usemam.Cl.Crawler.AppHost.Extensions
{
    public class RedisHostExtension : IAppHostExtension, IRedisConfiguration
    {
        private string _host;
        private int _port;

        public void Attach(Container container)
        {
            container.Register<IRedisClientsManager>(new PooledRedisClientManager($"{_host}:{_port}"));
            container.Register<IUserRepository>(c => new RedisUserRepository(c.Resolve<IRedisClientsManager>()));
        }

        public IRedisConfiguration WithHostInfo(string host, int port)
        {
            _host = host;
            _port = port;
            return this;
        }

        public IRedisConfiguration WithHostInfoFromArgs(string[] args)
        {
            try
            {
                _host = args[0];
            }
            catch
            {
                throw new ApplicationException("Redis host is not provided.");
            }

            try
            {
                _port = int.Parse(args[1]);
            }
            catch
            {
                throw new ApplicationException("Redis port is not provided.");
            }
            return this;
        }
    }
}