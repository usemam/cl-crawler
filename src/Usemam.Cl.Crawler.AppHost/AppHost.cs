using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using Funq;
using ServiceStack;

namespace Usemam.Cl.Crawler.AppHost
{
    public class AppHost : ServiceStackHost
    {
        private readonly IEnumerable<IAppHostExtension> _extensions;

        public AppHost(
            string hostName,
            IEnumerable<Assembly> assemblies,
            IEnumerable<IAppHostExtension> extensions)
            : base(hostName, assemblies.ToArray())
        {
            _extensions = extensions;
        }

        public override void Configure(Container container)
        {
            foreach (IAppHostExtension appHostExtension in _extensions)
            {
                appHostExtension.Attach(container);
            }
        }
    }
}