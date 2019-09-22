using System;
using Usemam.Cl.Crawler.AppHost;
using Usemam.Cl.Crawler.Domain.Messages;
using Usemam.Cl.Crawler.Domain.Repositories;

namespace Usemam.Cl.Crawler.Parser
{
    class Program
    {
        static void Main(string[] args)
        {
            var appHost = new AppHostBuilder()
                .WithName("Parser component")
                .WithAssembly(typeof(Program).Assembly)
                .WithAssembly(typeof(RedisUserRepository).Assembly)
                .WithLogging(b => b.LoggerFor<SearchAdsMessageHandler>())
                .WithRedis(c => c.WithHostInfoFromArgs(args))
                .WithMessaging(b => b.WithMessageHandler<SearchAdsMessageHandler, SearchAds>())
                .Build();
            appHost.Init();

            Console.WriteLine("Press any key to stop...");
            Console.ReadKey();
        }
    }
}
