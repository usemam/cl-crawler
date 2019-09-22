using Funq;
using Hangfire;
using Hangfire.Common;
using Usemam.Cl.Crawler.AppHost;
using Usemam.Cl.Crawler.Scheduler.Jobs;

namespace Usemam.Cl.Crawler.Scheduler
{
    public class HangfireConfigurationExtension : IAppHostExtension
    {
        public void Attach(Container container)
        {
            GlobalConfiguration.Configuration.UseActivator(new Activator(container));

            var jobManager = new RecurringJobManager();
            jobManager.AddOrUpdate(
                NotifyParserJob.JobId,
                Job.FromExpression((NotifyParserJob job) => job.Execute()), Cron.Daily(20));
        }
    }
}