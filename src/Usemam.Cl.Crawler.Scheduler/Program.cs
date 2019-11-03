using System;
using Hangfire;
using Usemam.Cl.Crawler.AppHost;
using Usemam.Cl.Crawler.Domain.Repositories;
using Usemam.Cl.Crawler.Scheduler.Jobs;

namespace Usemam.Cl.Crawler.Scheduler
{
    class Program
    {
        static void Main(string[] args)
        {
            var appHost = new HostBuilder()
                .WithName("Scheduler component")
                .WithAssembly(typeof(Program).Assembly)
                .WithAssembly(typeof(RedisUserRepository).Assembly)
                .WithLogging(b => b.LoggerFor<NotifyParserJob>())
                .WithRedis(b => b.WithHostInfoFromArgs(args))
                .WithMessaging()
                .WithExtension(new HangfireConfigurationExtension())
                .BuildAppHost();
            appHost.Init();

            using (new BackgroundJobServer())
            {
                Console.WriteLine("Press any key to stop...");
                Console.ReadKey();
            }
        }
    }
}
