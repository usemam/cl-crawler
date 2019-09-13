using System;
using Funq;
using Hangfire;

namespace Usemam.Cl.Crawler.Scheduler.Jobs
{
    public class Activator : JobActivator
    {
        private readonly Container _container;

        public Activator(Container container)
        {
            _container = container;
        }

        public override object ActivateJob(Type jobType)
        {
            return _container.Resolve(jobType);
        }
    }
}