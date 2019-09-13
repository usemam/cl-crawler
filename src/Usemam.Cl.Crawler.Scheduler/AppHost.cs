using Funq;
using Hangfire;
using Hangfire.Common;
using ServiceStack;
using ServiceStack.Messaging;
using ServiceStack.Messaging.Redis;
using ServiceStack.Redis;
using Usemam.Cl.Crawler.Domain.Repositories;
using Usemam.Cl.Crawler.Scheduler.Jobs;

namespace Usemam.Cl.Crawler.Scheduler
{
    public class AppHost : ServiceStackHost
    {
        private readonly string _redisHost;
        private readonly int _redisPort;

        public AppHost(string redisHost, int redisPort)
            : base("Scheduler component", typeof(AppHost).Assembly, typeof(RedisUserRepository).Assembly)
        {
            _redisHost = redisHost;
            _redisPort = redisPort;
        }

        public override void Configure(Container container)
        {
            container.Register<IRedisClientsManager>(new PooledRedisClientManager($"{_redisHost}:{_redisPort}"));

            container.Register<IUserRepository>(c => new RedisUserRepository(c.Resolve<IRedisClientsManager>()));

            var messageService = new RedisMqServer(container.Resolve<IRedisClientsManager>());
            container.Register<IMessageService>(messageService);
            messageService.Start();

            GlobalConfiguration.Configuration.UseActivator(new Activator(container));

            var jobManager = new RecurringJobManager();
            jobManager.AddOrUpdate(NotifyParserJob.JobId, Job.FromExpression((NotifyParserJob job) => job.Execute()), Cron.Daily(20));
        }
    }
}